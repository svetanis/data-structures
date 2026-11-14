package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayList;
import java.util.Collections;
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
// number is the middle one when the friends' numbers are in order. One list entry here is the row
// of one friend (in rows) or the column of one friend (in cols). One scan row by row hands out the
// rows already in order, but the columns come out jumbled, so the columns are sorted. The answer
// is the total distance of the rows to their middle row plus the total distance of the columns
// to their middle column.
//
// Time: O(r * c + f log f) -- one scan of the grid, then a sort of the f column numbers
// (f is the number of friends, at most r * c).
// Space: O(f) -- each friend adds one number to each list.
//
// Siblings: BestMeetingPoint scans twice, once by rows and once by columns, so it needs no sort;
// BestMeetingPointBfs tries every cell as the meeting point with a breadth-first walk from it.

public final class BestMeetingPointSort {

	public int bmp(int[][] grid) {
		List<Integer> rows = new ArrayList<>();
		List<Integer> cols = new ArrayList<>();
		for (int r = 0; r < grid.length; r++) { // WALK: row by row, so rows come out in order
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] == 1) {
					rows.add(r); // RECORD: this friend's row and column
					cols.add(c);
				}
			}
		}
		Collections.sort(cols); // SORT: the columns came out jumbled
		int medianRow = rows.get(rows.size() >> 1); // MEDIAN: the middle friend's row
		int medianCol = cols.get(cols.size() >> 1); // MEDIAN: the middle friend's column
		return dist(rows, medianRow) + dist(cols, medianCol); // RETURN: row steps + column steps
	}

	private int dist(List<Integer> list, int median) {
		int sum = 0;
		for (int coordinate : list) {
			sum += Math.abs(coordinate - median); // TAKE: this friend's steps along one axis
		}
		return sum;
	}

	public static void main(String[] args) {
		BestMeetingPointSort bmp = new BestMeetingPointSort();
		int[][] grid = { { 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0 } };
		System.out.println(bmp.bmp(grid)); // 6 point (0,2)

		int[][] grid1 = { { 1, 1 } };
		System.out.println(bmp.bmp(grid1)); // 1
	}
}
