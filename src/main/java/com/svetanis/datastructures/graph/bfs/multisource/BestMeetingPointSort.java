package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 296. Best Meeting Point

public final class BestMeetingPointSort {
	// Time complexity: O(r * c)
	// Space Complexity: O(r + c)

	public int bmp(int[][] grid) {
		List<Integer> rows = new ArrayList<>();
		List<Integer> cols = new ArrayList<>();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] == 1) {
					rows.add(r);
					cols.add(c);
				}
			}
		}
		Collections.sort(cols);
		int medianRow = rows.get(rows.size() >> 1);
		int medianCol = cols.get(cols.size() >> 1);
		return dist(rows, medianRow) + dist(cols, medianCol);
	}

	private int dist(List<Integer> list, int median) {
		int sum = 0;
		for (int coordinate : list) {
			sum += Math.abs(coordinate - median);
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
