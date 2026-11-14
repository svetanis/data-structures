package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

// 1306. Jump Game III
//
// Input: a -- an array of non-negative integers; start -- an index. Standing on index i you may jump
// to i + a[i] or to i - a[i], never outside the array. Returns true if some index holding 0 can be
// reached from start. On LeetCode: 1 <= a.length <= 5 * 10^4, 0 <= a[i] < a.length,
// 0 <= start < a.length. The method uses the array itself as its visited marker, so it overwrites the
// entries it has processed with -1: pass a copy if the array is needed afterwards.
//
// The one idea: one queue entry is an index you can stand on. Take an index; if it holds 0 you are
// done. Otherwise it has at most two next indexes, a[i] to the left and a[i] to the right, so this is
// a search through a graph with two edges per index. Visited is kept in the array: once an index has
// been processed its entry becomes -1, which no jump length can be, so that index is never processed
// twice. An index can be put on the queue twice before it is processed (two different indexes jump
// onto it); the second copy is skipped when it comes off, because by then its entry is -1.
//
// Time: O(n) -- each index is processed once; it adds at most two entries to the queue.
// Space: O(n) -- the queue.

public final class JumpGameIII {

	public static boolean canReach(int[] a, int start) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(start);
		while (!queue.isEmpty()) {
			int index = queue.poll(); // TAKE: an index you can stand on
			int next = a[index];
			if (next < 0) {
				continue; // SKIP: already processed; -1 is not a jump length
			}
			if (next == 0) {
				return true; // RETURN: a zero is reachable
			}

			// mark current index as visited
			a[index] = -1; // MARK

			int left = index - next;
			if (safe(a, left)) {
				queue.offer(left); // WALK: jump left
			}
			int right = index + next;
			if (safe(a, right)) {
				queue.offer(right); // WALK: jump right
			}
		}
		return false; // no zero is reachable
	}

	private static boolean safe(int[] a, int index) {
		return index >= 0 && index < a.length && a[index] >= 0; // inside the array and not processed yet
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 2, 3, 0, 3, 1, 2 };
		System.out.println(canReach(a1, 5)); // true
		int[] a2 = { 4, 2, 3, 0, 3, 1, 2 };
		System.out.println(canReach(a2, 0)); // true
		int[] a3 = { 3, 0, 2, 1, 2 };
		System.out.println(canReach(a3, 2)); // false
		int[] a4 = { 3, 4, 4, 2, 0, 4 };
		System.out.println(canReach(a4, 0)); // false
	}
}
