package com.svetanis.datastructures.queue.circular;

// 622. Design Circular Queue
//
// Input: a first-in-first-out queue of ints with a fixed capacity k, stored in one array used as
// a ring. LC names the methods enQueue, deQueue, Front and Rear. enqueue returns false when full
// and dequeue returns false when empty; front and rear return -1 when empty. On LeetCode:
// 1 <= k <= 1000, 0 <= value <= 1000, at most 3000 calls.
//
// The one idea: one array slot is one place in the line, and the line wraps: after the last slot
// comes slot 0 again. Every index only ever moves one step on, (i + 1) % capacity, so no slot is
// ever worked out by adding a count to a position. front is the slot of the oldest item, rear the
// slot of the newest, and size is how many items there are. rear starts at -1, so the first
// enqueue steps onto (-1 + 1) % capacity = 0. size is still needed: with front and rear alone,
// an empty queue and a full one look the same.
//
// Time: every operation O(1) -- one step of index arithmetic at most
// Space: O(k) -- one array of capacity k
//
// Siblings:
//   CircularQueue   the same problem with front and size stored; the newest item and the free
//                   slot are worked out as (front + size - 1) and (front + size), wrapped
//   CircularDeque   LC 641, adds and removes at both ends
//   CircularBuffer  not LeetCode: when full, an append overwrites the oldest item
//   DynamicQueue    not LeetCode: when full, the array doubles

public final class CircularQueueRear {
	private static final String BAD_CAPACITY = "capacity must be at least 1, got %d";

	private final int[] queue; // the ring
	private final int capacity;
	private int front; // the oldest item's slot: only dequeue moves it
	private int rear; // the newest item's slot: only enqueue moves it
	private int size; // how many items are in the queue

	public CircularQueueRear(int k) {
		if (k < 1) { // every index wraps modulo k, so it must be at least 1
			throw new IllegalArgumentException(BAD_CAPACITY.formatted(k));
		}
		this.capacity = k;
		this.queue = new int[k];
		this.front = 0;
		this.rear = -1; // the first step lands on slot 0
		this.size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == capacity;
	}

	public boolean enqueue(int value) {
		if (isFull()) { // FULL?
			return false;
		}
		rear = (rear + 1) % capacity; // STEP rear on first, so it points at the newest item
		queue[rear] = value; // WRITE
		size++; // GROW
		return true;
	}

	public boolean dequeue() {
		if (isEmpty()) { // EMPTY?
			return false;
		}
		front = (front + 1) % capacity; // STEP front on; the old slot is not cleared
		size--; // SHRINK
		return true;
	}

	public int front() {
		return isEmpty() ? -1 : queue[front]; // the oldest
	}

	public int rear() {
		return isEmpty() ? -1 : queue[rear]; // the newest
	}

	public static void main(String[] args) {
		CircularQueueRear queue = new CircularQueueRear(3);
		System.out.println(queue.enqueue(1)); // true
		System.out.println(queue.enqueue(2)); // true
		System.out.println(queue.enqueue(3)); // true
		System.out.println(queue.enqueue(4)); // false
		System.out.println(queue.rear()); // 3
		System.out.println(queue.isFull()); // true
		System.out.println(queue.dequeue()); // true
		System.out.println(queue.enqueue(4)); // true
		System.out.println(queue.rear()); // 4
		System.out.println(queue.front()); // 2
	}
}
