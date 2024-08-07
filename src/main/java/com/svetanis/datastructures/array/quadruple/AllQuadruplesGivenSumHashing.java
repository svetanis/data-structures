package com.svetanis.datastructures.array.quadruple;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.sort;
import static com.svetanis.java.base.collect.Maps.getIfPresent;
import static com.svetanis.java.base.utils.Print.printLines;
import static java.util.Arrays.asList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;
import com.svetanis.java.base.utils.Quadruple;

// given an array of unsorted numbers and a target
// find all unique quadruples whose sum is equal 
// to the target number

public final class AllQuadruplesGivenSumHashing {
	// Time Complexity: O(n^2)

	public static ImmutableList<Quadruple<Integer, Integer, Integer, Integer>> quadruples(int[] a, int k) {
		int n = a.length;
		Map<Integer, Pair<Integer, Integer>> map = newHashMap();
		Set<Quadruple<Integer, Integer, Integer, Integer>> set = newHashSet();
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				int sum = a[i] + a[j];
				int target = k - sum;
				Pair<Integer, Integer> p1 = Pair.build(i, j);
				Optional<Pair<Integer, Integer>> p2 = getIfPresent(map, target);
				if (p2.isPresent() && !isCommon(p1, p2.get())) {
					int left = p2.get().getLeft();
					int right = p2.get().getRight();
					List<Integer> sorted = sort(asList(a[i], a[j], a[left], a[right]));
					set.add(quadruple(sorted));
				}
				if (!map.containsKey(sum)) {
					map.put(sum, Pair.build(i, j));
				}
			}
		}
		return newList(set);
	}

	private static boolean isCommon(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
		boolean one = p1.getLeft() == p2.getLeft();
		boolean two = p1.getRight() == p2.getRight();
		boolean three = p1.getLeft() == p2.getRight();
		boolean four = p1.getRight() == p2.getLeft();
		return one || two || three || four;
	}

	private static Quadruple<Integer, Integer, Integer, Integer> quadruple(Iterable<Integer> iterable) {
		Iterator<Integer> iter = iterable.iterator();
		return Quadruple.build(iter.next(), iter.next(), iter.next(), iter.next());
	}

	public static void main(String[] args) {
		int[] a = { 4, 1, 2, -1, 1, -3 };
		printLines(quadruples(a, 1));

		int[] a1 = { 2, 0, -1, 1, -2, 2 };
		printLines(quadruples(a1, 2));
	}
}

// Output:
// -3, -1, 1, 4
// -3, 1, 1, 2

// -2, 0, 2, 2
// -1, 0, 1, 2
