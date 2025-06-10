package com.svetanis.datastructures.array.segregate;

import java.util.Arrays;

import com.svetanis.java.base.utils.Print;

// 927. Three Equal Parts

public final class ThreeEqualParts {
	// Time Complexity: O(n)

	public static int[] threeEqualParts(int[] a) {
		int n = a.length;
		int ones = Arrays.stream(a).sum();
		if (ones == 0) {
			return new int[] { 0, n - 1 };
		}
		if (ones % 3 != 0) {
			return new int[] { -1, -1 };
		}
		int count = ones / 3;
		int i = find(a, 1);
		int j = find(a, count + 1);
		int k = find(a, 2 * count + 1);
		while (k < n && a[i] == a[j] && a[j] == a[k]) {
			i++;
			j++;
			k++;
		}
		return k == n ? new int[] { i - 1, j } : new int[] { -1, -1 };
	}

	private static int find(int[] a, int one) {
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			if (sum == one) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 0, 1, 0, 1 };
		Print.print(threeEqualParts(a1)); // 0,3

		int[] a2 = { 1, 1, 0, 1, 1 };
		Print.print(threeEqualParts(a2)); // -1,-1

		int[] a3 = { 1, 1, 0, 0, 1 };
		Print.print(threeEqualParts(a3)); // 0,2
	}
}
