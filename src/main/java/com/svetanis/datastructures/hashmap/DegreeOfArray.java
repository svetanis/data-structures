package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 697. Degree of an Array

public final class DegreeOfArray {
	// Time Complexity: O(n + k log k)
	// Space Complexity: O(k)

	public static int degreeOfArray(int[] a) {
		int n = a.length;
		int degree = 0;
		int[] counts = new int[n];
		int[] left = new int[n];
		int[] right = new int[n];
		Arrays.fill(left, -1);
		Arrays.fill(right, -1);
		for (int i = 0; i < n; i++) {
			int num = a[i];
			counts[num]++;
			degree = Math.max(degree, counts[num]);
			if (left[num] == -1) {
				left[num] = i;
			}
			right[num] = i;
		}
		int min = Integer.MAX_VALUE;
		for (int num : a) {
			if (counts[num] == degree) {
				int len = right[num] - left[num] + 1;
				min = Math.min(min, len);
			}
		}
		return min;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 2, 3, 1 };
		System.out.println(degreeOfArray(a1)); // 2

		int[] a2 = { 1, 2, 2, 3, 1, 4, 2 };
		System.out.println(degreeOfArray(a2)); // 6
	}
}