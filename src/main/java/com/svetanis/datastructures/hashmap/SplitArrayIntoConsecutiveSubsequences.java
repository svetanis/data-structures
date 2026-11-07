package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// 659. Split Array into Consecutive Subsequences
//
// isPossible(nums) takes ints sorted in non-decreasing order and returns true if they can be
// split into one or more subsequences where each is consecutive (every value is one more than
// the one before) and at least 3 long. Every element goes into exactly one subsequence. On
// LeetCode: 1 <= nums.length <= 10^4, -1000 <= nums[i] <= 1000, nums is sorted.
//
// Build the subsequences while reading nums in order. A map takes a value v to the lengths of
// the subsequences that currently end at v, in a min-heap. Each num either extends one that
// ends at num - 1 or starts a new one of length 1. When several end at num - 1, extend the
// SHORTEST: it is the one that may end below 3, and a longer one is already closer to safe.
// A subsequence nobody extends simply ends where it is. At the end every subsequence must be
// at least 3 long, and the shortest in each heap decides.
//
// Siblings, same problem: none in this package.

public final class SplitArrayIntoConsecutiveSubsequences {
	// Time complexity: O(n log n), each number does at most one poll and one offer on a heap
	// Auxiliary Space: O(n), at most one heap entry per number

	public static boolean isPossible(int[] a) {
		Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
		for (int num : a) {
			int prev = num - 1;
			// there exists a sequence to append to
			if (map.containsKey(prev)) {
				PriorityQueue<Integer> pq = map.get(prev);
				int len = pq.poll() + 1; // EXTEND: the shortest one ending at num - 1
				if (pq.isEmpty()) {
					map.remove(prev);
				}
				map.computeIfAbsent(num, k -> new PriorityQueue<>()).offer(len); // MOVE: it now ends at num
			} else {
				// start a new sequence
				map.computeIfAbsent(num, k -> new PriorityQueue<>()).offer(1); // START: length 1
			}
		}
		for (PriorityQueue<Integer> pq : map.values()) {
			if (pq.peek() < 3) { // CHECK: the shortest ending at this value
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3, 3, 4, 5 };
		System.out.println(isPossible(a1)); // true
		int[] a2 = { 1, 2, 3, 3, 4, 4, 5, 5 };
		System.out.println(isPossible(a2)); // true
		int[] a3 = { 1, 2, 3, 4, 4, 5 };
		System.out.println(isPossible(a3)); // false
	}
}