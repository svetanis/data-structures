package com.svetanis.datastructures.hashmap.cache;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public final class LruSimpleTest {

	public static void main(String[] args) {
		List<Integer> list = newArrayList();
		LruSimple cache1 = new LruSimple(2);
		cache1.put(1, 1);
		cache1.put(2, 2);
		list.add(cache1.get(1));
		cache1.put(3, 3);
		list.add(cache1.get(2));
		cache1.put(4, 4);
		list.add(cache1.get(1));
		list.add(cache1.get(3));
		list.add(cache1.get(4));
		System.out.println(list); // 1, -1, -1, 3, 4

		list.clear();
		LruSimple cache2 = new LruSimple(2);
		list.add(cache2.get(5));
		cache2.put(2, 6);
		cache2.put(5, 4);
		list.add(cache2.get(5));
		System.out.println(list); // -1 4

		list.clear();
		LruSimple cache3 = new LruSimple(3);
		cache3.put(1, 3);
		cache3.put(2, 10);
		cache3.put(3, 1);
		cache3.put(4, 7);
		list.add(cache3.get(1));
		list.add(cache3.get(3));
		list.add(cache3.get(2));
		System.out.println(list); // -1 1 10
	}
}
