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
// RUNG 2: counts components DOWN to 1 rather than edges UP to n - 1. The two are
// the same fact from opposite ends, since a spanning tree has exactly one component
// and exactly n - 1 edges, and neither stops earlier than the other. What it costs
// is a special case: with n == 1 there is no union to make, so the count never
// reaches 1 by merging and the method would fall through to -1. Rung 1 needs no
// such case -- count and n - 1 are both 0.

public final class ConnectCitiesMinCost {
	// Time Complexity: O(m log m)
	// Space Complexity: O(n)

	private int[] rank;
	private int[] parent;

	public int minCost(int n, int[][] connections) {
		// one city is already connected to all of itself, so it costs
		// nothing. the loop below reports success only when a union
		// drops the component count to 1, and with n == 1 there is no
		// union to make -- it would fall through and answer -1
		if (n <= 1) {
			return 0;
		}
		initParent(n);
		Arrays.sort(connections, comparingInt(c -> c[2]));
		int totalCost = 0;
		int remainingComponents = n;
		for (int[] connection : connections) {
			if (!union(connection[0] - 1, connection[1] - 1)) {
				continue;
			}
			totalCost += connection[2];
			remainingComponents -= 1;
			if (remainingComponents == 1) {
				return totalCost;
			}
		}
		return -1;
	}

	private void initParent(int n) {
		this.rank = new int[n];
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
	}

	// two find calls, not four: writing parent[find(x)] = find(y) walks
	// both trees a second time to reach the roots it has already found
	private boolean union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (px == py) {
			return false;
		}
		// attach the shorter tree under the taller one. without this, the
		// cheap edges 1-2, 2-3, 3-4 ... each attach the old root under the
		// new city and build one chain as deep as the graph
		if (rank[px] < rank[py]) {
			parent[px] = py;
		} else if (rank[px] > rank[py]) {
			parent[py] = px;
		} else {
			parent[py] = px;
			rank[px] += 1;
		}
		return true;
	}

	// iterative, so depth cannot reach the call stack. compression does
	// not save a recursive version on its own: along a chain built as
	// above every find is called on a shallow endpoint, so nothing ever
	// compresses the chain, and one later edge touching its head recurses
	// the full depth -- StackOverflowError from about 50,000 cities
	private int find(int node) {
		while (parent[node] != node) {
			// path halving: point each node at its grandparent on the way
			// up, which halves the chain without a second pass
			parent[node] = parent[parent[node]];
			node = parent[node];
		}
		return node;
	}

	public static void main(String[] args) {
		ConnectCitiesMinCost ccc = new ConnectCitiesMinCost();
		int[][] g = { { 1, 2, 5 }, { 1, 3, 6 }, { 2, 3, 1 } };
		System.out.println(ccc.minCost(3, g));

		int[][] g2 = { { 1, 2, 3 }, { 3, 4, 4 }, { 1, 4, 7 }, { 2, 3, 5 } };
		System.out.println(ccc.minCost(4, g2)); // 12

		// a single city, already connected, nothing to buy. the loop
		// reports success only after a union drops the component count
		// to 1, and there is no union to make here -- this printed -1
		System.out.println(ccc.minCost(1, new int[0][])); // 0

		// no edge joins city 3 to the rest, so no spanning tree exists
		int[][] g3 = { { 1, 2, 5 } };
		System.out.println(ccc.minCost(3, g3)); // -1
	}
}
