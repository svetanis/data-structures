package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 1647. Minimum Deletions to Make Character Frequencies Unique

public final class MinDeletions {
	// Time Complexity: O(n + k log k)
	// Space Complexity: O(k)

	public static int minDeletions(String s) {
		int[] counts = new int[26];
		for (char c : s.toCharArray()) {
			counts[c - 'a']++;
		}
		Arrays.sort(counts);
		int minDeletions = 0;
		int prev = Integer.MAX_VALUE;
		for (int i = counts.length - 1; i >= 0; i--) {
			int curr = counts[i];
			if (prev == 0) {
				minDeletions += curr;
			} else if (curr >= prev) {
				minDeletions += curr - prev + 1;
				prev--;
			} else {
				prev = curr;
			}
		}
		return minDeletions;
	}

	public static void main(String[] args) {
		System.out.println(minDeletions("aab")); // 0
		System.out.println(minDeletions("aaabbbcc")); // 2
		System.out.println(minDeletions("ceabaacb")); // 2
	}
}