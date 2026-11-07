package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 2201. Count Artifacts That Can Be Extracted
//
// artifacts(n, a, dig) takes an n x n grid, the buried artifacts a -- each { r1, c1, r2, c2 },
// the rectangle from top-left (r1, c1) to bottom-right (r2, c2), corners included -- and the
// cells dug, each { r, c }. Returns how many artifacts have every one of their cells dug. On
// LeetCode: 1 <= n <= 1000, artifacts never overlap, each covers at most 4 cells, the dug
// cells are distinct.
//
// Put the dug cells in a set, then test each artifact cell by cell: one undug cell and that
// artifact stays buried. A cell (r, c) goes in the set as the single number r * n + c, its
// position when the grid is read row by row; with 0 <= c < n no two cells share a number.
//
// Siblings, same problem: none in this package.

public final class CountArtifacts {
	// Time complexity: O(d + s), d = cells dug, s = cells the artifacts cover (at most 4 each)
	// Auxiliary Space: O(d) -- the set of dug cells

	public static int artifacts(int n, int[][] a, int[][] dig) {
		Set<Integer> set = new HashSet<>(); // DUG: each cell as row * n + column
		for (int[] cell : dig) {
			set.add(cell[0] * n + cell[1]);
		}
		int count = 0;
		for (int[] artifact : a) {
			if (isArtifact(artifact, set, n)) {
				count++; // COUNT: every cell of this one was dug
			}
		}
		return count;
	}

	private static boolean isArtifact(int[] artifact, Set<Integer> set, int n) {
		int top = artifact[0], bottom = artifact[2];
		int left = artifact[1], right = artifact[3];
		for (int i = top; i <= bottom; i++) { // CELLS: corners included, so <=
			for (int j = left; j <= right; j++) {
				if (!set.contains(i * n + j)) { // BURIED?: one undug cell is enough
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[][] a1 = { { 0, 0, 0, 0 }, { 0, 1, 1, 1 } };
		int[][] d1 = { { 0, 0 }, { 0, 1 } };
		System.out.println(artifacts(2, a1, d1)); // 1

		int[][] a2 = { { 0, 0, 0, 0 }, { 0, 1, 1, 1 } };
		int[][] d2 = { { 0, 0 }, { 0, 1 }, { 1, 1 } };
		System.out.println(artifacts(2, a2, d2)); // 2
	}
}
