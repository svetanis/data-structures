package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 684. Redundant Connection
// RedundantConnectionSubmit is the same algorithm with the structure inlined.
//
// The one problem in this folder where the interesting call is the one that FAILS.
// Everywhere else union is asked to join things; here the answer is the first edge
// it refuses, because refusing means the two ends were already connected and this
// edge closes a cycle.

public final class RedundantConnection {
	// Time Complexity: O(e)
	// Space Complexity: O(n)

	private DisjointSet ds;

	public int[] findRedundantConnection(int[][] edges) {
		// nodes are labelled 1..n and the statement gives exactly n edges, so
		// edges.length IS the node count -- and n + 1 slots are needed, because
		// slot 0 belongs to no node and node n still needs a seat
		int n = edges.length;
		this.ds = new DisjointSet(n + 1);
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1];
			// false means they were already connected, so this edge is the extra one.
			// returning here is what makes it the LAST such edge in input order, which
			// is what the statement asks for when more than one answer exists.
			if (!ds.union(from, to)) {
				return edge;
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
		System.out.println(Arrays.toString(rc.findRedundantConnection(e1))); // [2, 3]

		int[][] e2 = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 4 }, { 1, 5 } };
		System.out.println(Arrays.toString(new RedundantConnection().findRedundantConnection(e2))); // [1, 4]

		// a triangle plus a tail: the cycle closes before the tail is read
		int[][] e3 = { { 1, 2 }, { 2, 3 }, { 1, 3 }, { 3, 4 } };
		System.out.println(Arrays.toString(new RedundantConnection().findRedundantConnection(e3))); // [1, 3]
	}
}
