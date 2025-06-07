package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 1584. Min Cost to Connect All Points
// Kruskal's Algorithm with Union-Find

public final class MinCostToConnectAllPointsKruskal {

	public int minCost(int[][] points) {
		int n = points.length;
		List<int[]> edges = edges(points);
		int cost = 0;
		int used = 0;
		UnionFind uf = new UnionFind(n);
		for (int[] edge : edges) {
			int u = edge[0], v = edge[1], w = edge[2];
			if (uf.union(u, v)) {
				cost += w;
				used++;
				if (used == n - 1) {
					break;
				}
			}
		}
		return cost;
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
		MinCostToConnectAllPointsKruskal mcc = new MinCostToConnectAllPointsKruskal();
		System.out.println(mcc.minCost(points1)); // 20

		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		MinCostToConnectAllPointsKruskal mcc2 = new MinCostToConnectAllPointsKruskal();
		System.out.println(mcc2.minCost(points2)); // 18
	}

	private static class UnionFind {
		private int[] parent;
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
			// make root of smaller rank point
			// to root of larger rank
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
