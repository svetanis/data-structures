package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

// 379. Design Phone Directory

public final class PhoneDirectoryQueue {

	private int total;
	private Queue<Integer> available;
	private Set<Integer> visited;

	public PhoneDirectoryQueue(int max) {
		this.available = new LinkedList<>();
		this.visited = new HashSet<>();
		this.total = max;
		for (int i = 0; i < max; i++) {
			available.add(i);
		}
	}

	public int get() {
		if (visited.size() == total) {
			return -1;
		}
		int slot = available.poll();
		visited.add(slot);
		return slot;
	}

	public boolean check(int num) {
		return !visited.contains(num);
	}

	public void release(int num) {
		if (visited.contains(num)) {
			available.add(num);
			visited.remove(num);
		}
	}

	public static void main(String[] args) {
		PhoneDirectoryQueue pd = new PhoneDirectoryQueue(3);
		System.out.println(pd.get()); // 0
		System.out.println(pd.get()); // 1
		System.out.println(pd.check(2)); // true
		System.out.println(pd.get()); // 2
		System.out.println(pd.check(2)); // false
		pd.release(2);
		System.out.println(pd.check(2)); // true
	}
}