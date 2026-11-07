package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

// 381. Insert Delete GetRandom O(1) - Duplicates allowed
//
// insert(val) adds a copy of val and returns true if val was not held before; remove(val)
// removes one copy and returns true if val was held; getRandom() returns a held value, each
// copy equally likely, so a value held twice comes up twice as often. On LeetCode:
// -2^31 <= val <= 2^31 - 1, at most 2 * 10^5 calls, getRandom only when something is held,
// each call O(1) on average.
//
// A list holds every copy with no gaps, so a random index is a random copy. A map takes each
// value to the SET of indexes where its copies sit. remove takes any one of val's indexes,
// MOVEs the last element into it and CUTs the last slot, so nothing shifts. The moved
// element's set then gains the hole's index BEFORE it loses the last index: when the hole is
// the last slot itself, the two are the same index, and only this order leaves it removed.
// The sets are LinkedHashSets, because remove asks a set for "any one index" with
// iterator().next(): a plain HashSet answers by walking past its empty buckets, and a set that
// once held many indexes keeps its large table, so one remove could cost up to that old size.
//
// Siblings: none for this problem in this package.
//   RandomizedSet  LC 380, the same list and map without duplicates: one index per value

public final class RandomizedCollection {
	// Time complexity: insert, remove, getRandom O(1) on average, a few map and set steps each
	// Auxiliary Space: O(n), one list slot and one set entry per copy held

	private static final Random RAND = new Random();

	public RandomizedCollection() {
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
	}

	private List<Integer> list; // every copy, no gaps
	private Map<Integer, Set<Integer>> map; // value -> the indexes of its copies in list

	public boolean insert(int val) {
		if (!map.containsKey(val)) {
			map.put(val, new LinkedHashSet<>());
		}
		map.get(val).add(list.size()); // INDEX: where the copy is about to go
		list.add(val); // ADD: at the end
		return map.get(val).size() == 1; // FIND: true if this is the only copy
	}

	public boolean remove(int val) {
		if (!map.containsKey(val)) { // FIND: is val held at all
			return false;
		}
		int index = map.get(val).iterator().next(); // FIND: any one copy's index, the hole
		map.get(val).remove(index);
		int lastIndex = list.size() - 1;
		int lastElement = list.get(lastIndex);
		list.set(index, lastElement); // MOVE: the last element into the hole
		map.get(lastElement).add(index); // REINDEX: gain the hole first ...
		map.get(lastElement).remove(lastIndex); // ... then lose the last slot, in case they are equal
		list.remove(lastIndex); // CUT: the last slot, nothing shifts
		if (map.get(val).isEmpty()) {
			map.remove(val); // FORGET: no copies left
		}
		return true;
	}

	public int getRandom() {
		return list.get(RAND.nextInt(list.size())); // PICK: nextInt(n) is 0..n-1
	}

	public static void main(String[] args) {
		RandomizedCollection rs = new RandomizedCollection();
		System.out.println(rs.insert(1)); // true
		System.out.println(rs.insert(1)); // false
		System.out.println(rs.insert(2)); // true
		System.out.println(rs.getRandom()); // 1 or 2, and 1 twice as often
		System.out.println(rs.remove(1)); // true
		System.out.println(rs.getRandom()); // 1 or 2
	}
}
