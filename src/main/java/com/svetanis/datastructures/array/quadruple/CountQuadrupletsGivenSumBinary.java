package com.svetanis.datastructures.array.quadruple;

// given 4 sorted arrays of distinct integers of the same size and a target sum
// count all quadruples from all the four arrays whose sum is equal to x
// quadruple has an element from each of the four arrays

public final class CountQuadrupletsGivenSumBinary {
	// Time Complexity: O(n^3 log n)

	public static int count(int[] a1, int[] a2, int[] a3, int[] a4, int target) {
		int n = a1.length;
		int count = 0;
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				for (int z = 0; z < n; z++) {
					int sum = a1[x] + a2[y] + a3[z];
					if (isBinary(a4, 0, n - 1, target - sum)) {
						count++;
					}
				}
			}
		}
		return count;
	}

	private static boolean isBinary(int[] a, int start, int end, int x) {
		// O(log n)
		if (end < start) {
			return false;
		}
		int mid = start + (end - start) / 2;
		if (a[mid] == x) {
			return true;
		} else if (x > a[mid]) {
			return isBinary(a, mid + 1, end, x);
		} else {
			return isBinary(a, start, mid - 1, x);
		}
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 4, 5, 6 };
		int[] a2 = { 2, 3, 7, 8 };
		int[] a3 = { 1, 4, 6, 10 };
		int[] a4 = { 2, 4, 7, 8 };
		System.out.println(count(a1, a2, a3, a4, 30));
	}
}
