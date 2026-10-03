package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.java.base.utils.Print;

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
		for (int asteroid : asteroids) {
			if (asteroid > 0) {
				dq.push(asteroid);
			} else {
				while (!dq.isEmpty() && dq.peek() > 0 && dq.peek() < -asteroid) {
					dq.pop(); // a smaller right-mover is destroyed
				}
				if (!dq.isEmpty() && dq.peek() == -asteroid) {
					dq.pop(); // equal sizes: both are destroyed
				} else if (dq.isEmpty() || dq.peek() < 0) {
					dq.push(asteroid); // nothing moving right is left to hit it
				}
			}
		}
		return leftToRight(dq);
	}

	// the top of the stack is the rightmost survivor, so the array is filled from its end
	private static int[] leftToRight(Deque<Integer> dq) {
		int[] result = new int[dq.size()];
		for (int i = result.length - 1; i >= 0; i--) {
			result[i] = dq.pop();
		}
		return result;
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 10, -5 };
		Print.print(collision(a1)); // [5,10]

		int[] a2 = { 8, -8 };
		Print.print(collision(a2)); // []

		int[] a3 = { 10, 2, -5 };
		Print.print(collision(a3)); // [10]
	}
}
