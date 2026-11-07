package com.svetanis.datastructures.queue.circular;

// 622. Design Circular Queue
//
// Input: a first-in-first-out queue of ints with a fixed capacity k, stored in one array used as
// a ring. LC names the methods enQueue, deQueue, Front and Rear. enqueue returns false when full
// and dequeue returns false when empty; front and rear return -1 when empty. On LeetCode:
// 1 <= k <= 1000, 0 <= value <= 1000, at most 3000 calls.
//
// The one idea: one array slot is one place in the line, and the line wraps: after the last slot
// comes slot 0 again. Only front (the slot of the oldest item) and size are stored. The next
// free slot is (front + size) % capacity, and the newest item sits one slot before it. Every
// index wraps modulo capacity, so no item is ever shifted.
//
// Time: every operation O(1) -- index arithmetic only
// Space: O(k) -- one array of capacity k
//
// Siblings:
//   CircularQueueRear  the same problem, with rear stored as well: every index moves only by
//                      (x + 1) % capacity, and rear starts at -1
//   CircularDeque      LC 641, the same ring with adds and removes at both ends
//   CircularBuffer     not LeetCode: when full, an append overwrites the oldest item
//   DynamicQueue       not LeetCode: when full, the array doubles

public final class CircularQueue {
	private static final String BAD_CAPACITY = "capacity must be at least 1, got %d";

	private int size; // current size of the queue
	private int front; // index of front element
	private int capacity; // max buffer size
	private int[] queue; // data storage array

	public CircularQueue(int k) {
		if (k < 1) { // every index wraps modulo k, so it must be at least 1
			throw new IllegalArgumentException(BAD_CAPACITY.formatted(k));
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
		if (isFull()) { // FULL?
			return false;
		}
		int index = (front + size) % capacity; // first free position after the newest item
		queue[index] = value; // WRITE
		size++; // GROW
		return true;
	}

	public boolean dequeue() {
		if (isEmpty()) { // EMPTY?
			return false;
		}
		front = (front + 1) % capacity; // ADVANCE; the old slot is not cleared, a later enqueue reuses it
		size--; // SHRINK
		return true;
	}

	public int front() {
		if (isEmpty()) { // EMPTY?
			return -1;
		} else {
			return queue[front]; // the oldest
		}
	}

	public int rear() {
		if (isEmpty()) { // EMPTY?
			return -1;
		} else {
			int index = (front + size - 1) % capacity; // the newest: one fewer step than size
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
