package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// A first-in-first-out queue of any type, built from two stacks (not a LeetCode problem; LC 232
// is the same design with int methods).
//
// Input: enqueue(value) adds at the back; dequeue() removes and returns the front item; peek()
// returns it without removing; size() counts the items. dequeue throws NoSuchElementException on
// an empty queue; peek returns null.
//
// The one idea: one entry is one queued item, held in exactly one of the two stacks. in holds the
// newest arrivals, newest on top. out holds the older items, oldest on top, which is the front
// of the queue. A stack gives back its newest item first, and pouring all of in onto out
// reverses the order again, so out gives back the oldest first. Pour only when out is empty:
// every item in out is older than every item in in, so out must be used up first.
//
// Time: enqueue, dequeue, peek amortized O(1) -- each item moves from in to out at most once;
//   size O(1)
// Space: O(n) -- each item sits in exactly one of the two stacks
//
// Siblings: QueueFromTwoStacks is the same design with LC 232's int methods; QueueOneStack
// uses one stack and recursion.

public final class QueueTwoStacks<T> {
	private Deque<T> in; // back of queue: new arrivals, newest on top
	private Deque<T> out; // front of queue: oldest on top

	// create an empty queue
	public QueueTwoStacks() {
		this.in = new ArrayDeque<>();
		this.out = new ArrayDeque<>();
	}

	public int size() {
		return in.size() + out.size(); // COUNT: each item is in exactly one stack
	}

	public void enqueue(T value) {
		in.push(value); // TAKE: ArrayDeque refuses null: enqueue(null) throws NullPointerException
	}

	public T dequeue() {
		move();
		return out.pop(); // RETURN: the oldest; NoSuchElementException if the queue is empty
	}

	public T peek() {
		move();
		return out.peek(); // RETURN: the oldest; null if the queue is empty
	}

	// POUR only when out is EMPTY: pouring onto older elements would put the newest on top
	private void move() {
		if (out.isEmpty()) {
			while (!in.isEmpty()) {
				out.push(in.pop()); // MOVE: pouring reverses: the oldest ends on top of out
			}
		}
	}

	public static void main(String[] args) {
		QueueTwoStacks<String> queue = new QueueTwoStacks<>();
		queue.enqueue("a");
		queue.enqueue("b");
		System.out.println(queue.peek()); // a
		System.out.println(queue.dequeue()); // a
		queue.enqueue("c");
		System.out.println(queue.dequeue()); // b
		System.out.println(queue.size()); // 1
		System.out.println(queue.dequeue()); // c
		System.out.println(queue.peek()); // null
	}
}
