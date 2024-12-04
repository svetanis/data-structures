package com.svetanis.datastructures.graph.theory;

// 997. Find the Town Judge

public final class TownJudge {
	// Time Complexity: O(n + m)
	// Space Complexity: O(n + m)

	public static int townJudge(int n, int[][] trust) {
		int[] trustCount = new int[n + 1];
		int[] trustedByCount = new int[n + 1];
		for (int[] relation : trust) {
			int trusts = relation[0];
			int trusted = relation[1];
			trustCount[trusts]++;
			trustedByCount[trusted]++;
		}
		for (int i = 1; i <= n; i++) {
			if (trustCount[i] == 0 && trustedByCount[i] == n - 1) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2 } };
		int[][] g2 = { { 1, 3 }, { 2, 3 } };
		int[][] g3 = { { 1, 3 }, { 2, 3 }, { 3, 1 } };
		System.out.println(townJudge(2, g1)); // 2
		System.out.println(townJudge(3, g2)); // 3
		System.out.println(townJudge(3, g3)); // -1
	}
}
