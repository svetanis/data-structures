package com.svetanis.datastructures.array.pairs;

import static java.util.Arrays.sort;

// given a sorted array of integers and a number k
// count pairs whose difference is less than k

public final class CountPairsLessGivenDiff {

	public static int count(int[] a, int k) {
		sort(a);

		int count = 0;
		int n = a.length;
		for (int i = 0; i < n; i++) {
			int j = i + 1;
			while (j < n && a[j] - a[i] < k) {
				count++;
				j++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 10, 4, 2 };
		System.out.println(count(a1, 3));

		int[] a2 = { 1, 8, 7 };
		System.out.println(count(a2, 7));
	}
}
