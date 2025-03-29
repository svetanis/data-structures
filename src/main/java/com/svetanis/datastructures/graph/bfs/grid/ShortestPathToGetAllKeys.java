package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 864. Shortest Path to Get All Keys

public final class ShortestPathToGetAllKeys {
	// Time & Space Complexity: O(n * m * 2^k).

	private static final char SRC = '@';
	private static final char WALL = '#';

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int minSteps(String[] grid) {
		Node src = src(grid, SRC);
		int total = totalKeys(grid);
		return shortestPath(src, total, grid);
	}

	private static int shortestPath(Node src, int total, String[] grid) {
		boolean[][][] visited = new boolean[grid.length][grid[0].length()][1 << total];
		visited[src.x][src.y][src.key] = true;
		Queue<Node> queue = new LinkedList<>();
		queue.offer(src);
		int steps = 0;
		while (!queue.isEmpty()) {
			for (int size = queue.size(); size > 0; size--) {
				Node node = queue.poll();
				int key = node.key;
				if (key == (1 << total) - 1) {
					return steps;
				}
				for (Node n : neighbors(node, grid)) {
					if (!visited[n.x][n.y][n.key]) {
						visited[n.x][n.y][key] = true;
						queue.offer(n);
					}
				}
			}
			steps++;
		}
		return -1;
	}

	private static List<Node> neighbors(Node node, String[] grid) {
		List<Node> list = new ArrayList<>();
		for (int i = 0; i < dx.length; i++) {
			int x = node.x + dx[i];
			int y = node.y + dy[i];
			if (!valid(x, y, grid.length, grid[0].length())) {
				continue;
			}
			boolean wall = isWall(x, y, grid);
			boolean hasKey = hasKey(node.key, grid[x].charAt(y));
			boolean lockedDoor = isDoor(x, y, grid) && !hasKey;
			if (wall || lockedDoor) {
				continue;
			}
			int key = node.key;
			if (isKey(x, y, grid)) {
				key = addKey(key, grid[x].charAt(y));
			}
			list.add(new Node(x, y, key));
		}
		return list;
	}

	private static int totalKeys(String[] grid) {
		int count = 0;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length(); y++) {
				char c = grid[x].charAt(y);
				if (Character.isLowerCase(c)) {
					count++;
				}
			}
		}
		return count;
	}

	private static Node src(String[] grid, char c) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length(); y++) {
				if (grid[x].charAt(y) == c) {
					return new Node(x, y, 0);// no keys
				}
			}
		}
		return null;
	}

	private static boolean hasKey(int keys, char door) {
		return (keys & (1 << (door - 'A'))) > 0;
	}

	private static int addKey(int keys, char key) {
		return keys | (1 << (key - 'a'));
	}

	private static boolean valid(int r, int c, int n, int m) {
		return r >= 0 && r < n && c >= 0 && c < m;
	}

	private static boolean isWall(int r, int c, String[] grid) {
		return grid[r].charAt(c) == WALL;
	}

	private static boolean isDoor(int r, int c, String[] grid) {
		return grid[r].charAt(c) >= 'A' && grid[r].charAt(c) <= 'F';
	}

	private static boolean isKey(int r, int c, String[] grid) {
		return grid[r].charAt(c) >= 'a' && grid[r].charAt(c) <= 'f';
	}

	private static class Node {
		private int x;
		private int y;
		private int key;

		public Node(int x, int y, int key) {
			this.x = x;
			this.y = y;
			this.key = key;
		}
	}

	public static void main(String[] args) {
		String[] a1 = { "@.a..", "###.#", "b.A.B" };
		System.out.println(minSteps(a1)); // 8
		String[] a2 = { "@..aA", "..B#.", "....b" };
		System.out.println(minSteps(a2)); // 6
		String[] a3 = { "@Aa" };
		System.out.println(minSteps(a3)); // -1
	}
}
