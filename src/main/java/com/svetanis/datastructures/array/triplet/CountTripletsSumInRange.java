package com.svetanis.datastructures.array.triplet;

import static java.util.Arrays.sort;

// given array of distinct integers and a range [a, b]
// count the number of triplets with sum in the range

public final class CountTripletsSumInRange {
	// Time complexity: O(n^2)

	public static int countInRange(int[] a, int left, int right) {
		sort(a);

		int above = count(a, right);
		int below = count(a, left - 1);
		return above - below;
	}

	public static int count(int[] a, int k) {
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
			if (sum > target) {
				right--;
			} else {
				count += right - left;
				left++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a = { 2, 7, 5, 3, 8, 4, 1, 9 };
		System.out.println(countInRange(a, 8, 16));
	}
}
