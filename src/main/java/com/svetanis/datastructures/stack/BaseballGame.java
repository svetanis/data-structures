package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 682. Baseball Game
//
// Input: a list of operations. An integer records that score, "+" records the sum of the last
// two scores, "D" records double the last score, and "C" removes the last score. Returns the
// sum of the scores still recorded at the end.
//
// Every operation looks only at the most recent scores, so the stack holds the recorded scores
// with the most recent on top. "+" needs the second one down as well: it pops the top, reads
// the one beneath, and pushes the top back before pushing the sum.

public final class BaseballGame {
	// Time Complexity: O(n), one push or pop per operation, then one pass to add up
	// Space Complexity: O(n) for the stack

	public static int game(String[] operations) {
		Deque<Integer> dq = new ArrayDeque<>();
		for (String operation : operations) {
			switch (operation) {
			case "+":
				int top = dq.pop();
				int prev = dq.peek();
				dq.push(top); // put back: "+" does not remove the last score
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
