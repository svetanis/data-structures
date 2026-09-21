package com.svetanis.datastructures.graph.unionfind;

// 261. Graph Valid Tree
// ValidTreeSubmit is the same algorithm with the structure inlined.
// ValidTreeDfs answers it by walking instead.
//
// A tree is TWO conditions, and checking only the first is the classic miss:
//   no cycle    -- every union must actually merge something
//   connected   -- and when the edges run out there is exactly one group left
// 684 needs only the first, because its statement promises the graph is connected.
// Here nothing promises it: n nodes and no edges is acyclic and still not a tree.

public final class ValidTree {
	// Time Complexity: O(n + e)
	// Space Complexity: O(n)

	public boolean validTree(int n, int[][] edges) {
		DisjointSet ds = new DisjointSet(n);
		// every node starts alone, so there are n groups to collapse
		int groups = n;
		for (int[] edge : edges) {
			// false means the two ends were already connected, so this edge closes
			// a cycle and the answer is settled without looking at the rest
			if (!ds.union(edge[0], edge[1])) {
				return false;
			}
			groups -= 1;
		}
		// no cycle was found; what is left to decide is whether it is all one piece
		return groups == 1;
	}

	public static void main(String[] args) {
		ValidTree vt = new ValidTree();
		int[][] chain = { { 0, 1 }, { 1, 2 }, { 2, 3 } };
		System.out.println(vt.validTree(4, chain)); // true

		// a cycle: the last edge joins two nodes already connected
		int[][] cycle = { { 0, 1 }, { 1, 2 }, { 0, 2 } };
		System.out.println(new ValidTree().validTree(3, cycle)); // false

		// acyclic and NOT connected -- the case a cycle check alone calls a tree
		int[][] split = { { 0, 1 }, { 2, 3 } };
		System.out.println(new ValidTree().validTree(4, split)); // false

		// one node, no edges: already a tree
		System.out.println(new ValidTree().validTree(1, new int[0][2])); // true
	}
}
