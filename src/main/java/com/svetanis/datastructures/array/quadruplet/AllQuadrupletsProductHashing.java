package com.svetanis.datastructures.array.quadruplet;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Maps.checkedPut;
import static com.svetanis.java.base.utils.Print.printLines;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;
import com.svetanis.java.base.utils.Quadruplet;

// given an array of unsorted distinct integers
// find pairs (a, b) and (c, d) such that  
// a * b == c * d

public final class AllQuadrupletsProductHashing {
	// Time Complexity: O(n^2)

	public static ImmutableList<Quadruplet<Integer, Integer, Integer, Integer>> quadruplets(int[] a) {
		int n = a.length;
		Map<Integer, Pair<Integer, Integer>> map = newHashMap();
		Set<Quadruplet<Integer, Integer, Integer, Integer>> set = newHashSet();
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				int prod = a[i] * a[j];
				if (map.containsKey(prod)) {
					Pair<Integer, Integer> p = map.get(prod);
					set.add(Quadruplet.build(a[i], a[j], p.getLeft(), p.getRight()));
				} else {
					checkedPut(map, prod, Pair.build(a[i], a[j]));
				}
			}
		}
		return newList(set);
	}

	public static void main(String[] args) {
		int[] a = { 3, 4, 7, 1, 2, 9, 8 };
		printLines(quadruplets(a));

		int[] a1 = { 1, 6, 3, 9, 2, 10 };
		printLines(quadruplets(a1));
	}
}
