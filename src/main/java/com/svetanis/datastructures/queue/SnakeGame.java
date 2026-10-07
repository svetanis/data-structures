package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

// 353. Design Snake Game
//
// The board is height rows by width columns; food lists [row, col] cells, eaten in the given
// order. move("U" / "D" / "L" / "R") returns the score so far (food eaten), or -1 when the
// head leaves the board or runs into the snake's body.
//
// The deque holds the snake's cells, head at the front and tail at the back, each stored as one
// int: row * width + col. The set holds the same cells, so a collision check is one look-up.
// Each move adds a new head; unless the new head is on the next food, the tail leaves first,
// so the head may step into the cell the tail has just left.

public final class SnakeGame {
	// Time Complexity: move O(1), a fixed number of deque and hash-set operations
	// Space Complexity: O(f) for f food items, the snake grows by one cell per food eaten

	private int height;
	private int width;
	private int index;
	private int score;
	private int[][] food;
	private Set<Integer> visited = new HashSet<>();
	private Deque<Integer> dq = new ArrayDeque<>();

	public SnakeGame(int width, int height, int[][] food) {
		this.height = height;
		this.width = width;
		this.food = food;
		this.index = 0;
		this.dq.offer(0);
		this.visited.add(0);
	}

	public int move(String direction) {
		int head = dq.peekFirst();
		int[] coordinates = next(head, direction);
		int x = coordinates[0];
		int y = coordinates[1];
		if (!safe(x, y)) {
			return -1;
		}
		if (index < food.length && food[index][0] == x && food[index][1] == y) {
			score++;
			index++;
		} else {
			int tail = dq.pollLast(); // no food: the tail moves first, freeing its cell
			visited.remove(tail);
		}
		int newHead = x * width + y;
		if (visited.contains(newHead)) { // runs into its own body
			return -1;
		}
		dq.offerFirst(newHead);
		visited.add(newHead);
		return score;
	}

	private boolean safe(int x, int y) {
		return x >= 0 && x < height && y >= 0 && y < width;
	}

	private int[] next(int head, String direction) {
		int row = head / width;
		int col = head % width;
		int x = row;
		int y = col;
		switch (direction) {
		case "U":
			x--;
			break;
		case "D":
			x++;
			break;
		case "L":
			y--;
			break;
		case "R":
			y++;
			break;
		}
		return new int[] { x, y };
	}

	public static void main(String[] args) {
		int[][] food = { { 1, 2 }, { 2, 2 } };
		SnakeGame sg = new SnakeGame(3, 3, food);
		System.out.println(sg.move("R")); // 0
		System.out.println(sg.move("R")); // 0
		System.out.println(sg.move("D")); // 1, the food at (1, 2)
		System.out.println(sg.move("D")); // 2
		System.out.println(sg.move("L")); // 2
	}
}