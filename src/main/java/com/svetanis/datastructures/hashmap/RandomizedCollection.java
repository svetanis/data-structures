package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

// 381. Insert Delete GetRandom O(1) - Duplicates allowed

public final class RandomizedCollection {
	// Time Complexity: O(1)
	// Space Complexity: O(n)

	private static final Random RAND = new Random();

	public RandomizedCollection() {
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
	}

	private List<Integer> list;
	private Map<Integer, Set<Integer>> map;

	public boolean insert(int val) {
		if (!map.containsKey(val)) {
			map.put(val, new HashSet<>());
		}
		map.get(val).add(list.size());
		list.add(val);
		return map.get(val).size() == 1;
	}

	public boolean remove(int val) {
		if (!map.containsKey(val)) {
			return false;
		}
		int index = map.get(val).iterator().next();
		map.get(val).remove(index);
		int lastIndex = list.size() - 1;
		int lastElement = list.get(lastIndex);
		list.set(index, lastElement);
		map.get(lastElement).add(index);
		map.get(lastElement).remove(lastIndex);
		list.remove(lastIndex);
		if (map.get(val).isEmpty()) {
			map.remove(val);
		}
		return true;
	}

	public int getRandom() {
		int size = list.size();
		int index = RAND.nextInt(list.size());
		return size == 0 ? -1 : list.get(index);
	}

	public static void main(String[] args) {
		RandomizedCollection rs = new RandomizedCollection();
		System.out.println(rs.insert(1)); // true
		System.out.println(rs.insert(1)); // false
		System.out.println(rs.insert(2)); // true
		System.out.println(rs.getRandom()); // 2
		System.out.println(rs.remove(1)); // true
		System.out.println(rs.getRandom()); // 1
	}
}
