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
// 'main' is kept in stack order, newest value at the front. push pays for that: the new value
// goes into the empty 'aux' first, every value from 'main' is queued behind it in its old
// order, and the two queues swap names. pop and top then only read the front of 'main'.
//
// Siblings, a stack built from another structure:
//   StackFromTwoQueuesPop  pop pays instead: push O(1), pop and top O(n)
//   StackFromSingleQueue   the same push-pays order with one queue, rotated in place
//   StackFromHeap          a heap ranked by push order, push and pop O(log n)

public final class StackFromTwoQueuesPush {
	// Time Complexity: push O(n), every value held moves to aux; pop, top, isEmpty O(1)
	// Space Complexity: O(n), each value sits in one of the two queues

	public StackFromTwoQueuesPush() {
		this.main = new ArrayDeque<>();
		this.aux = new ArrayDeque<>();
	}

	private Deque<Integer> main; // ORDER: stack order, newest at the front
	private Deque<Integer> aux; // EMPTY: between calls, the queue to rebuild into

	public boolean isEmpty() {
		return main.isEmpty();
	}

	public void push(int value) {
		// add element to aux queue
		aux.offer(value); // FRONT: first into the empty aux
		// move all elements from
		// main queue to aux queue
		// behind the new value, in their old order
		move();
		// swap the main and aux queues
		swap();
	}

	private void move() {
		while (!main.isEmpty()) {
			aux.offer(main.poll()); // BEHIND: an older value queues after the new one
		}
	}

	private void swap() {
		Deque<Integer> temp = main;
		main = aux;
		aux = temp;
	}

	public int pop() {
		if (main.isEmpty()) {
			throw new IllegalStateException("stack underflow");
		} else {
			return main.poll(); // POP: the front is the newest
		}
	}

	public int top() {
		if (main.isEmpty()) {
			throw new IllegalStateException("stack underflow");
		} else {
			return main.peek(); // TOP: read the front
		}
	}

	public static void main(String[] args) {
		StackFromTwoQueuesPush stack = new StackFromTwoQueuesPush();
		stack.push(24);
		stack.push(34);
		stack.push(4);
		stack.push(10);
		stack.push(1);
		stack.push(43);
		stack.push(21);
		System.out.println(stack.pop()); // 21
		System.out.println(stack.pop()); // 43
		System.out.println(stack.pop()); // 1
	}
}
