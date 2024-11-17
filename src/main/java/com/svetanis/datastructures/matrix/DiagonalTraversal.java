package com.svetanis.datastructures.matrix;

import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 498. Diagonal Traverse

public final class DiagonalTraversal {
	// Time Complexity: O(n * m)
	// Space Complexity: O(min(n, m))

	public static int[] diagonal(int[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;
		int index = 0;
		int[] a = new int[rows * cols];
		List<Integer> list = new ArrayList<>();
		for (int diagonal = 0; diagonal < rows + cols - 1; diagonal++) {
			int row = diagonal < cols ? 0 : diagonal - cols + 1;
			int col = diagonal < cols ? diagonal : cols - 1;
			while (row < rows && col >= 0) {
				list.add(grid[row][col]);
				row++;
				col--;
			}
			if (diagonal % 2 == 0) {
				Collections.reverse(list);
			}
			for (int element : list) {
				a[index++] = element;
			}
			list.clear();
		}
		return a;
	}

	public static void main(String[] args) {
		int[][] m1 = { //
				{ 1, 2, 3 }, //
				{ 4, 5, 6 }, //
				{ 7, 8, 9 } //
		};//
		print(diagonal(m1)); // 1,2,4,7,5,3,6,8,9

		int[][] m2 = { //
				{ 1, 2 }, //
				{ 3, 4 } //
		};//
		print(diagonal(m2)); // 1,2,3,4
	}
}
