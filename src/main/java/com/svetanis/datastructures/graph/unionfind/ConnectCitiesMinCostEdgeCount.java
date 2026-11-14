package com.svetanis.datastructures.graph.unionfind;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;

// 1135. Connecting Cities With Minimum Cost
//
// Input: n cities numbered 1..n and connections[i] = [x, y, cost], a road that can be built
// between cities x and y for that cost. On LeetCode: 1 <= n <= 10^4, 1 <= connections.length <=
// 10^4, x != y, 0 <= cost <= 10^5. The array is sorted in place.
// Returns the least total cost to build roads so every city can reach every other, or -1 when
// the given roads cannot join all the cities.
//
// The one idea (the same Kruskal method in all three files): the cheapest set of roads never
// needs a loop, so it is a tree of n - 1 roads. Go through the roads from cheapest to dearest. A
// road between two cities that are not yet connected is needed: build it. A road between two
// cities that are already connected would only close a loop: skip it. The cheapest road is never
// regretted, since a road that joins the same two groups at no lower cost was seen first.
// "Already connected?" is union-find: every city points toward the root of its group, and two
// cities are connected when they reach the same root.
//
// This file counts EDGES up to n - 1: `count == n - 1` states the fact the problem rests on -- a
// tree on n nodes has n - 1 edges -- so the stopping rule and the -1 check are the same line read
// twice, and n == 1 needs no special case (count and n - 1 are both 0). The two methods differ
// only in how the 1-based city labels meet a 0-based array: one shifts every label down by one,
// the other leaves the labels alone and gives slot 0 away. The choice is free.
//
// The three 1135 files are a ladder, and each rung adds exactly one thing:
//   1. ConnectCitiesMinCostEdgeCount  -- Kruskal, counting EDGES up to n - 1
//   2. ConnectCitiesMinCost           -- the same, counting COMPONENTS down to 1
//   3. ConnectCitiesMinCostCounting   -- rung 2 with the comparison sort replaced
//                                        by a counting sort on the cost
// All three return the same answer on every input. Rung 1 is the one to write.
// The two counts are the same fact from opposite ends: a spanning tree has exactly one
// component and exactly n - 1 edges.
//
// Time: O(m log m) -- the sort dominates; the union-find walk is nearly free.
// Space: O(n) -- the parent and size arrays (the sort is in place).

public final class ConnectCitiesMinCostEdgeCount {
	private DisjointSet ds;

	// cities are 1..n, so every label is shifted down by one to index 0..n-1
	public int minimumCostShifted(int n, int[][] connections) {
		this.ds = new DisjointSet(n);
		Arrays.sort(connections, comparingInt(connection -> connection[2])); // SORT: cheapest road first
		int total = 0;
		int count = 0; // COUNT: roads built so far
		for (int[] connection : connections) {
			int from = connection[0] - 1;
			int to = connection[1] - 1;
			int cost = connection[2];
			// union is the loop check and the join in one call
			if (ds.union(from, to)) { // TAKE: not connected yet, build the road
				total += cost;
				count += 1;
			}
			if (count == n - 1) {
				break; // STOP: a tree on n cities is complete
			}
		}
		// RETURN: fewer than n - 1 roads built means the cities were never all joined,
		// and the total of a forest is a plausible number that is not an answer.
		// n == 1 needs no special case: count and n - 1 are both 0.
		return count < n - 1 ? -1 : total;
	}

	// the same, leaving the labels alone and giving slot 0 away unused
	public int minimumCostPadded(int n, int[][] connections) {
		this.ds = new DisjointSet(n + 1);
		Arrays.sort(connections, comparingInt(connection -> connection[2])); // SORT: cheapest road first
		int total = 0;
		int count = 0;
		for (int[] connection : connections) {
			if (ds.union(connection[0], connection[1])) { // TAKE
				total += connection[2];
				count += 1;
			}
			if (count == n - 1) {
				break; // STOP
			}
		}
		return count < n - 1 ? -1 : total; // RETURN
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
			while (root != parent[root]) { // WALK: up to the root
				root = parent[root];
			}
			parent[x] = root; // COMPRESS: the asked-about city points straight at the root
			return root;
		}

		boolean union(int a, int b) {
			int ra = find(a);
			int rb = find(b);
			if (ra == rb) {
				return false;
			}
			if (size[ra] > size[rb]) { // JOIN: the smaller group goes under the larger
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
