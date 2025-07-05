package com.svetanis.datastructures.array.shuffle;

import static com.svetanis.java.base.utils.Print.print;

// 1470. Shuffle the Array

public final class ShuffleArray1470 {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] shuffle(int[] a, int n) {
		int[] shuffled = new int[2 * n];
		for (int index = 0, k = 0; index < n; index++) {
			shuffled[k++] = a[index];
			shuffled[k++] = a[index + n];
		}
		return shuffled;
	}

	public static int[] shuffle2(int[] a, int k) {
		int n = a.length;
		int left = 0;
		int right = k;
		int[] shuffled = new int[n];
		for (int index = 0; index < n; index++) {
			shuffled[index] = index % 2 == 0 ? a[left++] : a[right++];
		}
		return shuffled;
	}

	public static void main(String[] args) {
		int[] a1 = { 2, 5, 1, 3, 4, 7 };
		print(shuffle(a1, 3)); // 2 3 5 4 1 7
		int[] a2 = { 1, 2, 3, 4, 4, 3, 2, 1 };
		print(shuffle(a2, 4)); // 1 4 2 3 3 2 4 1
		int[] a3 = { 1, 1, 2, 2 };
		print(shuffle(a3, 2)); // 1 2 1 2
	}
}