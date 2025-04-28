package com.svetanis.datastructures.hashmap;

// 705. Design HashSet

public final class HashSetImplementation {

	private boolean[] data;

	public HashSetImplementation() {
		data = new boolean[1000001];
	}

	public void add(int key) {
		data[key] = true;
	}

	public void remove(int key) {
		data[key] = false;
	}

	public boolean contains(int key) {
		return data[key];
	}

	public static void main(String[] args) {
		HashSetImplementation hsi = new HashSetImplementation();
		hsi.add(1);
		hsi.add(2);
		System.out.println(hsi.contains(1)); // true
		System.out.println(hsi.contains(3)); // false
		hsi.add(2);
		System.out.println(hsi.contains(2)); // true
		hsi.remove(2);
		System.out.println(hsi.contains(2)); // false
	}
}