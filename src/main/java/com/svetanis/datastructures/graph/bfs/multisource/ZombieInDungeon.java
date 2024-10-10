package com.svetanis.datastructures.graph.bfs.multisource;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.Coordinate;

// given an m by n grid of integers
// representing a map of a dungeon.
// in this map:
// -1 represents a wall or an obstacle of some kind
// 0 represents a gate out of the dungeon
// INF represents empty space

// venturing into the dungeon is very dangerous,
// so you would like to know how close you are
// at each point in the dungeon to the closest exit.
// given the map of the dungeon, return the same map,
// but for each empty space, that space is replaced
// by the number of steps it takes to reach the 
// closest exit. if a space can't reach the exit, that
// space remains INF.

// note that each step, you can move horizontally or 
// vertically, but you can't move pass a wall or an 
// obstacle.

public final class ZombieInDungeon {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static int INF = MAX_VALUE;
	private static final int[] dx = { 0, 1, 0, -1 };
	private static final int[] dy = { 1, 0, -1, 0 };

	public static List<List<Integer>> gateDists(List<List<Integer>> grid) {
		Queue<Coordinate> queue = init(grid);
		bfs(queue, grid);
		return grid;
	}

	private static Queue<Coordinate> init(List<List<Integer>> grid) {
		int n = grid.size();
		int m = grid.get(0).size();
		Queue<Coordinate> queue = new ArrayDeque<>();
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (grid.get(r).get(c) == 0) {
					queue.add(new Coordinate(r, c));
				}
			}
		}
		return queue;
	}

	private static void bfs(Queue<Coordinate> queue, List<List<Integer>> grid) {
		while (!queue.isEmpty()) {
			Coordinate node = queue.poll();
			int val = grid.get(node.getRow()).get(node.getCol());
			List<Coordinate> neighbors = neighbors(node, grid);
			for (Coordinate neighbor : neighbors) {
				int row = neighbor.getRow();
				int col = neighbor.getCol();
				if (grid.get(row).get(col) == INF) {
					grid.get(row).set(col, 1 + val);
					queue.add(neighbor);
				}
			}
		}
	}

	private static List<Coordinate> neighbors(Coordinate node, List<List<Integer>> grid) {
		List<Coordinate> list = new ArrayList<>();
		for (int dist = 0; dist < dx.length; dist++) {
			int x = node.getRow() + dx[dist];
			int y = node.getCol() + dy[dist];
			if (valid(grid, x, y)) {
				list.add(new Coordinate(x, y));
			}
		}
		return list;
	}

	private static boolean valid(List<List<Integer>> grid, int row, int col) {
		boolean isRow = row >= 0 && row < grid.size();
		boolean isCol = col >= 0 && col < grid.get(0).size();
		return isRow && isCol && grid.get(row).get(col) != -1;
	}

	public static void main(String[] args) {
		List<List<Integer>> grid = newArrayList();
		grid.add(asList(INF, -1, 0, INF));
		grid.add(asList(INF, INF, INF, -1));
		grid.add(asList(INF, -1, INF, -1));
		grid.add(asList(0, -1, INF, INF));
		print(gateDists(grid));
	}
}
