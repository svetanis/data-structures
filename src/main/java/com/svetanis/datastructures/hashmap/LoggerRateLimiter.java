package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 359. Logger Rate Limiter
//
// Messages arrive with timestamps, in chronological order; several may share a timestamp.
// A message prints only if the same text has not printed in the last 10 seconds: a print at
// t blocks identical messages until t + 10. shouldPrintMessage says whether this one prints.
// On LeetCode: 0 <= timestamp <= 10^9, at most 10^4 calls.
//
// One number per message: the time it last PRINTED, in a map. It changes only when the
// message prints -- a blocked call must not move it, or a message would stay blocked longer
// than 10 seconds. The test is >= 10, because the block lasts until t + 10, not through it.
//
// Two methods, the same test. They differ in how "never printed" is spelled:
//   shouldPrintMessage        a message not in the map is its own case
//   shouldPrintMessageSimple  getOrDefault(message, -100): a made-up print far enough in the
//                             past to always pass. It is correct only because LeetCode's
//                             timestamps are >= 0 -- with a first message at -95 it would be
//                             blocked. Both share the one map, so use one per instance.

public final class LoggerRateLimiter {
	// Time complexity: O(1) per call, one map lookup and at most one put
	// Auxiliary Space: O(m), one entry per distinct message ever printed; nothing is removed

	public LoggerRateLimiter() {
		this.map = new HashMap<>();
	}

	private final Map<String, Integer> map; // LAST: message -> the time it last printed

	public boolean shouldPrintMessage(int timestamp, String message) {
		if (!map.containsKey(message)) { // NEVER: not printed before, so it prints
			map.put(message, timestamp); // RECORD
			return true;
		} else {
			int prev = map.get(message);
			if (timestamp - prev < 10) { // TEST: still blocked, and the map stays as it is
				return false;
			}
			map.put(message, timestamp); // RECORD: only when it prints
			return true;
		}
	}

	public boolean shouldPrintMessageSimple(int timestamp, String message) {
		int prev = map.getOrDefault(message, -100); // NEVER: -100 passes, since timestamps are >= 0
		if (timestamp - prev < 10) { // TEST: still blocked
			return false;
		}
		map.put(message, timestamp); // RECORD: only when it prints
		return true;
	}

	public static void main(String[] args) {
		LoggerRateLimiter lrl = new LoggerRateLimiter();
		System.out.println(lrl.shouldPrintMessageSimple(1, "foo")); // true
		System.out.println(lrl.shouldPrintMessageSimple(2, "bar")); // true
		System.out.println(lrl.shouldPrintMessageSimple(3, "foo")); // false
		System.out.println(lrl.shouldPrintMessageSimple(8, "bar")); // false
		System.out.println(lrl.shouldPrintMessageSimple(10, "foo")); // false
		System.out.println(lrl.shouldPrintMessageSimple(11, "foo")); // true
	}
}
