package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.util.Arrays.sort;

import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.utils.Triplet;

// Given array of unsorted numbers, find all unique triplets in it that add up to zero.

// fix the first element one by one and
// find the other two elements
// to find the other two elements, start two index variables
// from two corners of the array and move them toward each other

public final class AllUniqueTripletsGivenSumSorted {

	public static ImmutableList<Triplet<Integer, Integer, Integer>> triplets(int[] a, int target) {
		// Time Complexity: O(n^2)

		sort(a);
		Set<Triplet<Integer, Integer, Integer>> set = newHashSet();
		for (int i = 0; i < a.length - 2; i++) {
			// skip same element to avoid duplicate triplets
			if (i > 0 && a[i - 1] == a[i]) {
				continue;
			}
			set.addAll(triplets(a, target, i));
		}
		return newList(set);
	}

	private static ImmutableList<Triplet<Integer, Integer, Integer>> triplets(int[] a, int target, int first) {
		int left = first + 1;
		int right = a.length - 1;
		Set<Triplet<Integer, Integer, Integer>> set = newHashSet();
		while (left < right) {
			int sum = a[first] + a[left] + a[right];
			if (sum == target) {
				set.add(Triplet.build(a[first], a[left], a[right]));
				left++;
				right--;
				// skip same element to avoid duplicate triplets
				while (left < right && a[left] == a[left - 1]) {
					left++;
				}
				while (left < right && a[right] == a[right + 1]) {
					right--;
				}
			} else if (sum < target) {
				left++;
			} else {
				right--;
			}
		}
		return newList(set);
	}

	public static void main(String[] args) {
		int[] a1 = { -2, 2, 0, -1, 1 };
		System.out.println(triplets(a1, 0));

		int[] a2 = { 10, 3, -4, 1, -6, 9 };
		System.out.println(triplets(a2, 0));

		int[] a3 = { -31013930, -31013930, 9784175, 21229755 };
		System.out.println(triplets(a3, 0));
		
		int[] a4 = { -3, 0, 1, 2, -1, 1, -2 };
		System.out.println(triplets(a4, 0));

		int[] a5 = { -5, 2, -1, -2, 3 };
		System.out.println(triplets(a5, 0));
	}
}
