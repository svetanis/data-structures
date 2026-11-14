package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 685. Redundant Connection II
//
// Input: edges -- n directed edges [parent, child] over nodes labelled 1..n; the graph was a rooted
// tree (one root, every other node has exactly one parent) and one extra directed edge was added.
// Returns the edge to remove so a rooted tree is left; if several answers exist, the one that
// occurs last in edges. On LeetCode: n == edges.length, 3 <= n <= 1000, 1 <= parent, child <= n,
// parent != child.
//
// The one idea: the extra edge can do two kinds of damage. (1) It can give some node a SECOND
// parent. (2) It can close a loop. It can do both. Walk the edges in order and set aside the second
// edge into any node (`conflict`), leaving it out of the union-find; join every other edge, and
// note the first one whose ends were already joined (`cycle`).
//   - no conflict: only a loop exists, and the edge that closed it is the answer.
//   - conflict and no loop: the second-parent edge is the answer.
//   - conflict and a loop even without it: the loop runs through the node's FIRST parent edge, so
//     that first edge is the answer.
// `parents[child]` holds the first parent seen for each child.
//
// Time: O(n * alpha) -- one pass, at most one union per edge.
// Space: O(n) -- the parents array and the union-find.

public final class RedundantConnectionII {

	private UnionFind uf;
	private int[] parents;

	public int[] redundantConnections(int[][] edges) {
		int n = edges.length;
		init(n + 1);
		int cycle = -1; // index of the edge that closed a loop, if any
		int conflict = -1; // index of the second edge into a node, if any
		this.uf = new UnionFind(n + 1);
		for (int i = 0; i < n; i++) {
			int from = edges[i][0];
			int to = edges[i][1];
			if (parents[to] != to) {
				conflict = i; // SKIP: a second parent for `to`, kept out of the union-find
			} else {
				parents[to] = from; // RECORD: the first parent
				if (!uf.union(from, to)) { // JOIN; a refusal means a loop
					cycle = i;
				}
			}
		}
		if (conflict == -1) {
			return edges[cycle]; // RETURN: only a loop, so the edge that closed it
		}
		int to = edges[conflict][1];
		if (cycle != -1) {
			return new int[] { parents[to], to }; // RETURN: the loop uses the first parent edge
		}
		return edges[conflict]; // RETURN: no loop, so the second parent edge
	}

	private void init(int n) {
		this.parents = new int[n + 1];
		for (int i = 0; i <= n; i++) {
			this.parents[i] = i;
		}
	}

	public static void main(String[] args) {
		RedundantConnectionII rc = new RedundantConnectionII();
		int[][] e1 = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
		System.out.println(Arrays.toString(rc.redundantConnections(e1))); // [2, 3]

		int[][] e2 = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 1 }, { 1, 5 } };
		System.out.println(Arrays.toString(rc.redundantConnections(e2))); // [4, 1]
	}

	private static class UnionFind {

		private int count;
		private int[] parent;

		public UnionFind(int size) {
			this.count = size;
			this.parent = new int[size];
			for (int i = 0; i < size; i++) {
				parent[i] = i;
			}
		}

		public int find(int x) {
			if (parent[x] != x) {
				parent[x] = find(parent[x]); // WALK up, COMPRESS on the way back
			}
			return parent[x];
		}

		public boolean union(int x, int y) {
			int parentX = find(x);
			int parentY = find(y);
			if (parentX == parentY) {
				return false; // already one group
			}
			parent[parentX] = parentY; // JOIN
			count--;
			return true;
		}
	}
}
