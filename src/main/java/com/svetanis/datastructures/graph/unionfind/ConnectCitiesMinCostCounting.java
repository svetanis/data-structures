package com.svetanis.datastructures.graph.unionfind;

// 1135. Connecting Cities With Minimum Cost
//
// Input: n cities numbered 1..n and connections[i] = [x, y, cost], a road that can be built
// between cities x and y for that cost. On LeetCode: 1 <= n <= 10^4, 1 <= connections.length <=
// 10^4, x != y, 0 <= cost <= 10^5. The array is left as it is.
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
// This file is rung 3: rung 2 (count COMPONENTS down to 1) with the comparison sort replaced by a
// counting sort. That is possible only because the statement BOUNDS THE COST (whole numbers up to
// 10^5): count how many roads carry each cost, add the counts up to learn where each cost starts
// in the sorted order, and drop each road into its place. Without that bound there is no counter
// array to build, and rung 2 is all there is. n == 1 needs a special case, since no road is ever
// built and the count never moves.
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
// Time: O(n + m + c) -- c is the largest cost present; no log factor, because nothing is compared.
// Space: O(n + m + c) -- the counter array of size c and the order array of size m.

public final class ConnectCitiesMinCostCounting {

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
		// reaching n cities takes n - 1 roads, so anything shorter
		// leaves a city stranded whatever the costs are
		if (connections.length < n - 1) {
			return -1;
		}
		initParent(n);
		int totalCost = 0;
		int remainingComponents = n; // COUNT: groups still apart
		for (int index : byAscendingCost(connections)) { // SORT: cheapest road first, by counting
			int[] connection = connections[index];
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

	// returns positions into connections, cheapest first. counting sort:
	// count how many connections carry each cost, add those counts up to
	// learn where each cost begins, then place each connection at the
	// position its own cost points to. no comparison anywhere, so the
	// log factor of a comparison sort never appears
	private int[] byAscendingCost(int[][] connections) {
		int maxCost = maxCost(connections);
		// one extra slot on each end: the count for cost c is parked at
		// c + 1, so that after the running total below, start[c] holds
		// where cost c begins rather than where it ends
		int[] start = new int[maxCost + 2];
		for (int[] connection : connections) { // COUNT: how many roads carry each cost
			start[connection[2] + 1] += 1;
		}
		for (int cost = 1; cost <= maxCost + 1; cost++) { // ADD UP: running total gives each cost's start
			start[cost] += start[cost - 1];
		}
		int[] order = new int[connections.length];
		for (int i = 0; i < connections.length; i++) { // PLACE: each road at its cost's next free slot
			int cost = connections[i][2];
			order[start[cost]] = i;
			// the next connection of this same cost lands one slot along
			start[cost] += 1;
		}
		return order;
	}

	// the problem caps a cost at 100,000, but reading the real maximum
	// keeps the counter array no bigger than this input actually needs
	private int maxCost(int[][] connections) {
		int max = 0;
		for (int[] connection : connections) {
			max = Math.max(max, connection[2]);
		}
		return max;
	}

	// two find calls, not four: writing parent[find(x)] = find(y) walks
	// both trees a second time to reach the roots it has already found
	private boolean union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (px == py) {
			return false;
		}
		// attach the shorter tree under the taller one, so no single
		// chain of connections can build a tree as deep as the graph
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

	// iterative, so tree depth can never reach the call stack
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
		ConnectCitiesMinCostCounting ccc = new ConnectCitiesMinCostCounting();
		int[][] g = { { 1, 2, 5 }, { 1, 3, 6 }, { 2, 3, 1 } };
		System.out.println(ccc.minCost(3, g)); // 6

		int[][] g2 = { { 1, 2, 3 }, { 3, 4, 4 }, { 1, 4, 7 }, { 2, 3, 5 } };
		System.out.println(ccc.minCost(4, g2)); // 12

		// a single city, already connected, nothing to buy
		System.out.println(ccc.minCost(1, new int[0][])); // 0

		// no connection reaches city 3
		int[][] g3 = { { 1, 2, 5 } };
		System.out.println(ccc.minCost(3, g3)); // -1

		// a cost of 0 is allowed, and it is the first slot of the counter
		// array rather than a missing one
		int[][] g4 = { { 1, 2, 0 }, { 2, 3, 0 }, { 1, 3, 4 } };
		System.out.println(ccc.minCost(3, g4)); // 0
	}
}
