package com.svetanis.datastructures.array;

// 1535. Find the Winner of an Array Game

public final class WinnerOfArrayGame {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int winner(int[] a, int k) {
		int max = a[0];
		for (int i = 1, count = 0; i < a.length; i++) {
			if (max < a[i]) {
				max = a[i];
				count = 1;
			} else {
				count++;
			}
			if (count == k) {
				break;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int[] a1 = { 2, 1, 3, 5, 4, 6, 7 };
		System.out.println(winner(a1, 2)); // 5

		int[] a2 = { 3, 2, 1 };
		System.out.println(winner(a2, 10)); // 3
	}
}
