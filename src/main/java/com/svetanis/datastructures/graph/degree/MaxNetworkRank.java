package com.svetanis.datastructures.graph.degree;

// 1615. Maximal Network Rank
//
// Input: n cities numbered 0..n-1, and roads, a list of two-way roads [a, b]. On LeetCode:
// 2 <= n <= 100, 0 <= roads.length <= n * (n - 1) / 2, a != b, and no pair of cities has more than
// one road between them.
// Returns: the largest network rank over all pairs of different cities. The network rank of a pair
// is the number of roads that touch at least one of the two cities; a road between the two is
// counted once.
//
// The one idea: a city's degree is the number of roads at it. For a pair (i, j), adding the two
// degrees counts every road at i and every road at j, and a road that joins i and j is in both
// counts, so it was counted twice and one copy has to come off. The matrix g[i][j] is 1 when
// there is a road between i and j, so rank(i, j) = degree[i] + degree[j] - g[i][j]. Try every pair
// and keep the largest.
//
// Time: O(n^2 + m) -- m steps to read the roads and fill the degrees and the matrix, then n^2 / 2
// pairs.
// Space: O(n^2) -- the n x n matrix.
//
// Siblings: none.

public final class MaxNetworkRank {

	public static int maxNetworkRank(int n, int[][] roads) {
		int[][] g = new int[n][n]; // MATRIX: g[i][j] -- 1 if a road joins cities i and j
		int[] inDegree = new int[n]; // CARDS: inDegree[v] -- roads at v (two-way, so in and out are the same)
		init(roads, g, inDegree);
		return mnr(g, inDegree);
	}

	private static int mnr(int[][] g, int[] inDegree) {
		int max = 0;
		for (int i = 0; i < g.length; i++) {
			for (int j = i + 1; j < g.length; j++) { // PAIR: each unordered pair once
				int rank = inDegree[i] + inDegree[j] - g[i][j]; // COUNT: a shared road is in both degrees, take it off once
				max = Math.max(max, rank); // KEEP: the best pair so far
			}
		}
		return max;
	}

	private static void init(int[][] roads, int[][] g, int[] inDegree) {
		for (int[] road : roads) {
			int from = road[0];
			int to = road[1];
			g[from][to] = 1; // RECORD: the road, in both directions
			g[to][from] = 1;
			inDegree[from]++; // COUNT: one more road at each end
			inDegree[to]++;
		}
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 1, 3 } };
		System.out.println(maxNetworkRank(4, g1)); // 4
		int[][] g2 = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 1, 3 }, { 2, 3 }, { 2, 4 } };
		System.out.println(maxNetworkRank(5, g2)); // 5
		int[][] g3 = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 2, 4 }, { 5, 6 }, { 5, 7 } };
		System.out.println(maxNetworkRank(8, g3)); // 5
	}
}
