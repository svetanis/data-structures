package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 946. Validate Stack Sequences

public final class ValidStackSequences {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static boolean validateStackSequences(int[] pushed, int[] popped) {
		Deque<Integer> dq = new ArrayDeque<>();
		int index = 0;
		for (int val : pushed) {
			dq.push(val);
			while (!dq.isEmpty() && dq.peek() == popped[index]) {
				dq.pop();
				index += 1;
			}
		}
		return index == popped.length;
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
