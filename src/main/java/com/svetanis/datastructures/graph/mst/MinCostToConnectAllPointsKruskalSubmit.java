package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 1584. Min Cost to Connect All Points
//
// Input: points, a list of [x, y] positions. On LeetCode: 1 <= points.length <= 1000,
// -10^6 <= x, y <= 10^6, and all points are different.
// Returns: the least total length of segments that joins every point to every other, directly or
// through other points. The length of a segment is the Manhattan distance |x1 - x2| + |y1 - y2|.
// A cheapest such set of segments is a minimum spanning tree (MST): n - 1 segments, no loops.
//
// Kruskal's Algorithm with Union-Find. Self-contained: the union-find is nested, so this pastes
// on its own.
//
// The one idea: take the segments from the shortest up, and keep a segment only if its two ends
// are not already connected through the segments kept so far; a segment between points that are
// already connected would close a loop and is never needed. The shortest segment is always safe
// to keep, and after it the shortest segment that does not close a loop is safe again, which is
// why this greedy choice never has to be undone. The nested union-find answers "already
// connected?" and joins two groups in one call: union returns true when the ends were in
// different groups. It joins by RANK where the shared DisjointSet joins by SIZE -- the totals are
// identical either way.
//
// Time: O(n^2 log n) -- n(n-1)/2 edges built, then sorted; the union-find walk is the cheap part.
// Space: O(n^2) -- the edge list.
//
// Siblings: MinCostToConnectAllPointsKruskal is the same algorithm against the shared
// DisjointSet, and is the one to read; PrimScan, PrimMatrix and PrimHeap solve the problem
// without building the edge list.

public final class MinCostToConnectAllPointsKruskalSubmit {

	public int minCost(int[][] points) {
		int n = points.length;
		List<int[]> edges = edges(points); // EDGES: every pair, sorted lightest first
		int total = 0;
		int count = 0;
		UnionFind uf = new UnionFind(n);
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1], dist = edge[2];
			if (uf.union(from, to)) {
				total += dist; // TAKE: this segment joins two groups
				count++;
				if (count == n - 1) {
					break; // STOP: the tree is complete
				}
			}
		}
		return total;
	}

	private List<int[]> edges(int[][] points) {
		int n = points.length;
		List<int[]> edges = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int dist = mdist(points, i, j);
				edges.add(new int[] { i, j, dist }); // BUILD: one edge per unordered pair
			}
		}
		Collections.sort(edges, Comparator.comparing(e -> e[2])); // SORT: lightest first
		return edges;
	}

	private int mdist(int[][] points, int u, int v) {
		return Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]); // RETURN: Manhattan distance
	}

	public static void main(String[] args) {
		int[][] points1 = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		MinCostToConnectAllPointsKruskalSubmit mcc = new MinCostToConnectAllPointsKruskalSubmit();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsKruskalSubmit mcc2 = new MinCostToConnectAllPointsKruskalSubmit();
		System.out.println(mcc2.minCost(points2)); // 18
	}

	private static class UnionFind {
		private int[] parent;
		// rank[root] is an UPPER BOUND ON THE HEIGHT of that tree -- never a count of
		// its elements. A root of rank 3 can hold five hundred nodes, so there is no
		// rank equivalent of size(x): 827's island area and 952's largest group both
		// need the real count and must use union by size instead.
		// Path compression shortens trees without lowering ranks, so after any find
		// this is a bound rather than the height. The guarantee still holds.
		private int[] rank;

		public UnionFind(int size) {
			this.parent = new int[size];
			this.rank = new int[size];
			init(size);
		}

		private void init(int size) {
			for (int i = 0; i < size; i++) {
				this.parent[i] = i; // CARDS: every point starts as its own group
				this.rank[i] = 1;
			}
		}

		public int find(int x) {
			int y = parent[x];
			if (y != x) {
				y = find(y); // WALK: up to the root
				parent[x] = y; // COMPRESS: point straight at it
			}
			return y;
		}

		public boolean union(int p, int q) {
			// put p and q into the same components
			int rootP = find(p);
			int rootQ = find(q);
			if (rootP == rootQ) {
				return false; // SKIP: already together
			}
			// make root of smaller rank point to root of larger rank.
			// note what is NOT here: when the ranks differ, nothing is recorded --
			// the shorter tree hangs underneath and the height does not change.
			// the counter moves ONLY on the tie below, the one case where the result
			// is one taller. union by size updates on every merge instead.
			if (rank[rootP] < rank[rootQ]) {
				parent[rootP] = rootQ;
			} else if (rank[rootP] > rank[rootQ]) {
				parent[rootQ] = rootP;
			} else {
				parent[rootQ] = rootP;
				rank[rootP]++;
			}
			return true; // JOIN: the two groups are now one
		}

	}
}
