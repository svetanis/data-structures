package com.svetanis.datastructures.array.rotate;

// 1752. Check if Array Is Sorted and Rotated

public final class IsSortedAndRotated {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static boolean check(int[] a) {
		int n = a.length;
		if (n <= 1) {
			return true;
		}
		int inversionCount = 0;
		for (int i = 0; i < n; i++) {
			int next = (i + 1) % n;
			if (a[i] > a[next]) {
				inversionCount += 1;
				if (inversionCount > 1) {
					return false;
				}
			}
		}
		return inversionCount <= 1;
	}

	public static void main(String[] args) {
		int[] a = { 3, 4, 5, 1, 2 };
		System.out.println(check(a)); // true
		int[] a1 = { 2, 1, 3, 4 };
		System.out.println(check(a1)); // false
		int[] a2 = { 1, 2, 3 };
		System.out.println(check(a2)); // true
	}
}