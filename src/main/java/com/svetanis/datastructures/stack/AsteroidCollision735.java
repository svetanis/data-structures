package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// 735. Asteroid Collision
//
// Each number is an asteroid in a row: its size is the absolute value, and the sign is its
// direction, positive moving right and negative moving left. Returns the asteroids still there
// after every collision, in their left-to-right order.
//
// The stack holds the survivors so far, left to right, the rightmost on top. Only a left-mover
// can hit anything, and the first thing it meets is the top of the stack: it destroys smaller
// right-movers there one by one, is destroyed by a bigger one, and both go if the sizes are
// equal. With a left-mover or nothing on top, it survives.

public final class AsteroidCollision735 {
	// Time Complexity: O(n), every asteroid is pushed once and popped at most once
	// Space Complexity: O(n) for the stack

	public static int[] collision(int[] asteroids) {
		Deque<Integer> dq = new ArrayDeque<>();
		for (int asteroid : asteroids) { // WALK: left to right, one asteroid at a time
			if (asteroid > 0) {
				dq.push(asteroid); // JOIN: a right-mover cannot hit anything already on the stack
			} else {
				while (!dq.isEmpty() && dq.peek() > 0 && dq.peek() < -asteroid) { // FIGHT: a smaller right-mover on top
					dq.pop(); // FIGHT: a smaller right-mover is destroyed
				}
				if (!dq.isEmpty() && dq.peek() == -asteroid) {
					dq.pop(); // EQUAL: equal sizes: both are destroyed
				} else if (dq.isEmpty() || dq.peek() < 0) {
					dq.push(asteroid); // JOIN: nothing moving right is left to hit it
				}
			}
		}
		return leftToRight(dq); // READ: the survivors, left to right
	}

	// the top of the stack is the rightmost survivor, so the array is filled from its end
	private static int[] leftToRight(Deque<Integer> dq) {
		int[] result = new int[dq.size()];
		for (int i = result.length - 1; i >= 0; i--) {
			result[i] = dq.pop(); // READ: the top goes to the rightmost free slot
		}
		return result;
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 10, -5 };
		System.out.println(Arrays.toString(collision(a1))); // [5,10]

		int[] a2 = { 8, -8 };
		System.out.println(Arrays.toString(collision(a2))); // []

		int[] a3 = { 10, 2, -5 };
		System.out.println(Arrays.toString(collision(a3))); // [10]
	}
}
