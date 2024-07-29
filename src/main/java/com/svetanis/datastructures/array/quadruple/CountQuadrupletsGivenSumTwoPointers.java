package com.svetanis.datastructures.array.quadruple;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

// given 4 sorted arrays of distinct integers of the same size and a target sum
// count all quadruples from all the four arrays whose sum is equal to x
// quadruple has an element from each of the four arrays

public final class CountQuadrupletsGivenSumTwoPointers {
	// Time Complexity: O(n^3 log n)

	public static int count(int[] a1, int[] a2, int[] a3, int[] a4, int target) {
		int n = a1.length;
		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int sum = a1[i] + a2[j];
				count += pairs(a3, a4, target - sum).size();
			}
		}
		return count;
	}

	private static ImmutableList<Pair<Integer, Integer>> pairs(int[] a1, int[] a2, int k) {
		int n = a1.length;
		int m = a2.length;
		int left = 0;
		int right = m - 1;

		List<Pair<Integer, Integer>> list = newArrayList();
		while (left < n && right > 0) {
			int sum = a1[left] + a2[right];
			if (sum == k) {
				list.add(Pair.build(a1[left], a2[right]));
				++left;
				--right;
			} else if (sum < k) {
				left++;
			} else { // sum > k
				right--;
			}
		}
		return newList(list);
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 4, 5, 6 };
		int[] a2 = { 2, 3, 7, 8 };
		int[] a3 = { 1, 4, 6, 10 };
		int[] a4 = { 2, 4, 7, 8 };
		System.out.println(count(a1, a2, a3, a4, 30));
	}
}
