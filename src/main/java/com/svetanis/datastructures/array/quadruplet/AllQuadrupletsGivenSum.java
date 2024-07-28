package com.svetanis.datastructures.array.quadruplet;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;
import static java.util.Arrays.sort;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.utils.Quadruplet;

// given an array of unsorted numbers and a target
// find all unique quadruplets whose sum is equal 
// to the target number

public final class AllQuadrupletsGivenSum {
	// Time complexity: O(n^3)

	public static ImmutableList<Quadruplet<Integer, Integer, Integer, Integer>> quadruplets(int[] a, int k) {
		sort(a);
		List<Quadruplet<Integer, Integer, Integer, Integer>> list = newArrayList();
		for (int i = 0; i < a.length - 3; i++) {
			// skip same element to avoid duplicates
			if (i > 0 && a[i] == a[i - 1]) {
				continue;
			}
			for (int j = i + 1; j < a.length - 2; j++) {
				// skip same element to avoid duplicates
				if (j > i + 1 && a[j] == a[j - 1]) {
					continue;
				}
				int target = k - a[i] - a[j];
				list.addAll(qdp(a, target, i, j));
			}
		}
		return newList(list);
	}

	private static ImmutableList<Quadruplet<Integer, Integer, Integer, Integer>> qdp(int[] a, int k, int i, int j) {
		int left = j + 1;
		int right = a.length - 1;
		List<Quadruplet<Integer, Integer, Integer, Integer>> list = newArrayList();
		while (left < right) {
			int sum = a[left] + a[right];
			if (sum < k) {
				left++;
			} else if (sum > k) {
				right--;
			} else {
				list.add(Quadruplet.build(a[i], a[j], a[left], a[right]));
				left++;
				right--;
				// skip same element to avoid duplicates
				while (left < right && a[left] == a[left - 1]) {
					left++;
				}
				while (left < right && a[right] == a[right + 1]) {
					right--;
				}
			}
		}
		return newList(list);
	}

	public static void main(String[] args) {
		int[] a = { 4, 1, 2, -1, 1, -3 };
		printLines(quadruplets(a, 1));

		int[] a1 = { 2, 0, -1, 1, -2, 2 };
		printLines(quadruplets(a1, 2));
	}
}
