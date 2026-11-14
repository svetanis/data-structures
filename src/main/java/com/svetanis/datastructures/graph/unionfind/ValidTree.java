package com.svetanis.datastructures.graph.unionfind;

// 261. Graph Valid Tree
//
// Input: n -- nodes labelled 0..n-1; edges -- pairs [a, b], each a wire between two nodes. Returns
// true if the edges make a tree: connected, with no loop. On LeetCode: 1 <= n <= 2000,
// 0 <= edges.length <= 5000, a != b, no edge repeated.
//
// The one idea: one element is one node. A tree on n nodes has exactly n - 1 edges, and every one
// of them must join two groups that were apart -- an edge inside a group closes a loop. Start with
// n groups; each edge must lower the count by one, and when the edges run out exactly one group
// must be left.
//
// Time: O(n + e * alpha) -- n slots to set up, one union per edge.
// Space: O(n) -- the DisjointSet.
//
// Siblings: ValidTreeSubmit is the same algorithm with the structure inlined.
// ValidTreeDfs answers it by walking instead.
//
// A tree is TWO conditions, and checking only the first is the classic miss:
//   no cycle    -- every union must actually merge something
//   connected   -- and when the edges run out there is exactly one group left
// 684 needs only the first, because its statement promises the graph is connected.
// Here nothing promises it: n nodes and no edges is acyclic and still not a tree.

public final class ValidTree {

	public boolean validTree(int n, int[][] edges) {
		DisjointSet ds = new DisjointSet(n);
		// every node starts alone, so there are n groups to collapse
		int groups = n; // BASE: every node alone
		for (int[] edge : edges) {
			// false means the two ends were already connected, so this edge closes
			// a cycle and the answer is settled without looking at the rest
			if (!ds.union(edge[0], edge[1])) { // JOIN
				return false; // a loop
			}
			groups -= 1; // COUNT: one group fewer
		}
		// no cycle was found; what is left to decide is whether it is all one piece
		return groups == 1; // RETURN: a loop-free graph is a tree only if it is one piece
	}

	public static void main(String[] args) {
		ValidTree vt = new ValidTree();
		int[][] chain = { { 0, 1 }, { 1, 2 }, { 2, 3 } };
		System.out.println(vt.validTree(4, chain)); // true -- three edges, one piece

		// a cycle: the last edge joins two nodes already connected
		int[][] cycle = { { 0, 1 }, { 1, 2 }, { 0, 2 } };
		System.out.println(new ValidTree().validTree(3, cycle)); // false -- the triangle has a loop

		// acyclic and NOT connected -- the case a cycle check alone calls a tree
		int[][] split = { { 0, 1 }, { 2, 3 } };
		System.out.println(new ValidTree().validTree(4, split)); // false -- two pieces

		// one node, no edges: already a tree
		System.out.println(new ValidTree().validTree(1, new int[0][2])); // true
	}
}
