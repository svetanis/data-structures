package com.svetanis.datastructures.hashmap;

// 41. First Missing Positive

public final class FirstMissingPositive {
	// Time Complexity: O(n)

	public static int firstMissingPositive(int[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			while (a[i] > 0 && a[i] < n && a[i] != a[a[i] - 1]) {
				swap(a, i, a[i] - 1);
			}
		}

		for (int i = 0; i < n; i++) {
			if (a[i] != i + 1) {
				return i + 1;
			}
		}
		return n + 1;
	}

	private static void swap(int[] a, int left, int right) {
		int temp = a[left];
		a[left] = a[right];
		a[right] = temp;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 0 };
		System.out.println(firstMissingPositive(a1)); // 3
		int[] a2 = { 3, 4, -1, 1 };
		System.out.println(firstMissingPositive(a2)); // 2
		int[] a3 = { 7, 8, 9, 11, 12 };
		System.out.println(firstMissingPositive(a3)); // 1
	}
}