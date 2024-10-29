package com.svetanis.datastructures.queue;

import java.util.Stack;

public final class QueueTwoStacks<T> {

	private Stack<T> in; // back of queue
	private Stack<T> out; // front of queue

	// create an empty queue
	public QueueTwoStacks() {
		this.in = new Stack<>();
		this.out = new Stack<>();
	}

	public int size() {
		return in.size() + out.size();
	}

	public void enqueue(T value) {
		in.push(value);
	}

	public T dequeue() {
		move();
		return out.pop();
	}

	public T peek() {
		move();
		return out.peek();
	}

	private void move() {
		if (out.empty()) {
			while (!in.empty()) {
				out.push(in.pop());
			}
		}
	}
}
