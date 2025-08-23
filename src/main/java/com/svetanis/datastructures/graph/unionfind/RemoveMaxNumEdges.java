package com.svetanis.datastructures.graph.unionfind;

// 1579. Remove Max Number of Edges to Keep Graph Fully Traversable

public final class RemoveMaxNumEdges {

	public int maxNumEdgesToRemove(int n, int[][] edges) {
		UnionFind auf = new UnionFind(n);
		UnionFind buf = new UnionFind(n);
		int max = 0;
		// shared edges
		for (int[] edge : edges) {
			int type = edge[0], from = edge[1], to = edge[2];
			if (type == 3) {
				if (!auf.union(from, to)) {
					max++;
				} else {
					buf.union(from, to);
				}
			}
		}

		for (int[] edge : edges) {
			int type = edge[0], from = edge[1], to = edge[2];
			if (type == 1 && !auf.union(from, to)) {
				max++;
			}
			if (type == 2 && !buf.union(from, to)) {
				max++;
			}
		}
		return auf.count == 1 && buf.count == 1 ? max : -1;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 3, 1, 2 }, { 3, 2, 3 }, { 1, 1, 3 }, { 1, 2, 4 }, { 1, 1, 2 }, { 2, 3, 4 } };
		RemoveMaxNumEdges ldc = new RemoveMaxNumEdges();
		System.out.println(ldc.maxNumEdgesToRemove(4, g1)); // 2

		int[][] g2 = { { 3, 1, 2 }, { 3, 2, 3 }, { 1, 1, 4 }, { 2, 1, 4 } };
		System.out.println(ldc.maxNumEdgesToRemove(4, g2)); // 0

		int[][] g3 = { { 3, 2, 3 }, { 1, 1, 2 }, { 2, 3, 4 } };
		System.out.println(ldc.maxNumEdgesToRemove(4, g3)); // -1
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
				size[i] = i;
				parent[i] = i;
			}
		}

		public boolean union(int x, int y) {
			int xp = find(x - 1);
			int yp = find(y - 1);
			if (xp == yp) {
				return false;
			}
			if (size[xp] > size[yp]) {
				parent[yp] = xp;
				size[xp] += size[yp];
			} else {
				parent[xp] = yp;
				size[yp] += size[xp];
			}
			--count;
			return true;
		}

		public int find(int x) {
			int y = parent[x];
			if (y != x) {
				y = find(y);
				parent[x] = y;
			}
			return y;
		}
	}
}
