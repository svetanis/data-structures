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
// Time: O(E log E + N) -- E = n + pipes edges including the wells; the sort dominates, then one find
// per end of each edge.
// Space: O(E + N) -- the combined edge array and the parent array.
//
// Siblings: WaterDistributionRank joins with union by rank in a separate method and does not stop
// early; this file hangs one root under the other without weighing and stops once n joins are made.

public final class WaterDistribution {

	private int[] parent;

	public int minCost(int n, int[] wells, int[][] pipes) {
		initParent(n);
		int[][] connections = connections(n, wells, pipes);
		// sort connections by cost
		Arrays.sort(connections, comparingInt(a -> a[2])); // SORT: cheapest first
		return totalCost(n, connections);
	}

	private int totalCost(int n, int[][] connections) {
		int totalCost = 0;
		for (int[] connection : connections) {
			int pa = find(connection[0]);
			int pb = find(connection[1]);
			// if both have the same root
			// they are already connected
			if (pa == pb) {
				continue; // SKIP: this pipe would close a loop
			}
			// otherwise, connect them
			parent[pa] = pb; // JOIN the two groups
			// and add the cost
			totalCost += connection[2]; // TAKE: pay for the pipe
			if (--n == 0) {
				break; // n + 1 houses are joined by n pipes: done
			}
		}
		return totalCost;
	}

	private void initParent(int n) {
		this.parent = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			parent[i] = i; // BASE: house 0 already has parent[0] == 0
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

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]); // WALK up, COMPRESS on the way back
		}
		return parent[x];
	}

	public static void main(String[] args) {
		WaterDistribution wd = new WaterDistribution();
		int[] wells = { 1, 2, 2 };
		int[][] pipes = { { 1, 2, 1 }, { 2, 3, 1 } };
		System.out.println(wd.minCost(3, wells, pipes)); // 3 -- one well (1) and two pipes (1 + 1)

		int[] wells2 = { 1, 1 };
		int[][] pipes2 = { { 1, 2, 1 }, { 1, 2, 2 } };
		System.out.println(wd.minCost(2, wells2, pipes2)); // 2 -- a well and the cheap pipe, or a well each
	}
}