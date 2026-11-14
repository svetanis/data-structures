package com.svetanis.datastructures.graph.unionfind;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;

// 1168. Optimize Water Distribution in a Village
//
// Input: n -- houses numbered 1..n; wells[i - 1] -- the cost of digging a well in house i;
// pipes[j] = [house1, house2, cost] -- the cost of laying a pipe between two houses. A house has
// water if it has a well or is joined by pipes to a house that has one. Returns the least total
// cost to give every house water. On LeetCode: 2 <= n <= 10^4, wells.length == n,
// 0 <= wells[i] <= 10^5, 1 <= pipes.length <= 10^4, pipes[j].length == 3, 1 <= house1, house2 <= n,
// 0 <= cost <= 10^5, house1 != house2; several pipes may join the same two houses.
//
// The one idea: invent a house 0 that stands for the water source, and treat "a well in house i" as
// a pipe from house 0 to house i that costs wells[i - 1]. Now every house has water exactly when
// it is connected to house 0, and the job is to connect all n + 1 houses as cheaply as possible --
// a minimum spanning tree, built by Kruskal's method: sort every pipe by cost, and take a pipe only
// when its two ends are not yet connected (union-find tells you), which never takes a pipe that
// closes a loop. Taking that pipe is always safe: every complete plan has to link this pipe's group
// to the rest by some pipe, and every pipe not yet looked at costs at least as much.
// One element is one house (0 is the source).
//
// Time: O(E log E + N) -- E = n + pipes edges including the wells; the sort dominates, then one
// union per edge, each near-constant.
// Space: O(E + N) -- the combined edge array, the parent array and the rank array.
//
// Siblings: WaterDistribution joins without ranks and stops once n joins are made; this file keeps a
// rank per root, so the trees stay shallow, and goes through every edge.

public final class WaterDistributionRank {

	private int[] rank;
	private int[] parent;

	public int minCost(int n, int[] wells, int[][] pipes) {
		initParent(n);
		int[][] connections = connections(n, wells, pipes);
		// sort connections by cost
		Arrays.sort(connections, comparingInt(a -> a[2])); // SORT: cheapest first
		int totalCost = 0;
		for (int[] connection : connections) {
			if (union(connection[0], connection[1])) { // JOIN; a refusal means a loop
				totalCost += connection[2]; // TAKE: pay for the pipe
			}
		}
		return totalCost;
	}

	private void initParent(int n) {
		this.rank = new int[n + 1];
		this.parent = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			parent[i] = i; // BASE: house 0 already has parent[0] == 0
			rank[i] = 0;
		}
	}

	private int[][] connections(int n, int[] wells, int[][] pipes) {
		int index = 0;
		int[][] grid = new int[n + pipes.length][3];
		for (int[] pipe : pipes) {
			grid[index++] = pipe;
		}
		for (int i = 0; i < n; i++) {
			grid[index++] = new int[] { 0, i + 1, wells[i] }; // a well is a pipe from house 0
		}
		return grid;
	}

	private boolean union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (px == py) {
			return false; // already connected
		}
		if (rank[px] > rank[py]) {
			parent[py] = px; // JOIN: lower rank under higher
		} else if (rank[px] < rank[py]) {
			parent[px] = py;
		} else {
			parent[py] = px;
			rank[px] += 1; // RAISE: only when equal ranks meet
		}
		return true;
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]); // WALK up, COMPRESS on the way back
		}
		return parent[x];
	}

	public static void main(String[] args) {
		WaterDistributionRank wdr = new WaterDistributionRank();
		int[] wells = { 1, 2, 2 };
		int[][] pipes = { { 1, 2, 1 }, { 2, 3, 1 } };
		System.out.println(wdr.minCost(3, wells, pipes)); // 3 -- one well (1) and two pipes (1 + 1)

		int[] wells2 = { 1, 1 };
		int[][] pipes2 = { { 1, 2, 1 }, { 1, 2, 2 } };
		System.out.println(wdr.minCost(2, wells2, pipes2)); // 2 -- a well and the cheap pipe, or a well each
	}
}