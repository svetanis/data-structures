package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 1345. Jump Game IV
//
// Input: a -- an array of integers. Standing on index i you may jump to i + 1, to i - 1, or to any
// other index j that holds the same value as a[i]; never outside the array. Returns the fewest jumps
// to get from index 0 to the last index. On LeetCode: 1 <= a.length <= 5 * 10^4,
// -10^8 <= a[i] <= 10^8.
//
// The one idea: one queue entry is {an index you can stand on, the number of jumps it took to get
// there}. Every jump costs the same one, so breadth first search gives the fewest: the first time the
// last index comes off the queue, no shorter sequence of jumps exists. The catch is the same-value
// jumps: a value that appears 30000 times links every one of those indexes to every other, so
// looking through them at each index would be quadratic. But the first time ANY index of a value is
// expanded, all the other indexes with that value are queued with the same count, and nothing later
// can reach them faster. So that value's list is removed from the map after one use, and each value
// is expanded once.
//
// Time: O(n) -- each index is queued once, and each value's list is walked once.
// Space: O(n) -- the map from value to its indexes, the visited set and the queue.

public final class JumpGameIV {

	public static int minJumps(int[] a) {
		int n = a.length;
		Map<Integer, List<Integer>> map = indexes(a); // value -> every index that holds it
		Set<Integer> set = new HashSet<>(); // QUEUED: indexes already put on the queue
		Deque<int[]> dq = new ArrayDeque<>();
		set.add(0);
		dq.offer(new int[] { 0, 0 }); // START: index 0, no jumps yet
		while (!dq.isEmpty()) {
			int[] node = dq.poll(); // TAKE
			int index = node[0];
			int count = node[1];
			if (index == n - 1) {
				return count; // RETURN: the first time the last index comes off
			}
			count++; // every jump from here costs one more
			List<Integer> indexes = map.getOrDefault(a[index], new ArrayList<>());
			for (int i : indexes) { // JUMP: to every index with the same value
				if (!set.contains(i)) {
					set.add(i);
					dq.offer(new int[] { i, count });
				}
			}
			map.remove(a[index]); // SKIP: this value is used up; nothing later needs its list again
			if (index + 1 < n && !set.contains(index + 1)) { // JUMP: one to the right
				set.add(index + 1);
				dq.offer(new int[] { index + 1, count });
			}
			if (index - 1 >= 0 && !set.contains(index - 1)) { // JUMP: one to the left
				set.add(index - 1);
				dq.offer(new int[] { index - 1, count });
			}
		}
		return -1; // not reachable for the stated inputs: the +-1 jumps already connect every index
	}

	private static Map<Integer, List<Integer>> indexes(int[] a) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			map.computeIfAbsent(a[i], k -> new ArrayList<>()).add(i);
		}
		return map;
	}

	public static void main(String[] args) {
		int[] a1 = { 100, -23, -23, 404, 100, 23, 23, 23, 3, 404 };
		System.out.println(minJumps(a1)); // 3

		int[] a2 = { 7 };
		System.out.println(minJumps(a2)); // 0

		int[] a3 = { 7, 6, 9, 6, 9, 6, 9, 7 };
		System.out.println(minJumps(a3)); // 1
	}
}
