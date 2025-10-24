package com.svetanis.datastructures.hashmap;

import java.util.Arrays;
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
		for (int num : set) {
			if (!set.contains(num - 1)) {
				int count = 1;
				int curr = num;
				while (set.contains(curr + 1)) {
					curr++;
					count++;
				}
				max = Math.max(max, count);
			}
		}
		return max;
	}

	public static int lcs2(int[] a) {
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

	public static int lcsSort(int[] a) {
		if (a.length == 0) {
			return 0;
		}
		Arrays.sort(a);
		int len = 1;
		int max = 1;
		for (int i = 1; i < a.length; i++) {
			if (a[i] == a[i - 1]) {
				continue;
			}
			if (a[i] == a[i - 1] + 1) {
				len++;
			} else {
				len = 1;
			}
			max = Math.max(max, len);
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