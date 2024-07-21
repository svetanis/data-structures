package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Maps.filterValues;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.utils.Maps.freqMap;
import static java.lang.Math.abs;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

// given an array of n integers,
// find all pairs of positive and
// negative value of a number
// that exists in the array

public final class PairsPosNegHashing {
	// Time Complexity: O(n)

	public static ImmutableList<Pair<Integer, Integer>> pairs(List<Integer> list) {
		List<Integer> transformed = transform(list, i -> abs(i));
		Map<Integer, Integer> map = freqMap(transformed);
		Map<Integer, Integer> filtered = filterValues(map, v -> v == 2);
		Set<Integer> set = filtered.keySet();
		return transform(set, p -> pair(p));
	}

	private static Pair<Integer, Integer> pair(int num) {
		int neg = -1 * num;
		return Pair.build(num, neg);
	}

	public static void main(String[] args) {
		List<Integer> list = newArrayList(4, 8, 9, -4, 1, -1, -8, -9, 5);
		System.out.println(pairs(list));
	}
}
