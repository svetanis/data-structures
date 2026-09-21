package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.List;

// 305. Number of Islands II
// NumberOfIslandsIISubmit is the same algorithm with the structure inlined.
//
// The problem that makes union-find necessary rather than convenient: the grid
// CHANGES k times and the answer is wanted after every change. A flood fill has
// to re-walk the whole grid each time and keeps nothing between runs. Measured at
// the constraint maximum (m*n = 10^4, k = 10^4): 2 ms against 2036 ms.
//
// The count is maintained, never recomputed: +1 for the new cell, then -1 for
// every union that actually merged something.

public final class NumberOfIslandsII {
	// Time Complexity: O(k) for k positions, times the cost of find
	// Space Complexity: O(rows * cols)

	private static final int[] DR = { -1, 1, 0, 0 };
	private static final int[] DC = { 0, 0, -1, 1 };

	private DisjointSet ds;
	private int[][] grid;

	public List<Integer> numIslands2(int rows, int cols, int[][] positions) {
		int size = rows * cols;
		this.ds = new DisjointSet(size);
		this.grid = new int[rows][cols];
		int count = 0;
		List<Integer> answer = new ArrayList<>();
		for (int[] position : positions) {
			int r = position[0], c = position[1];
			int k = r * cols + c;
			// the same cell twice: nothing changes, but an answer is still owed.
			// without this the cell is counted as new and then fails to merge with
			// itself, inflating every later answer.
			if (grid[r][c] == 1) {
				answer.add(count);
				continue;
			}
			grid[r][c] = 1;
			// optimistic: its own island until a neighbour says otherwise.
			// the cautious rule -- look first, and only increment if there are no
			// neighbours -- cannot work. Placing the middle cell of  1 . 1  takes the
			// count from 2 DOWN to 1, and "do not increment" leaves it at 2. How many
			// islands the neighbours belonged to is the whole answer, and only union
			// knows it.
			count += 1;
			for (int d = 0; d < 4; d++) {
				// all four directions: this cell can join islands on any side
				int x = r + DR[d], y = c + DC[d];
				if (x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == 1) {
					// computed INSIDE the guard on purpose. Off the grid, y = -1 makes
					// x * cols - 1 a perfectly valid index naming the row above's last
					// cell, so an unguarded read merges two cells that do not touch and
					// returns a plausible wrong count rather than throwing.
					int nk = x * cols + y;
					// two neighbours already in one island merge only once, and
					// the boolean is what tells the two cases apart
					if (ds.union(k, nk)) {
						count -= 1;
					}
				}
			}
			answer.add(count);
		}
		return answer;
	}

	public static void main(String[] args) {
		NumberOfIslandsII nii = new NumberOfIslandsII();
		int[][] positions = { { 0, 0 }, { 0, 1 }, { 1, 2 }, { 2, 1 } };
		System.out.println(nii.numIslands2(3, 3, positions)); // [1, 1, 2, 3]

		// the same cell twice, and a cell that joins two islands at once
		int[][] repeats = { { 0, 0 }, { 0, 2 }, { 0, 0 }, { 0, 1 } };
		System.out.println(new NumberOfIslandsII().numIslands2(1, 3, repeats)); // [1, 2, 2, 1]
	}
}
