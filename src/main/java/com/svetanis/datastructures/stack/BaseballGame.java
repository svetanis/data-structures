package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 682. Baseball Game

public final class BaseballGame {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int game(String[] operations) {
		Deque<Integer> dq = new ArrayDeque<>();
		for (String operation : operations) {
			switch (operation) {
			case "+":
				int top = dq.pop();
				int prev = dq.peek();
				dq.push(top);
				dq.push(top + prev);
				break;
			case "D":
				dq.push(dq.peek() * 2);
				break;
			case "C":
				dq.pop();
				break;
			default:
				dq.push(Integer.parseInt(operation));
				break;
			}
		}
		int sum = 0;
		while (!dq.isEmpty()) {
			sum += dq.pop();
		}
		return sum;
	}

	public static void main(String[] args) {
		String[] a1 = { "5", "2", "C", "D", "+" };
		System.out.println(game(a1)); // 30
		String[] a2 = { "5", "-2", "4", "C", "D", "9", "+", "+" };
		System.out.println(game(a2)); // 27
		String[] a3 = { "1", "C" };
		System.out.println(game(a3)); // 0
	}
}
