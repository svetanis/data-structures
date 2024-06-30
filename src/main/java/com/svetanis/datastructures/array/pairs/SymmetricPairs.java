package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

// given array of pairs, find all symmetric pairs
// two pairs (a,b) and (c,d) are symmetric, if a == d and b == c

public final class SymmetricPairs {

	public static ImmutableList<Pair<Integer, Integer>> pairs(List<Pair<Integer, Integer>> pairs) {
		// Time complexity: O(n)

		Map<Integer, Integer> map = newHashMap();
		List<Pair<Integer, Integer>> list = newArrayList();
		for (Pair<Integer, Integer> pair : pairs) {
			int left = pair.getLeft();
			int right = pair.getRight();
			if (map.containsKey(right)) {
				if (map.get(right) == left) {
					list.add(Pair.build(right, left));
				}
			} else {
				map.put(left, right);
			}
		}
		return newList(list);
	}

	public static void main(String[] args) {
		Pair<Integer, Integer> p1 = Pair.build(11, 20);
		Pair<Integer, Integer> p2 = Pair.build(30, 40);
		Pair<Integer, Integer> p3 = Pair.build(5, 10);
		Pair<Integer, Integer> p4 = Pair.build(40, 30);
		Pair<Integer, Integer> p5 = Pair.build(10, 5);
		List<Pair<Integer, Integer>> pairs = asList(p1, p2, p3, p4, p5);
		System.out.println(pairs(pairs));
	}
}
