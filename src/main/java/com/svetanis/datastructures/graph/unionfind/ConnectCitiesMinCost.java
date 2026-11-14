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
// This file counts COMPONENTS down to 1: start with n groups, and every road built merges two,
// so the answer is ready the moment the count reaches 1. n == 1 needs a special case, since no
// road is ever built and the count never moves.
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
// Time: O(m log m) -- sorting the m roads; each union-find step is close to constant.
// Space: O(n) -- the parent and rank arrays (the sort is in place).

public final class ConnectCitiesMinCost {

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
		Arrays.sort(connections, comparingInt(c -> c[2])); // SORT: cheapest road first
		int totalCost = 0;
		int remainingComponents = n; // COUNT: groups still apart
		for (int[] connection : connections) {
			if (!union(connection[0] - 1, connection[1] - 1)) {
				continue; // SKIP: already connected, this road would close a loop
			}
			totalCost += connection[2]; // TAKE: build the road
			remainingComponents -= 1;
			if (remainingComponents == 1) {
				return totalCost; // RETURN: one group left, every city is joined
			}
		}
		return -1; // RETURN: roads ran out with groups still apart
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
	// compresses the chain, and one later edge touching its head would
	// recurse the full depth of the chain
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
		System.out.println(ccc.minCost(3, g)); // 6

		int[][] g2 = { { 1, 2, 3 }, { 3, 4, 4 }, { 1, 4, 7 }, { 2, 3, 5 } };
		System.out.println(ccc.minCost(4, g2)); // 12

		// a single city, already connected, nothing to buy: no union is ever made
		// to bring the component count down to 1, so n == 1 is answered up front
		System.out.println(ccc.minCost(1, new int[0][])); // 0

		// no edge joins city 3 to the rest, so no spanning tree exists
		int[][] g3 = { { 1, 2, 5 } };
		System.out.println(ccc.minCost(3, g3)); // -1
	}
}
