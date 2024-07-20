package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Arrays.max;

import java.util.List;
import com.svetanis.java.base.Pair;

// given two arrays of positive and distinct integers.
// find a pair from the two arrays with max sum.
// the pair should contain one element from both arrays

public final class PairMaxSumTwoArrays {
	// Time Complexity: O(n)

	public static Pair<Integer, Integer> maxPair(List<Integer> list1, List<Integer> list2) {
		int max1 = max(list1);
		int max2 = max(list2);
		return Pair.build(max1, max2);
	}

	public static void main(String[] args) {
		List<Integer> list1 = newArrayList(10, 2, 3);
		List<Integer> list2 = newArrayList(3, 4, 7);
		System.out.println(maxPair(list1, list2));
	}
}
