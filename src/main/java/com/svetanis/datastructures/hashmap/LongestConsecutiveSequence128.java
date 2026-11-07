package com.svetanis.datastructures.hashmap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// 128. Longest Consecutive Sequence
//
// Each method takes an unsorted int array and returns the length of the longest run of
// consecutive values it holds (4, 2, 3, 1 is a run of 4), whatever their positions; 0 for
// an empty array. LeetCode asks for O(n) time. On LeetCode: 0 <= nums.length <= 10^5,
// -10^9 <= nums[i] <= 10^9, so num - 1 and curr + 1 never overflow.
//
// Put every value in a set. A value whose num - 1 is missing starts a run: walk up from it
// while curr + 1 is in the set, and the steps taken are the run's length. Only starts walk,
// and every value belongs to exactly one run, so all the walks together step over each
// value once. Walking from every value instead would step over the same run again and
// again.
//
// Three methods, same problem:
//   lcs      walks from each start in the set, so each start is tried once
//   lcs2     walks from each start in the array, which may hold a start many times;
//            removing values as the walk passes them makes a repeat start stop at once
//   lcsSort  sorts, then counts runs of +1 steps, skipping repeats: O(n log n), and it
//            sorts the caller's array

public final class LongestConsecutiveSequence128 {
	// Time complexity: lcs and lcs2 O(n), all walks together step at most n times;
	// lcsSort O(n log n) for the sort
	// Auxiliary Space: O(n) for the set; lcsSort O(1) beyond the sort

	public static int lcs(int[] a) {
		Set<Integer> set = new HashSet<>();
		for (int element : a) {
			set.add(element);
		}
		int max = 0;
		for (int num : set) {
			if (!set.contains(num - 1)) { // START: nothing below, so a run begins here
				int count = 1;
				int curr = num;
				while (set.contains(curr + 1)) { // WALK up the run
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
			if (!set.contains(num - 1)) { // START, or a value whose run was already walked
				int count = 1;
				int curr = num;
				while (set.contains(curr + 1)) { // WALK up the run
					set.remove(curr); // REMOVE: a later copy of this start finds nothing above
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
				continue; // SKIP a repeat: it neither extends nor breaks the run
			}
			if (a[i] == a[i - 1] + 1) {
				len++; // EXTEND: one more than the value before
			} else {
				len = 1; // BREAK: a gap starts a new run
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