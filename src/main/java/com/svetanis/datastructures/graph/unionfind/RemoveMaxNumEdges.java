package com.svetanis.datastructures.graph.unionfind;

// 1579. Remove Max Number of Edges to Keep Graph Fully Traversable
//
// Input: n -- nodes labelled 1..n; edges -- [type, a, b], an undirected edge that Alice may use
// when type is 1, Bob when type is 2, both when type is 3. Returns the largest number of edges
// that can be removed while Alice alone, and Bob alone, can still reach every node from every
// other; -1 if that is impossible even with all edges kept. On LeetCode: 1 <= n <= 10^5,
// 1 <= edges.length <= min(10^5, 3 * n * (n - 1) / 2), 1 <= type <= 3, 1 <= a < b <= n, all
// (type, a, b) triples distinct.
//
// The one idea: Alice and Bob each need a spanning tree of their own edges, and a tree on n nodes
// needs only n - 1 edges, so every edge that closes a loop for the person who would use it can be
// removed. Keep one union-find per person; one element is one node. Take the shared type-3 edges
// FIRST: one shared edge serves both people, so it is the best value for each edge kept. A shared
// edge that merges nothing for Alice merges nothing for Bob either, because after the shared phase
// the two structures hold the same groups. Then add Alice's own edges to her structure and Bob's to
// his; every edge that merges nothing is a removal. At the end both must be one group, else -1.
//
// Time: O(e * alpha) -- one union per edge (shared edges in both structures).
// Space: O(n) -- two parent arrays and two size arrays.

public final class RemoveMaxNumEdges {

	public int maxNumEdgesToRemove(int n, int[][] edges) {
		UnionFind auf = new UnionFind(n);
		UnionFind buf = new UnionFind(n);
		int max = 0; // removable edges so far
		// shared edges
		for (int[] edge : edges) {
			int type = edge[0], from = edge[1], to = edge[2];
			if (type == 3) {
				if (!auf.union(from, to)) {
					max++; // REMOVE: closes a loop for both people
				} else {
					buf.union(from, to); // KEEP: and Bob gets the same join
				}
			}
		}

		for (int[] edge : edges) {
			int type = edge[0], from = edge[1], to = edge[2];
			if (type == 1 && !auf.union(from, to)) {
				max++; // REMOVE: a loop in Alice's edges
			}
			if (type == 2 && !buf.union(from, to)) {
				max++; // REMOVE: a loop in Bob's edges
			}
		}
		return auf.count == 1 && buf.count == 1 ? max : -1; // RETURN: only if both reach everyone
	}

	public static void main(String[] args) {
		int[][] g1 = { { 3, 1, 2 }, { 3, 2, 3 }, { 1, 1, 3 }, { 1, 2, 4 }, { 1, 1, 2 }, { 2, 3, 4 } };
		RemoveMaxNumEdges ldc = new RemoveMaxNumEdges();
		System.out.println(ldc.maxNumEdgesToRemove(4, g1)); // 2 -- Alice's edges 1-3 and 1-2 close loops

		int[][] g2 = { { 3, 1, 2 }, { 3, 2, 3 }, { 1, 1, 4 }, { 2, 1, 4 } };
		System.out.println(ldc.maxNumEdgesToRemove(4, g2)); // 0 -- every edge is needed

		int[][] g3 = { { 3, 2, 3 }, { 1, 1, 2 }, { 2, 3, 4 } };
		System.out.println(ldc.maxNumEdgesToRemove(4, g3)); // -1 -- Alice cannot reach 4, Bob cannot reach 1
	}

	private static class UnionFind {
		private int count;
		private int[] size;
		private int[] parent;

		public UnionFind(int n) {
			this.count = n;
			this.size = new int[n];
			this.parent = new int[n];
			for (int i = 0; i < n; i++) {
				size[i] = 1; // one element in each group at the start
				parent[i] = i;
			}
		}

		public boolean union(int x, int y) {
			int xp = find(x - 1);
			int yp = find(y - 1);
			if (xp == yp) {
				return false; // already one group
			}
			if (size[xp] > size[yp]) { // the larger root stays on top
				parent[yp] = xp;
				size[xp] += size[yp];
			} else {
				parent[xp] = yp;
				size[yp] += size[xp];
			}
			--count; // COUNT: one group fewer
			return true;
		}

		public int find(int x) {
			int y = parent[x];
			if (y != x) {
				y = find(y); // WALK up to the root
				parent[x] = y; // COMPRESS: point straight at it
			}
			return y;
		}
	}
}
