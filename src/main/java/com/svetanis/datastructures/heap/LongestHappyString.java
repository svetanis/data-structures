package com.svetanis.datastructures.heap;

import java.util.PriorityQueue;
import java.util.Queue;

// 1405. Longest Happy String

public final class LongestHappyString {
	// Time Complexity: O(a + b + c)
	// Space Complexity: O(a + b + c)

	public static String lhs(int a, int b, int c) {
		Queue<int[]> pq = build(a, b, c);
		StringBuilder sb = new StringBuilder();
		while (!pq.isEmpty()) {
			int[] curr = pq.poll();
			char s = (char) curr[0];
			int len = sb.length();
			if (len >= 2 && s == sb.charAt(len - 1) && s == sb.charAt(len - 2)) {
				if (pq.isEmpty()) {
					break;
				}
				int[] next = pq.poll();
				sb.append((char) next[0]);
				if (next[1] > 1) {
					next[1]--;
					pq.offer(next);
				}
				pq.offer(curr);
			} else {
				sb.append((char) curr[0]);
				if (curr[1] > 1) {
					curr[1]--;
					pq.offer(curr);
				}
			}
		}
		return sb.toString();
	}

	private static Queue<int[]> build(int a, int b, int c) {
		Queue<int[]> pq = new PriorityQueue<>((x, y) -> y[1] - x[1]);
		if (a > 0) {
			pq.offer(new int[] { 'a', a });
		}
		if (b > 0) {
			pq.offer(new int[] { 'b', b });
		}
		if (c > 0) {
			pq.offer(new int[] { 'c', c });
		}
		return pq;
	}

	public static void main(String[] args) {
		System.out.println(lhs(1, 1, 7)); // ccaccbcc
		System.out.println(lhs(7, 1, 0)); // aabaa
	}
}
