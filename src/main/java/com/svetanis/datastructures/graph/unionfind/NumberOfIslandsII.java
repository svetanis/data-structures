package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.List;

// 305. Number of Islands II
//
// Input: m and n -- the rows and columns of a grid that starts as all water; positions -- cells
// [row, col] turned into land one after another. An island is a group of land cells joined up, down,
// left or right. Returns a list with the number of islands after each position is added. On
// LeetCode: 1 <= m, n, positions.length <= 10^4, 1 <= m * n <= 10^4, 0 <= row < m, 0 <= col < n;
// the same cell may appear more than once.
//
// The one idea: the grid CHANGES k times and the answer is wanted after every change. A flood fill
// has to re-walk the whole grid each time and keeps nothing between runs; a union-find keeps the
// islands between additions. One element is one cell, numbered row * cols + col. The count is
// maintained, never recomputed: +1 for the new cell, then -1 for every union that actually merged
// two islands.
//
// Time: O(m * n + k * alpha(m * n)) -- m * n is the one-time DisjointSet set-up; each of the k
// positions makes at most four unions. A flood fill after every position would cost O(k * m * n).
// Space: O(m * n) -- the DisjointSet and the grid.
//
// Siblings: NumberOfIslandsIISubmit is the same algorithm with the structure inlined.

public final class NumberOfIslandsII {

	private static final int[] DR = { -1, 1, 0, 0 };
	private static final int[] DC = { 0, 0, -1, 1 };

	private DisjointSet ds;
	private int[][] grid;

	public List<Integer> numIslands2(int rows, int cols, int[][] positions) {
		int size = rows * cols;
		this.ds = new DisjointSet(size);
		this.grid = new int[rows][cols]; // 1 where a position has been added
		int count = 0; // islands right now
		List<Integer> answer = new ArrayList<>();
		for (int[] position : positions) {
			int r = position[0], c = position[1];
			int k = r * cols + c;
			// the same cell twice: nothing changes, but an answer is still owed.
			// without this the cell is counted as new and then fails to merge with
			// itself, inflating every later answer.
			if (grid[r][c] == 1) {
				answer.add(count);
				continue; // SKIP: a repeated cell changes nothing
			}
			grid[r][c] = 1; // TAKE: the cell becomes land
			// optimistic: its own island until a neighbour says otherwise.
			// the cautious rule -- look first, and only increment if there are no
			// neighbours -- cannot work. Placing the middle cell of  1 . 1  takes the
			// count from 2 DOWN to 1, and "do not increment" leaves it at 2. How many
			// islands the neighbours belonged to is the whole answer, and only union
			// knows it.
			count += 1; // COUNT: the new cell, as an island of its own
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
					if (ds.union(k, nk)) { // JOIN with a land neighbour
						count -= 1; // COUNT: two islands became one
					}
				}
			}
			answer.add(count); // RECORD: the count after this position
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
