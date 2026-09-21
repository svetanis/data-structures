package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 1584. Min Cost to Connect All Points
// Self-contained: the union-find is nested, so this pastes on its own.
// MinCostToConnectAllPointsKruskal is the same algorithm against the shared
// DisjointSet, and is the one to read. This one joins by RANK where the shared
// class joins by SIZE -- the totals are identical either way.
// Kruskal's Algorithm with Union-Find

public final class MinCostToConnectAllPointsKruskalSubmit {

	public int minCost(int[][] points) {
		int n = points.length;
		List<int[]> edges = edges(points);
		int total = 0;
		int count = 0;
		UnionFind uf = new UnionFind(n);
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1], dist = edge[2];
			if (uf.union(from, to)) {
				total += dist;
				count++;
				if (count == n - 1) {
					break;
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
				edges.add(new int[] { i, j, dist });
			}
		}
		Collections.sort(edges, Comparator.comparing(e -> e[2]));
		return edges;
	}

	private int mdist(int[][] points, int u, int v) {
		return Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]);
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
				this.parent[i] = i;
				this.rank[i] = 1;
			}
		}

		public int find(int x) {
			int y = parent[x];
			if (y != x) {
				y = find(y);
				parent[x] = y;
			}
			return y;
		}

		public boolean union(int p, int q) {
			// put p and q into the same components
			int rootP = find(p);
			int rootQ = find(q);
			if (rootP == rootQ) {
				return false;
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
			return true;
		}

	}
}
