package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 635. Design Log Storage System

public final class LogSystem {

	private List<LogEntry> logs;
	private Map<String, Integer> map;

	public LogSystem() {
		this.map = new HashMap<>();
		this.logs = new ArrayList<>();
		map.put("Year", 4);
		map.put("Month", 7);
		map.put("Day", 10);
		map.put("Hour", 13);
		map.put("Minute", 16);
		map.put("Second", 19);
	}

	public void put(int id, String timestamp) {
		logs.add(new LogEntry(id, timestamp));
	}

	public List<Integer> retrieve(String start, String end, String granularity) {
		List<Integer> list = new ArrayList<>();
		int glen = map.get(granularity);
		String sprefix = start.substring(0, glen);
		String eprefix = end.substring(0, glen);
		for (LogEntry log : logs) {
			String prefix = log.timestamp.substring(0, glen);
			boolean moreThan = sprefix.compareTo(prefix) <= 0;
			boolean lessThan = prefix.compareTo(eprefix) <= 0;
			if (moreThan && lessThan) {
				list.add(log.id);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		LogSystem ls = new LogSystem();
		ls.put(1, "2017:01:01:23:59:59");
		ls.put(2, "2017:01:01:22:59:59");
		ls.put(3, "2016:01:01:00:00:00");
		System.out.println(ls.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year")); // [3,2,1]
		System.out.println(ls.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Hour")); // [2,1]
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