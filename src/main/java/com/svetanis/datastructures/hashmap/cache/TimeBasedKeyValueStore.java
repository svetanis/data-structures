package com.svetanis.datastructures.hashmap.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// 981. Time Based Key-Value Store

public final class TimeBasedKeyValueStore {

	private Map<String, TreeMap<Integer, String>> map;

	public TimeBasedKeyValueStore() {
		this.map = new HashMap<>();
	}

	public void set(String key, String val, int timestamp) {
		map.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, val);
	}

	public String get(String key, int timestamp) {
		if (!map.containsKey(key)) {
			return "";
		}
		TreeMap<Integer, String> tvm = map.get(key);
		Integer closestTimestamp = tvm.floorKey(timestamp);
		return closestTimestamp == null ? "" : tvm.get(closestTimestamp);
	}

	public static void main(String[] args) {
		TimeBasedKeyValueStore sa = new TimeBasedKeyValueStore();
		sa.set("foo", "bar", 1);
		System.out.println(sa.get("foo", 1)); // bar
		System.out.println(sa.get("foo", 3)); // bar
		sa.set("foo", "bar2", 4);
		System.out.println(sa.get("foo", 4)); // bar2
		System.out.println(sa.get("foo", 5)); // bar2
	}
}