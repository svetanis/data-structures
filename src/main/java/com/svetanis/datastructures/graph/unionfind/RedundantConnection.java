package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 684. Redundant Connection
//
// Input: edges -- n edges [a, b] over nodes labelled 1..n; the graph was a tree and one extra edge
// was added. Returns the edge to remove so a tree is left; if several answers exist, the one that
// occurs last in edges. On LeetCode: n == edges.length, 3 <= n <= 1000, 1 <= a < b <= n, no edge
// repeated, the graph is connected.
//
// The one idea: one element is one node. Walk the edges in order, joining the two ends of each. An
// edge whose two ends are already in one group closes a loop -- that is the extra edge. Edges that
// came earlier were all needed to build the groups, so the first refused edge is also the last
// edge of the loop in input order.
//
// Time: O(n * alpha) -- one union per edge, each near-constant.
// Space: O(n) -- the DisjointSet.
//
// Siblings: RedundantConnectionSubmit is the same algorithm with the structure inlined.
//
// The one problem in this folder where the interesting call is the one that FAILS.
// Everywhere else union is asked to join things; here the answer is the first edge
// it refuses, because refusing means the two ends were already connected and this
// edge closes a cycle.

public final class RedundantConnection {

	private DisjointSet ds;

	public int[] findRedundantConnection(int[][] edges) {
		// nodes are labelled 1..n and the statement gives exactly n edges, so
		// edges.length IS the node count -- and n + 1 slots are needed, because
		// slot 0 belongs to no node and node n still needs a seat
		int n = edges.length;
		this.ds = new DisjointSet(n + 1); // one element per label; slot 0 is never used
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1];
			// false means they were already connected, so this edge is the extra one.
			// returning here is what makes it the LAST such edge in input order, which
			// is what the statement asks for when more than one answer exists.
			if (!ds.union(from, to)) { // JOIN; a refusal means a loop
				return edge; // RETURN: the first edge that closes a loop
			}
		}
		// unreachable: the statement guarantees one extra edge, so some union must
		// refuse. NOT a sentinel like {-1, -1} -- that is a well-formed, plausible
		// edge and a caller cannot tell it from an answer. null is crude and cannot
		// be mistaken for one, which on this line is the property that matters.
		return null;
	}

	public static void main(String[] args) {
		RedundantConnection rc = new RedundantConnection();
		int[][] e1 = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
		System.out.println(Arrays.toString(rc.findRedundantConnection(e1))); // [2, 3] -- 1-2, 1-3 already join 2 and 3

		int[][] e2 = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 4 }, { 1, 5 } };
		System.out.println(Arrays.toString(new RedundantConnection().findRedundantConnection(e2))); // [1, 4]

		// a triangle plus a tail: the cycle closes before the tail is read
		int[][] e3 = { { 1, 2 }, { 2, 3 }, { 1, 3 }, { 3, 4 } };
		System.out.println(Arrays.toString(new RedundantConnection().findRedundantConnection(e3))); // [1, 3]
	}
}
