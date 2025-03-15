package com.svetanis.datastructures.array;

// 747. Largest Number At Least Twice of Others

public final class LargestNumAtLeastTwiceOfOthers {
	// Time Complexity: O(n)

	public static int dominantIndex(int[] a) {
		int max = 0;
		int second = 0;
		int index = -1;
		for (int i = 0; i < a.length; i++) {
			int curr = a[i];
			if (curr > max) {
				second = max;
				max = curr;
				index = i;
			} else if (curr > second) {
				second = curr;
			}
		}
		return max >= 2 * second ? index : -1;
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 6, 1, 0 };
		System.out.println(dominantIndex(a1)); // 1
		int[] a2 = { 1, 2, 3, 4 };
		System.out.println(dominantIndex(a2)); // -1
	}
}
