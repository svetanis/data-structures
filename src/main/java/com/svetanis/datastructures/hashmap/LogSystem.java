package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 635. Design Log Storage System
//
// put(id, timestamp) stores a log; a timestamp reads "Year:Month:Day:Hour:Minute:Second",
// every field zero-padded, as in "2017:01:01:23:59:59". retrieve(start, end, granularity)
// returns the ids of the logs whose timestamp lies between start and end, both included,
// comparing only the fields down to granularity ("Year", "Month", ..., "Second") and
// ignoring the rest. The ids come back in the order the logs were put. The statement asks
// for no order; its example prints [3, 2, 1], which is timestamp order. On LeetCode:
// 2000 <= Year <= 2017, at most 500 calls to put and retrieve.
//
// Every field has a fixed width, so the timestamp strings sort exactly as the times do,
// and "only down to the hour" is "only the first 13 characters". The map holds that cut
// length per granularity; retrieve cuts start, end and each log to it and keeps a log when
// start <= log <= end as strings.

public final class LogSystem {
	// Time complexity: put O(1); retrieve O(n), n = logs stored, each cut and compared once
	// on at most 19 characters
	// Auxiliary Space: O(n), one entry per log

	private List<LogEntry> logs;
	private Map<String, Integer> map; // granularity -> length of the timestamp prefix it keeps

	public LogSystem() {
		this.map = new HashMap<>();
		this.logs = new ArrayList<>();
		map.put("Year", 4); // "2017"
		map.put("Month", 7); // "2017:01"
		map.put("Day", 10);
		map.put("Hour", 13);
		map.put("Minute", 16);
		map.put("Second", 19); // the whole timestamp
	}

	public void put(int id, String timestamp) {
		logs.add(new LogEntry(id, timestamp));
	}

	public List<Integer> retrieve(String start, String end, String granularity) {
		List<Integer> list = new ArrayList<>();
		int glen = map.get(granularity);
		String sprefix = start.substring(0, glen); // CUT the bounds to the granularity
		String eprefix = end.substring(0, glen);
		for (LogEntry log : logs) {
			String prefix = log.timestamp.substring(0, glen); // CUT the log the same way
			boolean moreThan = sprefix.compareTo(prefix) <= 0; // COMPARE as strings: fixed width
			boolean lessThan = prefix.compareTo(eprefix) <= 0;
			if (moreThan && lessThan) {
				list.add(log.id); // KEEP: both bounds included
			}
		}
		return list;
	}

	public static void main(String[] args) {
		LogSystem ls = new LogSystem();
		ls.put(1, "2017:01:01:23:59:59");
		ls.put(2, "2017:01:01:22:59:59");
		ls.put(3, "2016:01:01:00:00:00");
		System.out.println(ls.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year")); // [1, 2, 3]
		System.out.println(ls.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour")); // [1, 2]
	}

	private static class LogEntry {
		private int id;
		private String timestamp;

		public LogEntry(int id, String timestamp) {
			this.id = id;
			this.timestamp = timestamp;
		}
	}
}