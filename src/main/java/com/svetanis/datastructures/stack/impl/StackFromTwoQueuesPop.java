package com.svetanis.datastructures.stack.impl;

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
//
// Siblings, a stack built from another structure:
//   StackFromTwoQueuesPush  push pays instead: push O(n), pop and top O(1)
//   StackFromSingleQueue    push pays, rotating one queue in place
//   StackFromHeap           a heap ranked by push order, push and pop O(log n)

public final class StackFromTwoQueuesPop {
	// Time Complexity: push, isEmpty O(1); pop, top O(n), every other value moves to aux
	// Space Complexity: O(n), each value sits in one of the two queues

	public StackFromTwoQueuesPop() {
		this.main = new ArrayDeque<>();
		this.aux = new ArrayDeque<>();
	}

	private Deque<Integer> main; // ORDER: push order, oldest at the front
	private Deque<Integer> aux; // EMPTY: between calls, the queue to move into

	public boolean isEmpty() {
		return main.isEmpty();
	}

	public void push(int value) {
		main.offer(value); // JOIN: at the back, nothing else moves
	}

	public int pop() {
		moveAllButLast();
		int result = main.poll(); // TAKE: the last one in is the last one left
		swap(); // SWAP: aux now holds the rest, in order
		return result;
	}

	public int top() {
		moveAllButLast();
		int result = main.peek(); // READ: the newest value
		aux.offer(main.poll()); // KEEP: top stays in the stack: it goes across too
		swap(); // SWAP: aux now holds everything, in order
		return result;
	}

	private void moveAllButLast() {
		if (main.isEmpty()) {
			throw new IllegalStateException("stack underflow");
		}
		while (main.size() != 1) { // MOVE: leave only the newest value in main
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
