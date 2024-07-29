package com.svetanis.datastructures.array.quadruple;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Maps.freqMap;

import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableList;

// given 4 sorted arrays of distinct integers of the same size and a target sum
// count all quadruples from all the four arrays whose sum is equal to x
// quadruple has an element from each of the four arrays

public final class CountQuadrupletsGivenSumHashing {
	// Time Complexity: O(n^2)

	public static int count(int[] a1, int[] a2, int[] a3, int[] a4, int target) {
		int n = a1.length;
		int count = 0;
		Map<Integer, Integer> map = freqMap(sums(a1, a2));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int sum = a3[i] + a4[j];
				count += map.getOrDefault(target - sum, 0);
			}
		}
		return count;
	}

	private static ImmutableList<Integer> sums(int[] a1, int[] a2) {
		int n = a1.length;
		List<Integer> list = newArrayList();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int sum = a1[i] + a2[j];
				list.add(sum);
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
