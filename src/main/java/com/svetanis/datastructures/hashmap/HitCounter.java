package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 362. Design Hit Counter

// design a system that keeps track 
// of the number of hits (or actions/events) 
// that occur on a resource in the last 5 minutes.

public final class HitCounter {

	private Map<Integer, Integer> map;

	public HitCounter() {
		this.map = new HashMap<>();
	}

	public void hit(int timestamp) {
		map.put(timestamp, map.getOrDefault(timestamp, 0) + 1);
	}

	public int getHits(int timestamp) {
		int hits = 0;
		for (int key : map.keySet()) {
			if (timestamp - key < 300) {
				hits += map.get(key);
			}
		}
		return hits;
	}

	public static void main(String[] args) {
		HitCounter hc = new HitCounter();
		hc.hit(1);
		hc.hit(2);
		hc.hit(3);
		System.out.println(hc.getHits(4)); // 3
		hc.hit(300);
		System.out.println(hc.getHits(300)); // 4
		System.out.println(hc.getHits(301)); // 3		
	}
}