package com.svetanis.datastructures.graph.unionfind;

// 1135. Connecting Cities With Minimum Cost

// Kruskal, but the connections reach the loop in cost order without ever
// being compared with each other. the costs are whole numbers no larger
// than 100,000, and counting how many carry each value orders them in
// three straight passes -- so the sort stops being the expensive half

public final class ConnectCitiesMinCostCounting {
	// Time Complexity: O(n + m + c), c = the largest cost present
	// Space Complexity: O(n + m + c)

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
		// reaching n cities takes n - 1 connections, so anything shorter
		// leaves a city stranded whatever the costs are
		if (connections.length < n - 1) {
			return -1;
		}
		initParent(n);
		int totalCost = 0;
		int remainingComponents = n;
		for (int index : byAscendingCost(connections)) {
			int[] connection = connections[index];
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
		for (int[] connection : connections) {
			start[connection[2] + 1] += 1;
		}
		for (int cost = 1; cost <= maxCost + 1; cost++) {
			start[cost] += start[cost - 1];
		}
		int[] order = new int[connections.length];
		for (int i = 0; i < connections.length; i++) {
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
