package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class RandomizedSet {

	private static final Random RAND = new Random();

	public RandomizedSet() {
		this.list = new ArrayList<>();
		this.map = new HashMap<>();
	}

	private List<Integer> list;
	private Map<Integer, Integer> map;

	public boolean insert(int val) {
		if (map.containsKey(val)) {
			return false;
		}
		map.put(val, list.size());
		list.add(val);
		return true;
	}

	public boolean remove(int val) {
		if (!map.containsKey(val)) {
			return false;
		}
		int index = map.get(val);
		int last = list.get(list.size() - 1);
		list.set(index, last);
		map.put(last, index);
		list.remove(list.size() - 1);
		map.remove(val);
		return true;
	}

	public int getRandom() {
		int index = RAND.nextInt(list.size());
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
