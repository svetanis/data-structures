package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 155. Min Stack

public final class MinStackSubmit {
	// Time Complexity: O(1)
	// Space Complexity: O(1)

	private Deque<Integer> stack;
	private Deque<Integer> aux;

	public MinStackSubmit() {
		this.stack = new ArrayDeque<>();
		this.aux = new ArrayDeque<>();
		aux.push(Integer.MAX_VALUE);
	}

	public int size() {
		return stack.size();
	}

	public boolean empty() {
		return stack.isEmpty();
	}

	public void push(int x) {
		stack.push(x);
		aux.push(Math.min(x, aux.peek()));
	}

	public void pop() {
		stack.pop();
		aux.pop();
	}

	public int peek() {
		return stack.peek();
	}

	public int min() {
		return aux.peek();
	}

	public static void main(String[] args) {
		MinStackSubmit stack = new MinStackSubmit();
		stack.push(-2);
		stack.push(0);
		stack.push(-3);
		System.out.println(stack.min()); // -3
		stack.pop();
		System.out.println(stack.peek()); // 0
		System.out.println(stack.min()); // -2
	}
}
