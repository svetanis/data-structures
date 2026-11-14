package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// 1489. Find Critical and Pseudo-Critical Edges in Minimum Spanning Tree
//
// Input: n vertices numbered 0..n-1 and edges, a list of weighted two-way edges [a, b, weight],
// connected. On LeetCode: 2 <= n <= 100, 1 <= edges.length <= min(200, n * (n - 1) / 2),
// a < b, 1 <= weight <= 1000, and all pairs (a, b) are different.
// Returns: two lists of edge indexes (positions in the input): first the critical edges, then the
// pseudo-critical ones, each in order of increasing edge weight (the edges are sorted by weight
// first and equal weights keep their input order). A minimum spanning tree (MST) is a set of
// n - 1 edges that joins every vertex at the lowest total weight, and a graph can have several.
// An edge is CRITICAL if it is in every MST: leaving it out makes the best tree heavier, or
// makes it impossible. An edge is PSEUDO-CRITICAL if it is in some MST but not in all of them.
//
// The one idea: first find the weight of an MST with Kruskal's method (take edges from the
// lightest up, keep an edge when it joins two separate groups). Then test each edge with two more
// Kruskal runs. Leave the edge out: if the vertices no longer all join, or the total goes up,
// then every best tree needed it, so it is critical. Otherwise force the edge in (join its ends
// first, add its weight, then continue with the rest): if the total is still the MST weight, then
// some best tree contains the edge, and since it is not critical, it is pseudo-critical. An edge
// that is neither is in no MST at all.
//
// Time: O(E^2 * alpha(n)) -- each of the E edges costs two Kruskal passes over all E edges, and
// each pass does a near-constant union-find step per edge; the single sort at the start is
// O(E log E).
// Space: O(n + E) -- the edge copies with their indexes, and one union-find of n vertices at a
// time. The union-find's find is recursive, but with n <= 100 it is at most 100 deep.
//
// Siblings: none. MstKruskal and MstForest are the plain Kruskal this file calls for each edge.

public final class CriticalEdgesInMST {

	private int[][] edges; // CARDS: edges[k] -- {a, b, weight, original index}, sorted by weight

	public List<List<Integer>> criticalEdges(int n, int[][] grid) {
		init(grid);
		int total = totalWeight(n, edges); // BASE: the weight of an MST
		List<List<Integer>> list = newList();
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1];
			int weight = edge[2], index = edge[3];
			UnionFind uf = new UnionFind(n);
			int excl = weightWithoutEdge(uf, index, edges); // LEAVE OUT: the best tree without this edge
			int count = uf.getCount();
			// add edge index to critical edges: without it the vertices do not all join (count > 1),
			// or they do but the tree is heavier
			if (count > 1 || count == 1 && excl > total) {
				list.get(0).add(index); // CRITICAL
				continue;
			}
			// pseudo-critical
			UnionFind puf = new UnionFind(n);
			puf.union(from, to); // FORCE IN: join its ends first
			int incl = weight + weightWithoutEdge(puf, index, edges);
			if (incl == total) {
				list.get(1).add(index); // PSEUDO-CRITICAL: some best tree contains it
			}
		}
		return list;
	}

	// Kruskal over every edge except the one with this index, on the given union-find
	private int weightWithoutEdge(UnionFind uf, int index, int[][] edges) {
		int weight = 0;
		for (int[] edge : edges) {
			if (edge[3] != index && uf.union(edge[0], edge[1])) {
				weight += edge[2]; // TAKE: it joins two separate groups
			}
		}
		return weight;
	}

	private List<List<Integer>> newList() {
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			list.add(new ArrayList<>()); // BUILD: list 0 for critical, list 1 for pseudo-critical
		}
		return list;
	}

	// Kruskal over all edges: the weight of an MST
	private int totalWeight(int n, int[][] edges) {
		int total = 0;
		UnionFind uf = new UnionFind(n);
		for (int[] edge : edges) {
			if (uf.union(edge[0], edge[1])) {
				total += edge[2]; // TAKE: it joins two separate groups
			}
		}
		return total;
	}

	// copy each edge with its input index, then sort the copies by weight
	private void init(int[][] grid) {
		int n = grid.length;
		this.edges = new int[n][4];
		for (int i = 0; i < n; i++) {
			int[] a = new int[4];
			System.arraycopy(grid[i], 0, a, 0, 3);
			a[3] = i;
			this.edges[i] = a;
		}
		Arrays.sort(this.edges, Comparator.comparingInt(a -> a[2])); // SORT: lightest first
	}

	public static void main(String[] args) {
		CriticalEdgesInMST ce = new CriticalEdgesInMST();
		int[][] e1 = { { 0, 1, 1 }, { 1, 2, 1 }, { 2, 3, 2 }, { 0, 3, 2 }, { 0, 4, 3 }, { 3, 4, 3 }, { 1, 4, 6 } };
		System.out.println(ce.criticalEdges(5, e1)); // [[0, 1], [2, 3, 4, 5]]
		int[][] e2 = { { 0, 1, 1 }, { 1, 2, 1 }, { 2, 3, 1 }, { 0, 3, 1 } };
		System.out.println(ce.criticalEdges(4, e2)); // [[], [0, 1, 2, 3]]
	}

	private static class UnionFind {
		private int count; // how many separate groups remain
		private int[] parent;

		public UnionFind(int size) {
			this.count = size;
			this.parent = new int[size];
			for (int i = 0; i < size; i++) {
				this.parent[i] = i;
			}
		}

		public int getCount() {
			return count;
		}

		// true if x and y were in different groups and are now joined
		public boolean union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			if (rootX == rootY) {
				return false; // SKIP: already together
			}
			parent[rootX] = rootY; // JOIN: one root under the other
			count--;
			return true;
		}

		public int find(int x) {
			if (x != parent[x]) {
				parent[x] = find(parent[x]); // WALK: up to the root, pointing straight at it on the way back
			}
			return parent[x]; // RETURN: the root
		}
	}
}
