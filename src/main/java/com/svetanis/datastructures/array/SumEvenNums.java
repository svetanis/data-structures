package com.svetanis.datastructures.array;

import com.svetanis.java.base.utils.Print;

// 985. Sum of Even Numbers After Queries

public final class SumEvenNums {
	// Time Complexity: O(n + m)
	// Space Complexity: O(1)

	public static int[] sumEvenAfterQueries(int[] nums, int[][] queries) {
		int n = queries.length;
		int sum = evenSum(nums);
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			int val = queries[i][0], index = queries[i][1];
			if (nums[index] % 2 == 0) {
				sum -= nums[index];
			}
			nums[index] += val;
			if (nums[index] % 2 == 0) {
				sum += nums[index];
			}
			a[i] = sum;
		}
		return a;
	}

	private static int evenSum(int[] a) {
		int sum = 0;
		for (int num : a) {
			if (num % 2 == 0) {
				sum += num;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 4 };
		int[][] q = { { 1, 0 }, { -3, 1 }, { -4, 0 }, { 2, 3 } };
		Print.print(sumEvenAfterQueries(a, q)); // 8 6 2 4
	}
}
