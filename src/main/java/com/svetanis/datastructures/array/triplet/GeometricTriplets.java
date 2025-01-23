package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.utils.Triplet;

// Given a sorted array of distinct positive integers, 
// print all triplets that forms Geometric Progression
// with integral common ratio.

public final class GeometricTriplets {
	// Time complexity: O(n^2)

	public static ImmutableList<Triplet<Integer, Integer, Integer>> triplets(int[] a) {
		int n = a.length;
		// fix every element as mid element
		List<Triplet<Integer, Integer, Integer>> list = newArrayList();
		for (int i = 1; i < n - 1; i++) {
			int left = i - 1;
			int right = i + 1;
			while (left >= 0 && right <= n - 1) {
				boolean one = a[i] % a[left] == 0;
				boolean two = a[right] % a[i] == 0;
				boolean three = a[i] / a[left] == a[right] / a[i];
				while (left >= 0 && right <= n - 1 && one && two && three) {
					list.add(Triplet.build(a[left], a[i], a[right]));
					right++;
					left--;
				}

				if (left < 0 || right >= n) {
					break;
				}

				if (a[i] % a[left] == 0 && a[right] % a[i] == 0) {
					if (a[i] / a[left] < a[right] / a[i]) {
						left--;
					} else {
						right++;
					}
				} else if (a[i] % a[left] == 0) {
					right++;
				} else {
					left--;
				}
			}
		}
		return copyOf(list);
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 4, 16 };
		System.out.println(triplets(a1));

		int[] a2 = { 1, 2, 6, 10, 18, 54 };
		System.out.println(triplets(a2));

		int[] a3 = { 2, 8, 10, 15, 16, 30, 32, 64 };
		System.out.println(triplets(a3));

		int[] a4 = { 1, 2, 6, 18, 36, 54 };
		System.out.println(triplets(a4));

		int[] a5 = { 1, 2, 3, 6, 18, 22 };
		System.out.println(triplets(a5));
	}
}