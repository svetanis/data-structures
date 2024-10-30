package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 359. Logger Rate Limiter
// Design a logger system that receives a stream 
// of messages along with their timestamps. 
// Each unique message should only be printed{" "} 
// at most every 10 seconds (i.e. a message printed 
// at timestamp t will prevent other identical messages 
// from being printed until timestamp t + 10).

// All messages will come in chronological order. 
// Several messages may arrive at the same timestamp.

public final class LoggerRateLimiter {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public LoggerRateLimiter() {
		this.map = new HashMap<>();
	}

	private final Map<String, Integer> map;

	public boolean shouldPrintMessage(int timestamp, String message) {
		if (!map.containsKey(message)) {
			map.put(message, timestamp);
			return true;
		} else {
			int prev = map.get(message);
			if (timestamp - prev < 10) {
				return false;
			}
			map.put(message, timestamp);
			return true;
		}
	}

	public boolean shouldPrintMessageSimple(int timestamp, String message) {
		int prev = map.getOrDefault(message, -100);
		if (timestamp - prev < 10) {
			return false;
		}
		map.put(message, timestamp);
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
