package com.svetanis.datastructures.array.triplet;

import java.util.HashMap;
import java.util.Map;

// 2964. Number of Divisible Triplet Sums

public final class CountDivisibleTriplets {
	// Time Complexity: O(n^2)

	public static int countDivisible(int[] a, int d) {
		int count = 0;
		int n = a.length;
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int mod = (d - (a[i] + a[j]) % d) % d;
				count += map.getOrDefault(mod, 0);
			}
			map.merge(a[i] % d, 1, Integer::sum);
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 2, 3, 5, 7, 11 };
		System.out.println(countDivisible(a1, 5));
	}
}
