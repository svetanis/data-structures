package com.svetanis.datastructures.queue.circular;

import static com.svetanis.java.base.Exceptions.illegalArgument;

// 622. Design Circular Queue
//
// A first-in-first-out queue of ints with a fixed capacity, stored in one array used as a ring.
// LC names the methods enQueue, deQueue, Front and Rear.
//
// enqueue returns false when full and dequeue returns false when empty; front and rear return
// -1 when empty.
//
// Only front (the position of the oldest item) and size are stored. The next free position is
// (front + size) % capacity, and the newest item sits one before it. Every index wraps modulo
// capacity, so no item is ever shifted.

public final class CircularQueue {
	// Time Complexity: every operation O(1), index arithmetic only
	// Space Complexity: O(k), one array of capacity k

	private int size; // current size of the queue
	private int front; // index of front element
	private int capacity; // max buffer size
	private int[] queue; // data storage array

	public CircularQueue(int k) {
		if (k < 1) { // every index wraps modulo k, so it must be at least 1
			throw illegalArgument("capacity must be at least 1, got %s", k);
		}
		this.front = 0;
		this.size = 0;
		this.capacity = k;
		this.queue = new int[k];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == capacity;
	}

	public int size() {
		return size;
	}

	public boolean enqueue(int value) {
		if (isFull()) {
			return false;
		}
		int index = (front + size) % capacity; // first free position after the newest item
		queue[index] = value;
		size++;
		return true;
	}

	public boolean dequeue() {
		if (isEmpty()) {
			return false;
		}
		front = (front + 1) % capacity; // the old slot is not cleared; a later enqueue reuses it
		size--;
		return true;
	}

	public int front() {
		if (isEmpty()) {
			return -1;
		} else {
			return queue[front];
		}
	}

	public int rear() {
		if (isEmpty()) {
			return -1;
		} else {
			int index = (front + size - 1) % capacity;
			return queue[index];
		}
	}

	public static void main(String[] args) {
		CircularQueue queue = new CircularQueue(3);
		System.out.println(queue.enqueue(1)); // true
		System.out.println(queue.enqueue(2)); // true
		System.out.println(queue.enqueue(3)); // true
		System.out.println(queue.enqueue(4)); // false
		System.out.println(queue.rear()); // 3
		System.out.println(queue.isFull()); // true
		System.out.println(queue.dequeue()); // true
		System.out.println(queue.enqueue(4)); // true
		System.out.println(queue.rear()); // 4
	}
}
