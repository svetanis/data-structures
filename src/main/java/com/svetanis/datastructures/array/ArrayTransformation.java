package com.svetanis.datastructures.array;

import java.util.Arrays;
import java.util.List;

// 1243. Array Transformation
//
// Input: a -- an array of ints. Each day, every inner element (not the first, not the last) that is
// smaller than both neighbours grows by 1 and every inner element larger than both shrinks by 1,
// all decided from the array as it stood at the start of that day. Returns the array after the
// day on which nothing changes. On LeetCode: 3 <= a.length <= 100, 1 <= a[i] <= 100. The method
// updates a in place.
//
// The one idea: simulate. The rule for one element reads its two neighbours' OLD values, so each
// round first takes a copy, decides every change from the copy and writes it into a. Without the
// copy, a change to a[i - 1] would leak into the decision for a[i] in the same round. Stop when a
// whole round writes nothing.
//
// Time: O(n * r) -- r rounds, each one pass over n elements plus a copy of n.
// Space: O(n) -- the copy taken each round, and the list returned.

public final class ArrayTransformation {

	public static List<Integer> transformArray(int[] a) {
		boolean updated = true;
		while (updated) {
			updated = false;
			int[] copy = a.clone(); // SNAPSHOT: every decision this round reads the old values
			for (int i = 1; i < copy.length - 1; i++) {
				if (copy[i] > copy[i - 1] && copy[i] > copy[i + 1]) {
					a[i] -= 1; // SHRINK: a local peak
					updated = true;
				}

				if (copy[i] < copy[i - 1] && copy[i] < copy[i + 1]) {
					a[i] += 1; // GROW: a local valley
					updated = true;
				}
			}
		}
		return Arrays.stream(a).boxed().toList();
	}

	public static void main(String[] args) {
		int[] a = { 1, 6, 3, 4, 3, 5 };
		System.out.println(transformArray(a)); // [1, 4, 4, 4, 4, 5]

		int[] a1 = { 6, 2, 3, 4 };
		System.out.println(transformArray(a1)); // [6, 3, 3, 4]
	}
}
