package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Sets.newSet;
import static java.util.Arrays.sort;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.svetanis.java.base.utils.Triplet;

//Given array of unsorted numbers, find all unique triplets in it that add up to zero.

//fix the first element one by one and
//find the other two elements
//to find the other two elements, start two index variables
//from two corners of the array and move them toward each other

public final class AllUniqueTripletsGivenSumSortedSimple {

	public static ImmutableSet<Triplet<Integer, Integer, Integer>> triplets(int[] a, int target) {
		// time complexity: O(n^2)
		int n = a.length;

		sort(a);
		Set<Triplet<Integer, Integer, Integer>> set = newHashSet();
		for (int i = 0; i < n - 2; ++i) {
			int left = i + 1;
			int right = n - 1;

			while (left < right) {
				int sum = a[i] + a[left] + a[right];
				if (sum == target) {
					set.add(Triplet.build(a[i], a[left], a[right]));
					left++;
					right--;
				} else if (sum < target) {
					left++;
				} else {
					right--;
				}
			}
		}
		return newSet(set);
	}

	public static void main(String[] args) {
		int[] a = { -2, 2, 0, -1, 1 };
		System.out.println(triplets(a, 0));

		int[] a1 = { 10, 3, -4, 1, -6, 9 };
		System.out.println(triplets(a1, 0));
	}
}