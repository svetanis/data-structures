package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 128. Longest Consecutive Sequence

public final class LongestConsecutiveSequence128 {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int lcs(int[] a) {
		Set<Integer> set = new HashSet<>();
		for (int element : a) {
			set.add(element);
		}
		int max = 0;
		for (int num : a) {
			if (!set.contains(num - 1)) {
				int count = 1;
				int curr = num;
				while (set.contains(curr + 1)) {
					set.remove(curr);
					curr++;
					count++;
				}
				max = Math.max(max, count);
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int[] a1 = { 100, 4, 200, 1, 3, 2 };
		int[] a2 = { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 };
		System.out.println(lcs(a1)); // 4
		System.out.println(lcs(a2)); // 9
	}
}