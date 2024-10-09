package com.svetanis.datastructures.graph.islands;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Coordinate;

// given an n x m 2D binary grid 
// which represents a map of 1s (land)
// and 0s (water), return the number
// of islands

// an island is surrounded by water 
// and is formed by connecting adjacent
// lands horizontally or vertically.
// all four edges of the grid are 
// surrounded by water

public final class NumberOfIslandsBfs {
	// Time complexity: O(r * c)
	// Space Complexity: O(r + c)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };
	// horizontal + vertical + diagonal moves
	// private static int[] ddx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	// private static int[] ddy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int count(List<List<Integer>> grid) {
		int count = 0;
		int n = grid.size();
		int m = grid.get(0).size();
		Set<Coordinate> set = newHashSet();
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				int val = grid.get(r).get(c);
				Coordinate src = new Coordinate(r, c);
				if (val != 0 && !set.contains(src)) {
					set.add(src);
					bfs(src, grid, set);
					count++;
				}
			}
		}
		return count;
	}

	private static void bfs(Coordinate src, List<List<Integer>> grid, Set<Coordinate> set) {
		Queue<Coordinate> queue = new ArrayDeque<>();
		queue.add(src);
		while (!queue.isEmpty()) {
			Coordinate curr = queue.poll();
			List<Coordinate> neighbors = neighbors(curr, grid);
			for (Coordinate neighbor : neighbors) {
				if (!set.contains(neighbor)) {
					queue.add(neighbor);
				}
				set.add(neighbor);
			}
		}
	}

	private static List<Coordinate> neighbors(Coordinate src, List<List<Integer>> grid) {
		List<Coordinate> list = new ArrayList<>();
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int dist = 0; dist < dx.length; ++dist) {
			int x = src.getRow() + dx[dist];
			int y = src.getCol() + dy[dist];
			if (valid(grid, x, y) && grid.get(x).get(y) != 0) {
				list.add(new Coordinate(x, y));
			}
		}
		return list;
	}

	private static boolean valid(List<List<Integer>> grid, int row, int col) {
		boolean one = row >= 0 && row < grid.size(); // row number is in range
		boolean two = col >= 0 && col < grid.get(0).size(); // col number is in range
		return one && two;
	}

	public static void main(String[] args) {
		List<List<Integer>> grid1 = newArrayList();
		grid1.add(asList(1, 1, 0, 0, 0));
		grid1.add(asList(0, 1, 0, 0, 1));
		grid1.add(asList(1, 0, 0, 1, 1));
		grid1.add(asList(0, 0, 0, 0, 0));
		grid1.add(asList(1, 0, 1, 0, 1));
		System.out.println(count(grid1)); // 6

		List<List<Integer>> grid2 = newArrayList();
		grid2.add(asList(1, 1, 1, 1, 0));
		grid2.add(asList(1, 1, 0, 1, 0));
		grid2.add(asList(1, 1, 0, 0, 0));
		grid2.add(asList(0, 0, 0, 0, 0));
		System.out.println(count(grid2)); // 1

		List<List<Integer>> grid3 = newArrayList();
		grid3.add(asList(1, 1, 0, 0, 0));
		grid3.add(asList(1, 1, 0, 0, 0));
		grid3.add(asList(0, 0, 1, 0, 0));
		grid3.add(asList(0, 0, 0, 1, 1));
		System.out.println(count(grid3)); // 3

		List<List<Integer>> grid4 = newArrayList();
		grid4.add(asList(1, 1, 1, 0, 0, 0));
		grid4.add(asList(1, 1, 1, 1, 0, 0));
		grid4.add(asList(1, 1, 1, 0, 0, 0));
		grid4.add(asList(0, 1, 0, 0, 0, 0));
		grid4.add(asList(0, 0, 0, 0, 1, 0));
		grid4.add(asList(0, 0, 0, 0, 0, 0));
		System.out.println(count(grid4)); // 2
	}
}
