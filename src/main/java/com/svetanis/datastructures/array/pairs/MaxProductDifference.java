package com.svetanis.datastructures.array.pairs;

// 1913. Maximum Product Difference Between Two Pairs

public final class MaxProductDifference {
	// Time Complexity: O(n)

	private static final int MIN = Integer.MIN_VALUE;
	private static final int MAX = Integer.MAX_VALUE;

	public static int maxDiff(int[] a) {
		int lp = largest(a);
		int sp = smallest(a);
		return lp - sp;
	}

	private static int smallest(int[] a) {
		int first = MAX;
		int second = MAX;
		for (int i = 0; i < a.length; ++i) {
			if (a[i] < first) {
				second = first;
				first = a[i];
			} else if (a[i] < second) {
				second = a[i];
			}
		}
		return first * second;
	}

	private static int largest(int[] a) {
		int first = MIN;
		int second = MIN;
		for (int i = 0; i < a.length; ++i) {
			if (a[i] > first) {
				second = first;
				first = a[i];
			} else if (a[i] > second) {
				second = a[i];
			}
		}
		return first * second;
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 6, 2, 7, 4 };
		System.out.println(maxDiff(a1)); // 34
		int[] a2 = { 4, 2, 5, 9, 7, 4, 8 };
		System.out.println(maxDiff(a2)); // 64
	}
}