package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

// 353. Design Snake Game
//
// Input: SnakeGame(width, height, food) -- a board of height rows by width columns, the snake one
// cell long at row 0, column 0. food lists [row, col] cells; one food item shows at a time and
// the next appears once it is eaten. move("U" / "D" / "L" / "R") moves the head one cell and
// returns the score so far (food eaten), or -1 when the head leaves the board or runs into the
// snake's body. The cell the tail leaves in the same move is free. On LeetCode:
// 1 <= width, height <= 10^4, 1 <= food.length <= 50, 0 <= row < height, 0 <= col < width, at
// most 10^4 calls to move, and food never appears on a cell the snake covers.
//
// The one idea: one deque entry is one cell of the snake, stored as the single number
// row * width + col, head at the front and tail at the back. The set holds the same numbers, so
// "is this cell part of the snake" is one look-up instead of a walk along the deque. A move adds
// the new head at the front. If the new head is not on the next food, the snake does not grow, so
// the tail leaves from the back first: that frees its cell before the collision check, which is
// why the head may step into the cell the tail has just left.
//
// Time: move O(1) -- a fixed number of deque and hash-set operations
// Space: O(f) -- the snake grows by one cell per food item eaten, so it never holds more than
//   f + 1 cells

public final class SnakeGame {
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
		int[] coordinates = next(head, direction); // STEP: where the head would land
		int x = coordinates[0];
		int y = coordinates[1];
		if (!safe(x, y)) { // WALL: off the board
			return -1;
		}
		if (index < food.length && food[index][0] == x && food[index][1] == y) {
			score++; // EAT: the snake grows, the tail stays
			index++;
		} else {
			int tail = dq.pollLast(); // no food: the tail moves first, freeing its cell
			visited.remove(tail);
		}
		int newHead = x * width + y;
		if (visited.contains(newHead)) { // runs into its own body
			return -1;
		}
		dq.offerFirst(newHead); // RECORD: the new head, in the deque and in the set
		visited.add(newHead);
		return score; // RETURN: food eaten so far
	}

	private boolean safe(int x, int y) {
		return x >= 0 && x < height && y >= 0 && y < width; // RETURN: inside the board
	}

	private int[] next(int head, String direction) {
		int row = head / width; // DECODE: the cell number back into row and column
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
		return new int[] { x, y }; // RETURN: the next cell, possibly off the board
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