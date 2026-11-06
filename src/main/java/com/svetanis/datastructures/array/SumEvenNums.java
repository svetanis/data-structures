package com.svetanis.datastructures.array;

import java.util.Arrays;

// 985. Sum of Even Numbers After Queries
//
// Input: an integer array nums and queries[i] = { val, index }. Query i adds val to nums[index],
// then asks for the sum of the even numbers in nums. Returns one answer per query. The queries run
// in order and nums is changed in place. On LeetCode: 1 <= nums.length <= 10^4, -10^4 <= nums[i] <=
// 10^4, 1 <= queries.length <= 10^4, -10^4 <= val <= 10^4, 0 <= index < nums.length.
//
// The one idea: do not re-add the whole array after each query. Keep sum, the sum of the even
// numbers right now. A query changes exactly one number, so only that number can enter or leave
// the even sum: if it was even, take it out before the change; if it is even after the change, put
// it in. Everything else stays counted exactly as before.
//
// Time: O(n + m) -- n to build the first even sum, then O(1) per query for m queries.
// Space: O(1) -- besides the answer array of m numbers.

public final class SumEvenNums {

	public static int[] sumEvenAfterQueries(int[] nums, int[][] queries) {
		int n = queries.length;
		int sum = evenSum(nums); // SUM: of the even numbers before any query
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			int val = queries[i][0], index = queries[i][1];
			if (nums[index] % 2 == 0) {
				sum -= nums[index]; // TAKE OUT: the old value, if it was counted
			}
			nums[index] += val;
			if (nums[index] % 2 == 0) {
				sum += nums[index]; // PUT IN: the new value, if it is even
			}
			a[i] = sum; // RECORD: the answer to query i
		}
		return a;
	}

	private static int evenSum(int[] a) {
		int sum = 0;
		for (int num : a) {
			if (num % 2 == 0) { // EVEN: also true for negative evens, as -4 % 2 is 0
				sum += num;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 4 };
		int[][] q = { { 1, 0 }, { -3, 1 }, { -4, 0 }, { 2, 3 } };
		System.out.println(Arrays.toString(sumEvenAfterQueries(a, q))); // [8, 6, 2, 4]
	}
}
