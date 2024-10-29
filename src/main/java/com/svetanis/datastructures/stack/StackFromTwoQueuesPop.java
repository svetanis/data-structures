package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 225. Implement Stack using Queue

// You must use only standard operations of a queue, 
// which means that only push to back, peek/pop from front, 
// size and is empty operations are valid.

public final class StackFromTwoQueuesPop {

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
		while (main.size() != 1) {
			aux.offer(main.poll());
		}
		int result = main.poll();
		// swap q1 and q2
		swap();
		return result;
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
