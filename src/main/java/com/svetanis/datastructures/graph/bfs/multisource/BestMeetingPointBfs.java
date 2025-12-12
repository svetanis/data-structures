package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

// 296. Best Meeting Point

public final class BestMeetingPointBfs {
	// Time complexity: O(r * c)
	// Space Complexity: O(r + c)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private int[][] grid;

	public int bmp(int[][] grid) {
		int min = Integer.MAX_VALUE;
		this.rows = grid.length;
		this.cols = grid[0].length;
		this.grid = grid;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				min = Math.min(min, bfs(new int[] { r, c }));
			}
		}
		return min;
	}

	private int bfs(int[] src) {
		String key = src[0] + "_" + src[1];
		Set<String> set = new HashSet<>();
		Deque<int[]> queue = new ArrayDeque<>();
		Map<String, Integer> map = new HashMap<>();
		queue.add(src);
		map.put(key, 0);
		set.add(key);
		int total = 0;
		while (!queue.isEmpty()) {
			int[] curr = queue.poll();
			int x = curr[0], y = curr[1];
			String currKey = x + "_" + y;
			int dist = map.get(currKey);
			if (grid[x][y] == 1) {
				total += dist;
			}
			List<int[]> neighbors = neighbors(curr);
			for (int[] next : neighbors) {
				String nextKey = next[0] + "_" + next[1];
				if (!set.contains(nextKey)) {
					queue.add(next);
					map.put(nextKey, dist + 1);
				}
				set.add(nextKey);
			}
		}
		return total;
	}

	private List<int[]> neighbors(int[] src) {
		List<int[]> list = new ArrayList<>();
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int dist = 0; dist < dx.length; ++dist) {
			int x = src[0] + dx[dist];
			int y = src[1] + dy[dist];
			if (valid(x, y)) {
				list.add(new int[] { x, y });
			}
		}
		return list;
	}

	private boolean valid(int row, int col) {
		boolean one = row >= 0 && row < rows; // row number is in range
		boolean two = col >= 0 && col < cols; // col number is in range
		return one && two;
	}

	public static void main(String[] args) {
		BestMeetingPointBfs bmp = new BestMeetingPointBfs();
		int[][] grid = { { 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0 } };
		System.out.println(bmp.bmp(grid)); // 6 point (0,2)

		int[][] grid1 = { { 1, 1 } };
		System.out.println(bmp.bmp(grid1)); // 1
	}
}
