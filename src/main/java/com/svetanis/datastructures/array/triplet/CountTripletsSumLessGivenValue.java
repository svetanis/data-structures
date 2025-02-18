package com.svetanis.datastructures.array.triplet;

import java.util.Arrays;

// 259. 3Sum Smaller

// given array of distinct integers and a sum value
// find count of triplets with sum smaller than target

public final class CountTripletsSumLessGivenValue {
	// Time complexity: O(n^2)

	public static int count(int[] a, int k) {
		Arrays.sort(a);

		int count = 0;
		for (int i = 0; i < a.length; i++) {
			int target = k - a[i];
			count += count(a, target, i);
		}
		return count;
	}

	private static int count(int[] a, int target, int first) {
		int count = 0;
		int left = first + 1;
		int right = a.length - 1;
		while (left < right) {
			int sum = a[left] + a[right];
			if (sum >= target) {
				right--;
			} else {
				count += right - left;
				left++;
			}
		}
		return count;

	}

	public static void main(String[] args) {
		int[] a = { 5, 1, 3, 4, 7 };
		System.out.println(count(a, 12));
	}
}
