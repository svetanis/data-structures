package com.svetanis.datastructures.hashmap;

// 1394. Find Lucky Integer in an Array

public final class LuckyIntegerInArray {
	// Time Complexity: O(n)

	public static int luckyNum(int[] a) {
		int[] counts = counts(a);
		for (int index = 500; index > 0; index--) {
			if (index == counts[index]) {
				return index;
			}
		}
		return -1;
	}

	private static int[] counts(int[] a) {
		int[] counts = new int[502];
		for (int num : a) {
			counts[num]++;
		}
		return counts;
	}

	public static void main(String[] args) {
		int[] a1 = { 2, 2, 3, 4 };
		System.out.println(luckyNum(a1)); // 2
		int[] a2 = { 1, 2, 2, 3, 3, 3 };
		System.out.println(luckyNum(a2)); // 3
		int[] a3 = { 2, 2, 2, 3, 3 };
		System.out.println(luckyNum(a3)); // -1
	}
}