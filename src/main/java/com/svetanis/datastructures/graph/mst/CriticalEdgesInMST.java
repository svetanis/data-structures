package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// 1489. Find Critical and Pseudo-Critical Edges in Minimum Spanning Tree

public final class CriticalEdgesInMST {
	// Time Complexity: O(E^2)

	private int[][] edges;

	public List<List<Integer>> criticalEdges(int n, int[][] grid) {
		init(grid);
		int total = totalWeight(n, edges);
		List<List<Integer>> list = newList();
		for (int[] edge : edges) {
			int from = edge[0], to = edge[1];
			int weight = edge[2], index = edge[3];
			UnionFind uf = new UnionFind(n);
			int excl = weightWithoutEdge(uf, index, edges);
			int count = uf.getCount();
			// add edge index to critical edges
			if (count > 1 || count == 1 && excl > total) {
				list.get(0).add(index);
				continue;
			}
			// pseudo-critical
			UnionFind puf = new UnionFind(n);
			puf.union(from, to);
			int incl = weight + weightWithoutEdge(puf, index, edges);
			if (incl == total) {
				list.get(1).add(index);
			}
		}
		return list;
	}

	private int weightWithoutEdge(UnionFind uf, int index, int[][] edges) {
		int weight = 0;
		for (int[] edge : edges) {
			if (edge[3] != index && uf.union(edge[0], edge[1])) {
				weight += edge[2];
			}
		}
		return weight;
	}

	private List<List<Integer>> newList() {
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			list.add(new ArrayList<>());
		}
		return list;
	}

	private int totalWeight(int n, int[][] edges) {
		int total = 0;
		UnionFind uf = new UnionFind(n);
		for (int[] edge : edges) {
			if (uf.union(edge[0], edge[1])) {
				total += edge[2];
			}
		}
		return total;
	}

	private void init(int[][] grid) {
		int n = grid.length;
		this.edges = new int[n][4];
		for (int i = 0; i < n; i++) {
			int[] a = new int[4];
			System.arraycopy(grid[i], 0, a, 0, 3);
			a[3] = i;
			this.edges[i] = a;
		}
		Arrays.sort(this.edges, Comparator.comparingInt(a -> a[2]));
	}

	public static void main(String[] args) {
		CriticalEdgesInMST ce = new CriticalEdgesInMST();
		int[][] e1 = { { 0, 1, 1 }, { 1, 2, 1 }, { 2, 3, 2 }, { 0, 3, 2 }, { 0, 4, 3 }, { 3, 4, 3 }, { 1, 4, 6 } };
		System.out.println(ce.criticalEdges(5, e1)); // [0,1],[2,3,4,5]
		int[][] e2 = { { 0, 1, 1 }, { 1, 2, 1 }, { 2, 3, 1 }, { 0, 3, 1 } };
		System.out.println(ce.criticalEdges(4, e2)); // [], [0,1,2,3]
	}

	private static class UnionFind {
		private int count;
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

		public boolean union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			if (rootX == rootY) {
				return false;
			}
			parent[rootX] = rootY;
			count--;
			return true;
		}

		public int find(int x) {
			if (x != parent[x]) {
				parent[x] = find(parent[x]);
			}
			return parent[x];
		}
	}
}
