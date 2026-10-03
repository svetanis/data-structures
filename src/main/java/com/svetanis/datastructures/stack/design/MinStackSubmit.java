package com.svetanis.datastructures.stack.design;

import java.util.ArrayDeque;
import java.util.Deque;

// 155. Min Stack

// aux[i] holds the minimum of everything at depth <= i, so pop needs NO work: the
// entry underneath already recorded the answer, back when it was pushed and the
// popped element did not exist yet.
//
// PUSHING UNCONDITIONALLY IS THE POINT. MinStackAux pushes only on a new minimum
// and therefore needs >= on push and == on pop -- two comparisons, and the >= is a
// real bug when written as >. Keeping one aux entry per push makes the two stacks
// the same height by construction, so there is nothing to compare and the
// duplicates question stops existing rather than being got right.
//
// This file is self-contained on purpose: it is the version pasted into LeetCode,
// so it must not import anything from java-base.

public final class MinStackSubmit {
	// Time Complexity: O(1)
	// Space Complexity: O(n) -- two stacks, one entry each per push

	private static final String UNDERFLOW = "stack underflow";

	private Deque<Integer> stack;
	private Deque<Integer> aux;

	public MinStackSubmit() {
		this.stack = new ArrayDeque<>();
		this.aux = new ArrayDeque<>();
		// A fake bottom, so push never has to ask whether aux is empty. It works
		// because no int can exceed MAX_VALUE -- but MAX_VALUE is itself a legal
		// value to push, so it must never be allowed to escape as an ANSWER. That
		// is what the guards in peek() and min() are for: without them min() on an
		// empty stack returns 2147483647, indistinguishable from a real
		// push(Integer.MAX_VALUE), and LC 155 never catches it because the problem
		// promises min() is only called on a non-empty stack.
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
		aux.push(Math.min(x, aux.peek())); // no branch: duplicates handle themselves
	}

	public void pop() {
		if (stack.isEmpty()) {
			throw new IllegalStateException(UNDERFLOW);
		}
		stack.pop();
		aux.pop(); // always in lockstep, so no comparison either
	}

	public int peek() {
		if (stack.isEmpty()) {
			throw new IllegalStateException(UNDERFLOW);
		}
		return stack.peek();
	}

	public int min() {
		if (stack.isEmpty()) {
			throw new IllegalStateException(UNDERFLOW); // never return the fake bottom
		}
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
