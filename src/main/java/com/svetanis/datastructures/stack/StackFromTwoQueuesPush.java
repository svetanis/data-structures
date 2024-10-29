package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.ArrayDeque;
import java.util.Deque;

// 225. Implement Stack using Queue

// You must use only standard operations of a queue, 
// which means that only push to back, peek/pop from front, 
// size and is empty operations are valid.

public final class StackFromTwoQueuesPush {

	public StackFromTwoQueuesPush() {
		this.main = new ArrayDeque<>();
		this.aux = new ArrayDeque<>();
	}

	private Deque<Integer> main;
	private Deque<Integer> aux;

	public boolean isEmpty() {
		return main.isEmpty();
	}

	public void push(int value) {
		// add element to aux queue
		aux.offer(value);
		// move all elements from
		// main queue to aux queue
		// reversing the order
		move();
		// swap the main and aux queues
		swap();
	}

	private void move() {
		while (!main.isEmpty()) {
			aux.offer(main.poll());
		}
	}

	private void swap() {
		Deque<Integer> temp = main;
		main = aux;
		aux = temp;
	}

	public int pop() {
		if (main.isEmpty()) {
			throw illegalState("stack underflow");
		} else {
			return main.poll();
		}
	}

	public int top() {
		if (main.isEmpty()) {
			throw illegalState("stack underflow");
		} else {
			return main.peek();
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
