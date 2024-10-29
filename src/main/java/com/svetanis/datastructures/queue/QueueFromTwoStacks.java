package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 232. Implement Queue using Stacks

// You must use only standard operations of a stack, 
// which means only push to top, peek/pop from top, 
// size, and is empty operations are valid.

public final class QueueFromTwoStacks {

	public QueueFromTwoStacks() {
		this.main = new ArrayDeque<>();
		this.aux = new ArrayDeque<>();
	}

	private Deque<Integer> main;
	private Deque<Integer> aux;

	public boolean isEmpty() {
		return main.isEmpty() && aux.isEmpty();
	}

	public void push(int value) {
		main.push(value);
	}

	public int pop() {
		move();
		return aux.pop();
	}

	public int top() {
		move();
		return aux.peek();
	}

	private void move() {
		if (aux.isEmpty()) {
			while (!main.isEmpty()) {
				aux.push(main.pop());
			}
		}
	}

	public static void main(String[] args) {
		QueueFromTwoStacks stack = new QueueFromTwoStacks();
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
