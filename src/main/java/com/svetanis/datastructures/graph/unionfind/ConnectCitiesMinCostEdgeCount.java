package com.svetanis.datastructures.graph.unionfind;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;

// 1135. Connecting Cities With Minimum Cost
// The three 1135 files are a ladder, and each rung adds exactly one thing:
//   1. ConnectCitiesMinCostEdgeCount  -- Kruskal, counting EDGES up to n - 1
//   2. ConnectCitiesMinCost           -- the same, counting COMPONENTS down to 1
//   3. ConnectCitiesMinCostCounting   -- rung 2 with the comparison sort replaced
//                                        by a counting sort on the cost
// All three return the same answer on every input. Rung 1 is the one to write.
//
// RUNG 1, and the version to carry. `count == n - 1` states the fact the problem
// rests on -- a tree on n nodes has n - 1 edges -- so the stopping rule and the
// -1 guard are the same line read twice.
// Kruskal counting EDGES up to n - 1. ConnectCitiesMinCost counts COMPONENTS down to 1
// instead; the two stopping rules are the same fact stated from opposite ends, since a
// spanning tree has exactly n - 1 edges and exactly one component.
// Both methods here are the same algorithm and differ only in how the 1-based city
// labels are fitted to a 0-based array -- the choice is free, and worth seeing twice.

public final class ConnectCitiesMinCostEdgeCount {
	// Time Complexity: O(m log m) -- the sort dominates; the union-find walk is nearly free
	// Space Complexity: O(n)

	private DisjointSet ds;

	// cities are 1..n, so every label is shifted down by one to index 0..n-1
	public int minimumCostShifted(int n, int[][] connections) {
		this.ds = new DisjointSet(n);
		Arrays.sort(connections, comparingInt(connection -> connection[2]));
		int total = 0;
		int count = 0;
		for (int[] connection : connections) {
			int from = connection[0] - 1;
			int to = connection[1] - 1;
			int cost = connection[2];
			// union is the cycle check and the join in one call
			if (ds.union(from, to)) {
				total += cost;
				count += 1;
			}
			if (count == n - 1) {
				break;
			}
		}
		// fewer than n - 1 edges taken means the cities were never all joined,
		// and the total of a forest is a plausible number that is not an answer.
		// n == 1 needs no special case: count and n - 1 are both 0.
		return count < n - 1 ? -1 : total;
	}

	// the same, leaving the labels alone and giving slot 0 away unused
	public int minimumCostPadded(int n, int[][] connections) {
		this.ds = new DisjointSet(n + 1);
		Arrays.sort(connections, comparingInt(connection -> connection[2]));
		int total = 0;
		int count = 0;
		for (int[] connection : connections) {
			if (ds.union(connection[0], connection[1])) {
				total += connection[2];
				count += 1;
			}
			if (count == n - 1) {
				break;
			}
		}
		return count < n - 1 ? -1 : total;
	}

	public static void main(String[] args) {
		int[][] connections = { { 1, 2, 5 }, { 1, 3, 6 }, { 2, 3, 1 } };
		System.out.println(new ConnectCitiesMinCostEdgeCount().minimumCostShifted(3, connections)); // 6
		System.out.println(new ConnectCitiesMinCostEdgeCount().minimumCostPadded(3, connections)); // 6

		// city 4 is reachable from 3 only, and 1-2 is a separate component
		int[][] split = { { 1, 2, 3 }, { 3, 4, 4 } };
		System.out.println(new ConnectCitiesMinCostEdgeCount().minimumCostShifted(4, split)); // -1

		// a single city is already connected to all of itself
		System.out.println(new ConnectCitiesMinCostEdgeCount().minimumCostShifted(1, new int[0][3])); // 0
	}

	private static final class DisjointSet {

		private final int[] parent;
		private final int[] size;

		DisjointSet(int n) {
			this.parent = new int[n];
			this.size = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
				size[i] = 1;
			}
		}

		int find(int x) {
			int root = parent[x];
			while (root != parent[root]) {
				root = parent[root];
			}
			parent[x] = root;
			return root;
		}

		boolean union(int a, int b) {
			int ra = find(a);
			int rb = find(b);
			if (ra == rb) {
				return false;
			}
			if (size[ra] > size[rb]) {
				parent[rb] = ra;
				size[ra] += size[rb];
			} else {
				parent[ra] = rb;
				size[rb] += size[ra];
			}
			return true;
		}
	}
}
