package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// 659. Split Array into Consecutive Subsequences

public final class SplitArrayIntoConsecutiveSubsequences {
	// Time Complexity: O(n log k)

	public static boolean isPossible(int[] a) {
		Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
		for (int num : a) {
			int prev = num - 1;
			// there exists a sequence to append to
			if (map.containsKey(prev)) {
				PriorityQueue<Integer> pq = map.get(prev);
				int len = pq.poll() + 1;
				if (pq.isEmpty()) {
					map.remove(prev);
				}
				map.computeIfAbsent(num, k -> new PriorityQueue<>()).offer(len);
			} else {
				// start a new sequence
				map.computeIfAbsent(num, k -> new PriorityQueue<>()).offer(1);
			}
		}
		for (PriorityQueue<Integer> pq : map.values()) {
			if (pq.peek() < 3) {
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