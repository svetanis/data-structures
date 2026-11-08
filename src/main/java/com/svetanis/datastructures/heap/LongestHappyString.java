package com.svetanis.datastructures.heap;

import static java.util.Comparator.comparingInt;

import java.util.PriorityQueue;
import java.util.Queue;

// 1405. Longest Happy String
//
// Input: a, b, c -- how many letters 'a', 'b' and 'c' may be used. A string is happy when it uses
// only those letters, at most a 'a's, b 'b's and c 'c's, and never three equal letters in a row.
// Returns a longest happy string; when several are longest, any one of them is accepted. On
// LeetCode: 0 <= a, b, c <= 100, a + b + c > 0.
//
// The one idea: one heap entry is a letter with how many of it are still left, as
// { letter, count }, and the heap keeps the letter with the most left on top. The only way to
// stop early is to be left holding copies of one letter with nothing to put between them, so
// always write the letter with the most left. When that would make three in a row, write one of
// the letter with the next most left instead, and keep the first for the next turn. A letter
// whose count reaches 0 leaves the heap. The string ends when the heap is empty, or when the
// only letter left would make three in a row.
//
// Time: O(a + b + c) -- each turn writes one letter or ends, and the heap never holds more than
// three entries, so a poll or offer is constant work.
// Space: O(a + b + c) -- the string being built.

public final class LongestHappyString {

	public static String lhs(int a, int b, int c) {
		Queue<int[]> pq = build(a, b, c); // HEAP: the letter with the most left on top
		StringBuilder sb = new StringBuilder();
		while (!pq.isEmpty()) {
			int[] curr = pq.poll(); // TAKE the letter with the most left
			char s = (char) curr[0];
			int len = sb.length();
			if (len >= 2 && s == sb.charAt(len - 1) && s == sb.charAt(len - 2)) { // BLOCKED: three in a row
				if (pq.isEmpty()) {
					break; // STOP: no other letter to put in between
				}
				int[] next = pq.poll(); // SECOND: the letter with the next most left
				sb.append((char) next[0]); // WRITE one of it
				if (next[1] > 1) {
					next[1]--;
					pq.offer(next); // BACK, if any are left
				}
				pq.offer(curr); // KEEP the blocked letter for the next turn
			} else {
				sb.append((char) curr[0]); // WRITE the letter with the most left
				if (curr[1] > 1) {
					curr[1]--;
					pq.offer(curr); // BACK, if any are left
				}
			}
		}
		return sb.toString(); // RETURN
	}

	private static Queue<int[]> build(int a, int b, int c) {
		Queue<int[]> pq = new PriorityQueue<>(comparingInt((int[] x) -> x[1]).reversed()); // ORDER: count, largest first
		if (a > 0) {
			pq.offer(new int[] { 'a', a }); // ADD only letters that may be used
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
