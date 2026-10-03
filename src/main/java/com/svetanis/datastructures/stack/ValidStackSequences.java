package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 946. Validate Stack Sequences
//
// Input: pushed and popped, two orderings of the same distinct values. Returns true when
// pushing the values in the order of pushed, and popping at well-chosen moments, can pop them
// in the order of popped.
//
// Replay the pushes on a real stack; index is the next value of popped still to be matched.
// After each push, pop while the top is that value. Waiting can never help: any later push
// would bury the value, and it is the one popped must see next.

public final class ValidStackSequences {
	// Time Complexity: O(n), every value is pushed once and popped at most once
	// Space Complexity: O(n) for the stack

	public static boolean validateStackSequences(int[] pushed, int[] popped) {
		Deque<Integer> dq = new ArrayDeque<>();
		int index = 0;
		for (int val : pushed) {
			dq.push(val);
			while (!dq.isEmpty() && dq.peek() == popped[index]) { // pop now, before it is buried
				dq.pop();
				index += 1;
			}
		}
		return index == popped.length; // every value of popped was matched
	}

	public static void main(String[] args) {
		int[] pushed = { 1, 2, 3, 4, 5 };
		int[] popped = { 4, 5, 3, 2, 1 };
		System.out.println(validateStackSequences(pushed, popped)); // true

		int[] pushed1 = { 1, 2, 3, 4, 5 };
		int[] popped1 = { 4, 3, 5, 1, 2 };
		System.out.println(validateStackSequences(pushed1, popped1)); // false
	}
}
