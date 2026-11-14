package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 864. Shortest Path to Get All Keys
//
// Input: a grid of strings. '.' is an empty cell, '#' a wall, '@' the start, a lowercase letter
// a key and the same uppercase letter its lock. You may walk up, down, left or right, but not
// through a wall, and not through a lock unless you already hold its key; stepping on a key
// picks it up. On LeetCode: 1 <= rows, cols <= 30, exactly one '@', between 1 and 6 keys named
// 'a', 'b', ... in order, each with exactly one lock.
// Returns the fewest steps that collect every key, or -1 if that is impossible.
//
// The one idea: standing on the same cell with a different set of keys in your pocket is a
// different situation (a door that was shut may now be open), so one queue entry is a Node
// {x, y, key}: a cell AND the set of keys held, packed into the bits of one int (bit 0 is key
// 'a', bit 1 is 'b', ...). There are at most rows * cols * 2^6 such situations. Taking the queue
// out one batch at a time counts the steps. The first Node whose key set holds all the keys
// (all total bits set) gives the answer.
//
// Time: O(n * m * 2^k) -- k keys; each (cell, key set) is expanded once with 4 neighbours.
// Space: O(n * m * 2^k) -- the visited table and the queue.
//
// Siblings: none -- this is the only file for this problem.

public final class ShortestPathToGetAllKeys {

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
		visited[src.x][src.y][src.key] = true; // RECORD: the start, holding no keys
		Queue<Node> queue = new LinkedList<>();
		queue.offer(src);
		int steps = 0;
		while (!queue.isEmpty()) {
			for (int size = queue.size(); size > 0; size--) { // LEVEL: only the Nodes of this batch
				Node node = queue.poll(); // TAKE
				int key = node.key;
				if (key == (1 << total) - 1) {
					return steps; // RETURN: every key held -- all total bits set
				}
				for (Node n : neighbors(node, grid)) {
					if (!visited[n.x][n.y][n.key]) {
						// RECORD: marks the neighbour's cell under the key set held BEFORE this step;
						// a step onto a key cell is therefore queued once per neighbour that reaches
						// it, which costs repeated work but does not change the answer
						visited[n.x][n.y][key] = true;
						queue.offer(n);
					}
				}
			}
			steps++; // COUNT: the next batch is one step further
		}
		return -1; // RETURN: some key can never be collected
	}

	private static List<Node> neighbors(Node node, String[] grid) {
		List<Node> list = new ArrayList<>();
		for (int i = 0; i < dx.length; i++) {
			int x = node.x + dx[i];
			int y = node.y + dy[i];
			if (!valid(x, y, grid.length, grid[0].length())) {
				continue; // SKIP: off the grid
			}
			boolean wall = isWall(x, y, grid);
			boolean hasKey = hasKey(node.key, grid[x].charAt(y));
			boolean lockedDoor = isDoor(x, y, grid) && !hasKey;
			if (wall || lockedDoor) {
				continue; // SKIP: a wall, or a lock whose key is not held
			}
			int key = node.key;
			if (isKey(x, y, grid)) {
				key = addKey(key, grid[x].charAt(y)); // TAKE: pick the key up
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
					count++; // COUNT: a key
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
		return (keys & (1 << (door - 'A'))) > 0; // BITS: is the key for this lock in the set?
	}

	private static int addKey(int keys, char key) {
		return keys | (1 << (key - 'a')); // BITS: set union with this key
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
