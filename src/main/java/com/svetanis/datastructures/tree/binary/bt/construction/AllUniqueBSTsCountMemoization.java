package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

// given a number n count
// structurally unique BSTs
// that can store values 1 to n

public final class AllUniqueBSTsCountMemoization {
	// Time Complexity: O(n * 2^n)

	private static final Map<Integer, Integer> MAP = newHashMap();

	public static int count(int n) {
		if (MAP.containsKey(n)) {
			MAP.get(n);
		}
		if (n <= 1) {
			return 1;
		}
		int count = 0;
		// making i root of the tree
		for (int i = 1; i <= n; i++) {
			int left = count(i - 1);
			int right = count(n - i);
			count += (left * right);
		}
		MAP.put(n, count);
		return count;
	}

	public static void main(String[] args) {
		System.out.println(count(3));
	}
}
