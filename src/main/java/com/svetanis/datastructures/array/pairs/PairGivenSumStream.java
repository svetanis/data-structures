package com.svetanis.datastructures.array.pairs;

import java.util.HashMap;
import java.util.Map;

// 170. Two Sum III - Data structure design

public final class PairGivenSumStream {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private Map<Integer, Integer> map = new HashMap<>();

	public void add(int number) {
		map.put(number, map.getOrDefault(number, 0) + 1);
	}

	public boolean find(int value) {
		for (int key : map.keySet()) {
			int count = map.get(key);
			int complement = value - key;
			if (map.containsKey(complement)) {
				if (complement != key || count > 1) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		PairGivenSumStream pgs = new PairGivenSumStream();
		pgs.add(1);
		pgs.add(3);
		pgs.add(5);
		System.out.println(pgs.find(4)); // true
		pgs.add(1);
		System.out.println(pgs.find(2)); // true
	}
}
