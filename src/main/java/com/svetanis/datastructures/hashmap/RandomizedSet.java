package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// 380. Insert Delete GetRandom O(1)
//
// insert(val) adds val if it is not held and returns true if it was added; remove(val)
// removes val if it is held and returns true if it was removed; getRandom() returns a held
// value, each one equally likely. On LeetCode: -2^31 <= val <= 2^31 - 1, at most 2 * 10^5
// calls, getRandom only when something is held, each call O(1) on average.
//
// A list holds the values with no gaps, so a random index is a random value, and a map takes
// each value to its index in the list, so finding one is a single lookup. remove MOVEs the
// last value into val's hole and CUTs the last slot: only the last slot can go without
// shifting anything, and a set has no order to keep. The moved value's map entry is updated
// (REINDEX) BEFORE val's entry is removed (FORGET), because when val is the last value, the
// moved value IS val: REINDEX briefly puts it back and FORGET then removes it. In the other
// order the removed value would stay in the map.
//
// Siblings: none for this problem in this package.
//   RandomizedCollection  LC 381, duplicates allowed: the map holds a set of indexes per value

public final class RandomizedSet {
	// Time complexity: insert, remove, getRandom O(1) on average, two map steps at most each
	// Auxiliary Space: O(n), one list slot and one map entry per value held

	private static final Random RAND = new Random();

	public RandomizedSet() {
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
	}

	private List<Integer> list; // the values, no gaps
	private Map<Integer, Integer> map; // value -> its index in list

	public boolean insert(int val) {
		if (map.containsKey(val)) { // FIND: already held
			return false;
		}
		map.put(val, list.size()); // INDEX: where val is about to go
		list.add(val); // ADD: at the end
		return true;
	}

	public boolean remove(int val) {
		if (!map.containsKey(val)) { // FIND: not held
			return false;
		}
		int index = map.get(val); // FIND: where val sits, the hole
		int last = list.get(list.size() - 1);
		list.set(index, last); // MOVE: the last value into the hole
		map.put(last, index); // REINDEX: the moved value's entry follows it
		list.remove(list.size() - 1); // CUT: the last slot, nothing shifts
		map.remove(val); // FORGET: after REINDEX, in case last == val
		return true;
	}

	public int getRandom() {
		int index = RAND.nextInt(list.size()); // PICK: nextInt(n) is 0..n-1
		return list.get(index);
	}

	public static void main(String[] args) {
		RandomizedSet rs = new RandomizedSet();
		System.out.println(rs.insert(1)); // true
		System.out.println(rs.remove(2)); // false
		System.out.println(rs.insert(2)); // true
		System.out.println(rs.getRandom()); // 1 or 2
		System.out.println(rs.remove(1)); // true
		System.out.println(rs.insert(2)); // false
		System.out.println(rs.getRandom()); // 2
	}
}
