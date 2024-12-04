package com.svetanis.datastructures.graph.theory;

// 1615. Maximal Network Rank

public final class MaxNetworkRank {
	// Time Complexity: O(n^2 + m)

	public static int maxNetworkRank(int n, int[][] roads) {
		int[][] g = new int[n][n];
		int[] inDegree = new int[n];
		init(roads, g, inDegree);
		return mnr(g, inDegree);
	}

	private static int mnr(int[][] g, int[] inDegree) {
		int max = 0;
		for (int i = 0; i < g.length; i++) {
			for (int j = i + 1; j < g.length; j++) {
				int rank = inDegree[i] + inDegree[j] - g[i][j];
				max = Math.max(max, rank);
			}
		}
		return max;
	}

	private static void init(int[][] roads, int[][] g, int[] inDegree) {
		for (int[] road : roads) {
			int from = road[0];
			int to = road[1];
			g[from][to] = 1;
			g[to][from] = 1;
			inDegree[from]++;
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
