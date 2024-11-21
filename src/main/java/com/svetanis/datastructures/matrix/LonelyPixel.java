package com.svetanis.datastructures.matrix;

// 531. Lonely Pixel I

public final class LonelyPixel {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n + m)

	public static int lonelyPixel(char[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		int[] rows = new int[n];
		int[] cols = new int[m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (matrix[i][j] == 'B') {
					rows[i]++;
					cols[j]++;
				}
			}
		}
		int count = 0;
		for (int i = 0; i < n; i++) {
			if (rows[i] == 1) {
				for (int j = 0; j < m; j++) {
					if (matrix[i][j] == 'B' && cols[j] == 1) {
						count++;
						// lonely B for this row is found
						break;
					}
				}
			}
		}
		return count;
	}

	public static void main(String[] args) {
		char[][] m1 = { { 'W', 'B', 'W' }, { 'W', 'W', 'W' }, { 'B', 'W', 'B' } };
		System.out.println(lonelyPixel(m1)); // 1

		char[][] m2 = { { 'W', 'W', 'B' }, { 'W', 'B', 'W' }, { 'B', 'W', 'W' } };
		System.out.println(lonelyPixel(m2)); // 3
	}
}
