package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// A first-in-first-out queue of any type, built from two stacks.
// QueueFromTwoStacks is the same design with LC 232's int methods.
//
// enqueue adds an item at the back; dequeue removes and returns the front item; peek returns
// the front item without removing it.
//
// A stack gives back its newest item first. Pouring all of in onto out reverses the order again,
// so out gives back the oldest first. Pour only when out is empty: every item in out is older
// than every item in in, so out must be used up first.

public final class QueueTwoStacks<T> {
	// Time Complexity: enqueue, dequeue, peek amortized O(1): each item is moved from in to out
	//   at most once; size: O(1)
	// Space Complexity: O(n), each item sits in exactly one of the two stacks

	private Deque<T> in; // back of queue: new arrivals, newest on top
	private Deque<T> out; // front of queue: oldest on top

	// create an empty queue
	public QueueTwoStacks() {
		this.in = new ArrayDeque<>();
		this.out = new ArrayDeque<>();
	}

	public int size() {
		return in.size() + out.size();
	}

	public void enqueue(T value) {
		in.push(value); // ArrayDeque refuses null: enqueue(null) throws NullPointerException
	}

	public T dequeue() {
		move();
		return out.pop(); // NoSuchElementException if the queue is empty
	}

	public T peek() {
		move();
		return out.peek(); // null if the queue is empty
	}

	// pour only when out is EMPTY: pouring onto older elements would put the newest on top
	private void move() {
		if (out.isEmpty()) {
			while (!in.isEmpty()) {
				out.push(in.pop()); // pouring reverses: the oldest ends on top of out
			}
		}
	}
}
