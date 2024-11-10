package com.svetanis.datastructures.matrix;

// 766. Toeplitz Matrix

public final class ToeplitzMatrixSimple {
	// Time Complexity: O(n^2)

	public static boolean isToeplitz(int[][] matrix) {

		int n = matrix.length;
		int m = matrix[0].length;
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				if (matrix[i][j] != matrix[i - 1][j - 1]) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[][] matrix = { //
				{ 6, 7, 8, 9 }, //
				{ 4, 6, 7, 8 }, //
				{ 1, 4, 6, 7 }, //
				{ 0, 1, 4, 6 }, //
				{ 2, 0, 1, 4 }//
		};//
		System.out.println(isToeplitz(matrix));
	}
}
