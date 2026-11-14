package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayList;
import java.util.List;

// 296. Best Meeting Point
//
// Input: a grid of 0 and 1; a 1 is a friend's home, a 0 is empty ground. All friends walk to one
// meeting point, one step up, down, left or right at a time, so a friend at (r1, c1) walks
// |r1 - r2| + |c1 - c2| steps to a point (r2, c2). Returns the smallest possible total of those
// steps over all friends. On LeetCode: 1 <= m, n <= 200, grid[i][j] is 0 or 1, at least two friends.
//
// The one idea: the walk is a row part plus a column part, and the two never interfere, so choose
// the meeting row and the meeting column separately. On one line of numbers, the best meeting
// number is the middle one when the friends' numbers are in order: from the middle, a step either
// way moves at least as many friends farther away as closer. One list entry here is the row of
// one friend (in rows) or the column of one friend (in cols). Scanning the grid row by row
// hands out the rows already in order, and scanning column by column hands out the columns
// already in order, so no sort is needed. The answer is the total distance of the rows to their
// middle row plus the total distance of the columns to their middle column.
//
// Time: O(r * c) -- two scans of the grid; the middle is read by index, nothing is sorted.
// Space: O(f) -- f is the number of friends, at most r * c; each friend adds one number to each list.
//
// Siblings: BestMeetingPointSort collects rows and columns in one scan and sorts the columns;
// BestMeetingPointBfs tries every cell as the meeting point with a breadth-first walk from it.

public final class BestMeetingPoint {

	public int bmp(int[][] grid) {
		List<Integer> rows = rows(grid);
		List<Integer> cols = cols(grid);
		int medianRow = rows.get(rows.size() >> 1); // MEDIAN: the middle friend's row
		int medianCol = cols.get(cols.size() >> 1); // MEDIAN: the middle friend's column
		return minDist(rows, medianRow) + minDist(cols, medianCol); // RETURN: row steps + column steps
	}

	private List<Integer> rows(int[][] grid) {
		List<Integer> rows = new ArrayList<>();
		for (int r = 0; r < grid.length; r++) { // WALK: row by row, so rows come out in order
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] == 1) {
					rows.add(r); // RECORD: this friend's row
				}
			}
		}
		return rows;
	}

	private List<Integer> cols(int[][] grid) {
		List<Integer> cols = new ArrayList<>();
		for (int c = 0; c < grid[0].length; c++) { // WALK: column by column, so columns come out in order
			for (int r = 0; r < grid.length; r++) {
				if (grid[r][c] == 1) {
					cols.add(c); // RECORD: this friend's column
				}
			}
		}
		return cols;
	}

	private int minDist(List<Integer> points, int origin) {
		int sum = 0;
		for (int point : points) {
			sum += Math.abs(point - origin); // TAKE: this friend's steps along one axis
		}
		return sum;
	}

	public static void main(String[] args) {
		BestMeetingPoint bmp = new BestMeetingPoint();
		int[][] grid = { { 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0 } };
		System.out.println(bmp.bmp(grid)); // 6 point (0,2)

		int[][] grid1 = { { 1, 1 } };
		System.out.println(bmp.bmp(grid1)); // 1
	}
}
