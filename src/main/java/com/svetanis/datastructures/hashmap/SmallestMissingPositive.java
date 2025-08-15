package com.svetanis.datastructures.hashmap;

// 2598. Smallest Missing Non-negative Integer After Operations

public final class SmallestMissingPositive {
	// Time Complexity: O(n)

	public static int smp(int[] a, int val) {
		int[] countMod = new int[val];
		for (int num : a) {
			int reminder = num % val;
			int positive = (reminder + val) % val;
			countMod[positive]++;
		}
		int min = 0;
		for (int i = 0; i < val; i++) {
			if (countMod[i] < countMod[min]) {
				min = i;
			}
		}
		return val * countMod[min] + min;
	}

	public static int smp2(int[] a, int val) {
		int[] countMod = new int[val];
		for (int num : a) {
			int reminder = num % val;
			int positive = (reminder + val) % val;
			countMod[positive]++;
		}
		for (int i = 0;; i++) {
			if (countMod[i % val] == 0) {
				return i;
			}
			countMod[i % val]--;
		}
	}

	public static void main(String[] args) {
		int[] a = { 1, -10, 7, 13, 6, 8 };
		System.out.println(smp(a, 5)); // 4

		int[] a1 = { 1, -10, 7, 13, 6, 8 };
		System.out.println(smp(a1, 7)); // 2

		int[] a2 = { 1, 3, 5, 7 };
		System.out.println(smp(a2, 2)); // 0

		int[] a3 = { 3, 0, 3, 2, 4, 2, 1, 1, 0, 4 };
		System.out.println(smp(a3, 5)); // 10

	}
}
