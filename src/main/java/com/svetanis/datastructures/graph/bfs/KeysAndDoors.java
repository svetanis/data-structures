package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Keys and Doors: the shortest walk from the start to the exit across a grid with locked doors.
//
// Input: grid -- a rectangle of text rows. '@' is the start and '+' the exit (one of each), '#' is
// water you cannot enter, '.' is open floor, a lower-case letter 'a' to 'j' is a key that you pick
// up by stepping on it, and the upper-case letter 'A' to 'J' is a door that opens only if you hold
// the key of the same letter. You walk one cell at a time, up, down, left or right. Returns the cells
// of a shortest walk from '@' to '+', as {row, column} pairs, both ends included; the array is empty
// when the exit cannot be reached. Assumes exactly one '@' (there is no check, a missing '@' fails).
//
// The one idea: standing on a cell is not enough to know where you can go next, because a door that
// is shut now may be open after a key has been picked up. So one queue entry is a cell TOGETHER with
// the set of keys in hand, written as a number whose bit k is 1 when key 'a' + k is held (10 keys
// give 1024 possible sets). The same cell with a different set of keys is a different state, and
// visited has a slot for each. Every step costs one, so breadth first search finds the fewest steps,
// and each entry remembers the entry it came from so the walk can be read back from the exit.
//
// Time: O(n * m * 2^10) -- n * m cells, each with up to 1024 key sets, and four steps from each.
// Space: O(n * m * 2^10) -- visited, and the queue.

public final class KeysAndDoors {

	private static final char SRC = '@';
	private static final char DST = '+';

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };

	public static int[][] shortestPath(String[] grid) {
		Node src = src(grid, SRC);
		List<Node> nodes = shortestPath(src, grid);
		int[][] cells = new int[nodes.size()][];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new int[] { nodes.get(i).x, nodes.get(i).y }; // RECORD: {row, column} of each step
		}
		return cells;
	}

	private static List<Node> shortestPath(Node src, String[] grid) {
		boolean[][][] visited = new boolean[grid.length][grid[0].length()][1024]; // STATE: cell and key set
		visited[src.x][src.y][src.key] = true;
		Queue<Node> queue = new LinkedList<>();
		queue.offer(src);

		while (!queue.isEmpty()) {
			Node node = queue.poll(); // TAKE
			int key = node.key;
			if (isDst(node.x, node.y, grid)) {
				return path(node); // RETURN: the first time the exit comes off is the fewest steps
			}

			for (Node n : neighbors(node, grid)) {
				if (!visited[n.x][n.y][n.key]) {
					visited[n.x][n.y][key] = true; // MARK: the cell under the keys held before this step
					queue.offer(n);
				}
			}
		}
		return new ArrayList<>(); // the exit cannot be reached
	}

	private static List<Node> path(Node node) {
		List<Node> list = new ArrayList<>();
		while (node != null) {
			list.add(0, node); // WALK back from the exit, putting each step in front
			node = node.parent;
		}
		return list;
	}

	private static List<Node> neighbors(Node node, String[] grid) {
		List<Node> list = new ArrayList<>();
		for (int i = 0; i < dx.length; i++) {
			int x = node.x + dx[i];
			int y = node.y + dy[i];
			if (!valid(x, y, grid.length, grid[0].length())) {
				continue; // SKIP: off the grid
			}
			boolean water = isWater(x, y, grid);
			boolean hasKey = hasKey(node.key, grid[x].charAt(y));
			boolean lockedDoor = isDoor(x, y, grid) && !hasKey;
			if (water || lockedDoor) {
				continue; // SKIP: water, or a door whose key is not in hand
			}
			int key = node.key;
			if (isKey(x, y, grid)) {
				key = addKey(key, grid[x].charAt(y)); // TAKE: pick up the key on this cell
			}
			list.add(new Node(x, y, key, node));
		}
		return list;
	}

	private static Node src(String[] grid, char c) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length(); y++) {
				if (grid[x].charAt(y) == c) {
					return new Node(x, y, 0, null);// no keys
				}
			}
		}
		return null;
	}

	private static boolean hasKey(int keys, char door) {
		return (keys & (1 << (door - 'A'))) > 0; // bit of this door's letter is set in the key set
	}

	private static int addKey(int keys, char key) {
		return keys | (1 << (key - 'a')); // set the bit of this key's letter
	}

	private static boolean valid(int r, int c, int n, int m) {
		return r >= 0 && r < n && c >= 0 && c < m;
	}

	private static boolean isWater(int r, int c, String[] grid) {
		return grid[r].charAt(c) == '#';
	}

	private static boolean isDoor(int r, int c, String[] grid) {
		return grid[r].charAt(c) >= 'A' && grid[r].charAt(c) <= 'J';
	}

	private static boolean isKey(int r, int c, String[] grid) {
		return grid[r].charAt(c) >= 'a' && grid[r].charAt(c) <= 'j';
	}

	private static boolean isDst(int r, int c, String[] grid) {
		return grid[r].charAt(c) == DST;
	}

	private static class Node {
		private int x;
		private int y;
		private int key;
		private Node parent;

		public Node(int x, int y, int key, Node parent) {
			this.x = x;
			this.y = y;
			this.key = key;
			this.parent = parent;
		}
	}

	public static void main(String[] args) {
		String[] a = { "...B", ".b#.", "@#+." };
		System.out.println(Arrays.deepToString(shortestPath(a))); // [[2, 0], [1, 0], [1, 1], [0, 1], [0, 2], [0, 3], [1, 3], [2, 3], [2, 2]]

		String[] b = { "@+" };
		System.out.println(Arrays.deepToString(shortestPath(b))); // [[0, 0], [0, 1]]

		String[] c = { "@B+" };
		System.out.println(Arrays.deepToString(shortestPath(c))); // []
	}
}
