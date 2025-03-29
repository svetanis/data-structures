package com.svetanis.datastructures.graph.bfs.multisource;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Math.min;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Coordinate;

// 296. Best Meeting Point
// given an n x m 2D binary grid 
// where each cell in the grid can
// either contain 1 (which represents
// the home of a fried) or a 0 (which
// signifies an empty space). the goal
// is to find the minimal total travel
// distance to a single meeting point.

// the total travel distance is defined
// as the sum of all distances from the
// homes of each friend (cell with 1) to
// a meeting point. when calculating 
// distances, we use the Manhattan Distance
// which means the distance between two 
// points is the sum of the absolute 
// differences of their coordinates. 
// for two points p1 and p2, the 
// Manhattan Distance is |p2.x - p1.x| + |p2.y - p1.y|

// the challenge of the problem lies in finding
// the optimal meeting point that minimizes
// the total travel distance for all friends.

public final class BestMeetingPointBfs {
	// Time complexity: O(r * c)
	// Space Complexity: O(r + c)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int bmp(List<List<Integer>> grid) {
		int min = Integer.MAX_VALUE;
		int n = grid.size();
		int m = grid.get(0).size();
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				Coordinate src = new Coordinate(r, c);
				min = min(min, bfs(src, grid));
			}
		}
		return min;
	}

	private static int bfs(Coordinate src, List<List<Integer>> grid) {
		Set<Coordinate> set = newHashSet();
		Queue<Coordinate> queue = new ArrayDeque<>();
		Map<Coordinate, Integer> map = newHashMap();
		queue.add(src);
		map.put(src, 0);
		int total = 0;
		while (!queue.isEmpty()) {
			Coordinate curr = queue.poll();
			int dist = map.get(curr);
			if (grid.get(curr.getRow()).get(curr.getCol()) == 1) {
				total += dist;
			}
			List<Coordinate> neighbors = neighbors(curr, grid);
			for (Coordinate neighbor : neighbors) {
				if (!set.contains(neighbor)) {
					queue.add(neighbor);
					map.put(neighbor, dist + 1);
				}
				set.add(neighbor);
			}
		}
		return total;
	}

	private static List<Coordinate> neighbors(Coordinate src, List<List<Integer>> grid) {
		List<Coordinate> list = new ArrayList<>();
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int dist = 0; dist < dx.length; ++dist) {
			int x = src.getRow() + dx[dist];
			int y = src.getCol() + dy[dist];
			if (valid(grid, x, y)) {
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
		grid1.add(asList(1, 0, 0, 0, 1));
		grid1.add(asList(0, 0, 0, 0, 0));
		grid1.add(asList(0, 0, 1, 0, 0));
		System.out.println(bmp(grid1)); // 6 point (0,2)
	}
}
