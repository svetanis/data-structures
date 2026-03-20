package com.svetanis.datastructures.hashmap;

// 1426. Counting Elements

public final class CountingElements {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int countElements(int[] a) {
		int[] counts = new int[1002];
		for (int num : a) {
			counts[num] += 1;
		}
		int count = 0;
		for (int num : a) {
			int next = num + 1;
			if (counts[next] > 0) {
				count += 1;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3 };
		System.out.println(countElements(a1)); // 2

		int[] a2 = { 1, 1, 3, 3, 5, 5, 7, 7 };
		System.out.println(countElements(a2)); // 0
	}
}