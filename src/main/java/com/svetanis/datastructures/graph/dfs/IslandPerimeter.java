package com.svetanis.datastructures.graph.dfs;

// 463. Island Perimeter

public final class IslandPerimeter {
	// Time complexity: O(ROW x COL)

	public static int perimeter(int[][] g) {
		int perimeter = 0;
		int rows = g.length;
		int cols = g[0].length;
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				if (g[i][j] == 1) {
					perimeter += 4;
					if (i < rows - 1 && g[i + 1][j] == 1) {
						perimeter -= 2;
					}
					if (j < cols - 1 && g[i][j + 1] == 1) {
						perimeter -= 2;
					}
				}
			}
		}
		return perimeter;
	}

	public static void main(String[] args) {
		int[][] g = { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 1, 1, 0, 0 } };
		System.out.println(perimeter(g)); // 16

		int[][] g2 = { { 1 } };
		System.out.println(perimeter(g2)); // 4

		int[][] g3 = { { 1, 0 } };
		System.out.println(perimeter(g3)); // 4

		int[][] g4 = { { 1, 1 }, { 1, 1 } };
		System.out.println(perimeter(g4)); // 8
	}
}
