package com.svetanis.datastructures.matrix;

// 766. Toeplitz Matrix

public final class ToeplitzMatrix {
	// Time Complexity: O(n^2)

	public static boolean isToeplitz(int[][] matrix) {

		int n = matrix.length;
		int m = matrix[0].length;
		// do for each element in first row
		for (int i = 0; i < m; i++) {
			if (!isDiagonal(matrix, 0, i)) {
				return false;
			}
		}
		// do for each element in first col
		for (int i = 1; i < n; i++) {
			if (!isDiagonal(matrix, i, 0)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isDiagonal(int[][] matrix, int i, int j) {
		int n = matrix.length;
		int m = matrix[0].length;
		int first = matrix[i][j];
		while (i < n && j < m) {
			if (matrix[i][j] != first) {
				return false;
			}
			i++;
			j++;
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
