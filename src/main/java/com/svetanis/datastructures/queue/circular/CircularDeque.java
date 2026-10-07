package com.svetanis.datastructures.queue.circular;

import static com.svetanis.java.base.Exceptions.illegalArgument;

// 641. Design Circular Deque
//
// A double-ended queue of ints with a fixed capacity, stored in one array used as a ring.
//
// insertFront and insertLast add at either end and return false when full; deleteFront and
// deleteLast return false when empty; getFront and getRear return -1 when empty.
//
// Only front (the position of the first item) and size are stored. The last item sits at
// (front + size - 1) % capacity, so deleteLast only has to shrink size. Every index wraps
// modulo capacity, so no item is ever shifted.

public final class CircularDeque {
	// Time Complexity: every operation O(1), index arithmetic only
	// Space Complexity: O(k), one array of capacity k

	private int size; // current size of the queue
	private int front; // index of front element
	private int capacity; // max buffer size
	private int[] queue; // data storage array

	public CircularDeque(int k) {
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

	// enqueue
	public boolean insertFront(int value) {
		if (isFull()) {
			return false;
		}
		front = (front - 1 + capacity) % capacity; // + capacity: in Java, -1 % capacity is -1
		queue[front] = value;
		size++;
		return true;
	}

	public boolean insertLast(int value) {
		if (isFull()) {
			return false;
		}
		int index = (front + size) % capacity;
		queue[index] = value;
		size++;
		return true;
	}

	// dequeue
	public boolean deleteFront() {
		if (isEmpty()) {
			return false;
		}
		front = (front + 1) % capacity;
		size--;
		return true;
	}

	public boolean deleteLast() {
		if (isEmpty()) {
			return false;
		}
		size--; // the last item is found from front + size, so this alone drops it
		return true;
	}

	public int getFront() {
		if (isEmpty()) {
			return -1;
		} else {
			return queue[front];
		}
	}

	public int getRear() {
		if (isEmpty()) {
			return -1;
		} else {
			int index = (front + size - 1) % capacity;
			return queue[index];
		}
	}

	public static void main(String[] args) {
		CircularDeque queue = new CircularDeque(3);
		System.out.println(queue.insertLast(1)); // true
		System.out.println(queue.insertLast(2)); // true
		System.out.println(queue.insertFront(3)); // true
		System.out.println(queue.insertFront(4)); // false
		System.out.println(queue.getRear()); // 2
		System.out.println(queue.isFull()); // true
		System.out.println(queue.deleteLast()); // true
		System.out.println(queue.insertFront(4)); // true
		System.out.println(queue.getFront()); // 4
	}
}
