package com.svetanis.datastructures.tree.binary.bt.special;

import java.util.Arrays;

// 2509. Cycle Length Queries in a Tree
//
// Input: n -- the tree is a complete binary tree with 2^n - 1 nodes, labelled 1 to 2^n - 1 so that
// node v has the children 2v and 2v + 1 (and so the parent v / 2, rounded down); queries[i] =
// { a, b } -- for each query, add an edge between nodes a and b, and the question is the length,
// counted in edges, of the cycle that edge closes. Returns, for each query, that length (the edge
// is removed again before the next query). On LeetCode: 2 <= n <= 30, 1 <= queries.length <= 10^5,
// 1 <= a, b <= 2^n - 1, a != b.
//
// The one idea: the cycle is the new edge plus the one path between a and b in the tree, so its
// length is that path's length in edges plus 1. The path climbs from both nodes to their lowest
// common ancestor. A parent always has a smaller label than its child, so the larger of two
// different labels is never an ancestor of the other: step the larger one up to its parent, count
// the step, and repeat until the two labels meet. n itself is never needed.
//
// Time: O(m * n) -- each query takes at most 2(n - 1) steps, as each label is at most n levels
// deep; m is queries.length.
// Space: O(m) -- the answers.

public final class CycleLengthQueries {

	public static int[] cycleLenQueries(int n, int[][] queries) {
		int m = queries.length;
		int[] cycles = new int[m];
		for (int i = 0; i < m; i++) {
			int start = queries[i][0];
			int end = queries[i][1];
			int len = 1; // COUNT: the new edge itself
			while (start != end) {
				if (start > end) {
					start >>= 1; // WALK: the larger label steps up to its parent
				} else {
					end >>= 1;
				}
				len++; // COUNT: one more edge on the path
			}
			cycles[i] = len;
		}
		return cycles; // RETURN: one cycle length per query
	}

	public static void main(String[] args) {
		int[][] q1 = { { 5, 3 }, { 4, 7 }, { 2, 3 } };
		System.out.println(Arrays.toString(cycleLenQueries(3, q1))); // [4, 5, 3]

		int[][] q2 = { { 1, 2 } };
		System.out.println(Arrays.toString(cycleLenQueries(2, q2))); // [2]
	}
}
