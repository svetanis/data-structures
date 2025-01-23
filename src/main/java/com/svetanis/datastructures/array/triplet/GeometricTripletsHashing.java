package com.svetanis.datastructures.array.triplet;

import java.util.HashMap;
import java.util.Map;

// A geometric sequence triplet 
// is a sequence of three numbers 
// where each successive number 
// is obtained by multiplying
// the preceding number by a 
// constant called the common ratio

public final class GeometricTripletsHashing {
	// Time complexity: O(n)

	public static int countTriplets(int[] a, int r) {
		int count = 0;
		Map<Integer, Integer> right = new HashMap<>();
		Map<Integer, Integer> left = new HashMap<>();
		for (int element : a) {
			right.put(element, right.getOrDefault(element, 0) + 1);
		}
		for (int element : a) {
			right.put(element, right.getOrDefault(element, 0) - 1);
			if (element % r == 0) {
				int first = element / r;
				int second = element * r;
				count += left.getOrDefault(first, 0) * right.getOrDefault(second, 0);
			}
			left.put(element, left.getOrDefault(element, 0) + 1);
		}

		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 4 };
		System.out.println(countTriplets(a1, 2)); // 1

		int[] a2 = { 5, 15, 45 };
		System.out.println(countTriplets(a2, 3)); // 1

		int[] a3 = { 2, 1, 2, 4, 8, 8 };
		System.out.println(countTriplets(a3, 2)); // 5
	}
}