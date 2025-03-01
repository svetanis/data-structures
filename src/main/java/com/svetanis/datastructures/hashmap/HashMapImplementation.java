package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 706. Design HashMap

public final class HashMapImplementation {

	private int[] a;

	public HashMapImplementation() {
		this.a = new int[1000001];
		Arrays.fill(a, -1);
	}

	public void put(int key, int val) {
		this.a[key] = val;
	}

	public int get(int key) {
		return a[key];
	}

	public void remove(int key) {
		this.a[key] = -1;
	}

	public static void main(String[] args) {
		HashMapImplementation hmi = new HashMapImplementation();
		hmi.put(1, 1);
		hmi.put(2, 2);
		System.out.println(hmi.get(1)); // 1
		System.out.println(hmi.get(3)); // -1
		hmi.put(2, 1);
		System.out.println(hmi.get(2)); // 1
		hmi.remove(2);
		System.out.println(hmi.get(2)); // -1
	}
}