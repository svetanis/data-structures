package com.svetanis.datastructures.stack.impl;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.ArrayDeque;
import java.util.Deque;

// 225. Implement Stack using Queues
//
// You must use only standard operations of a queue,
// which means that only push to back, peek/pop from front,
// size and is empty operations are valid.
//
// push adds a value; pop and top return the most recently pushed value still held.
//
// 'main' holds the values in push order, oldest at the front, so push only adds at the back.
// pop pays for that: the newest value is at the back, so every value in front of it is moved
// to 'aux', the newest is taken, and the two queues swap names. top makes the same moves but
// passes the newest value across as well, since it stays in the stack.

public final class StackFromTwoQueuesPop {
	// Time Complexity: push, isEmpty O(1); pop, top O(n), every other value moves to aux
	// Space Complexity: O(n), each value sits in one of the two queues

	public StackFromTwoQueuesPop() {
		this.main = new ArrayDeque<>();
		this.aux = new ArrayDeque<>();
	}

	private Deque<Integer> main;
	private Deque<Integer> aux;

	public boolean isEmpty() {
		return main.isEmpty();
	}

	public void push(int value) {
		main.offer(value);
	}

	public int pop() {
		moveAllButLast();
		int result = main.poll(); // the last one in is the last one left
		swap();
		return result;
	}

	public int top() {
		moveAllButLast();
		int result = main.peek();
		aux.offer(main.poll()); // top stays in the stack: it goes across too
		swap();
		return result;
	}

	private void moveAllButLast() {
		if (main.isEmpty()) {
			throw illegalState("stack underflow");
		}
		while (main.size() != 1) { // leave only the newest value in main
			aux.offer(main.poll());
		}
	}

	private void swap() {
		Deque<Integer> temp = main;
		main = aux;
		aux = temp;
	}

	public static void main(String[] args) {
		StackFromTwoQueuesPop stack = new StackFromTwoQueuesPop();
		stack.push(24);
		stack.push(34);
		stack.push(4);
		stack.push(10);
		stack.push(1);
		stack.push(43);
		stack.push(21);
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
	}
}
