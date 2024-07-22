package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;
import static java.util.Arrays.sort;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.utils.Triplet;

// given array of distinct integers and a sum value
// find all triplets with sum smaller than target

public final class AllTripletsSumLessGivenValue {

	public static ImmutableList<Triplet<Integer, Integer, Integer>> triplets(int[] a, int k) {
		// Time complexity: O(n log n)
		sort(a);

		List<Triplet<Integer, Integer, Integer>> list = newArrayList();
		for (int i = 0; i < a.length; i++) {
			int target = k - a[i];
			list.addAll(triplets(a, target, i));
		}
		return newList(list);
	}

	private static ImmutableList<Triplet<Integer, Integer, Integer>> triplets(int[] a, int target, int first) {
		int left = first + 1;
		int right = a.length - 1;
		List<Triplet<Integer, Integer, Integer>> list = newArrayList();
		while (left < right) {
			int sum = a[left] + a[right];
			if (sum >= target) {
				right--;
			} else {
				for (int i = right; i > left; i--) {
					list.add(Triplet.build(a[first], a[left], a[i]));
				}
				left++;
			}
		}
		return newList(list);
	}

	public static void main(String[] args) {
		int[] a = { 5, 1, 3, 4, 7 };
		printLines(triplets(a, 12));
	}
}
