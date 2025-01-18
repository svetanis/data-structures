package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.java.base.utils.Print;

// 735. Asteroid Collision

public final class AsteroidCollision735 {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] collision(int[] asteroids) {
		Deque<Integer> dq = new ArrayDeque<>();
		for (int asteroid : asteroids) {
			if (asteroid > 0) {
				dq.offerLast(asteroid);
			} else {
				while (!dq.isEmpty() && dq.peekLast() > 0 && dq.peekLast() < -asteroid) {
					dq.pollLast();
				}
				if (!dq.isEmpty() && dq.peekLast() == -asteroid) {
					dq.pollLast();
				} else if (dq.isEmpty() || dq.peekLast() < 0) {
					dq.offerLast(asteroid);
				}
			}
		}
		return dq.stream().mapToInt(Integer::valueOf).toArray();
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
