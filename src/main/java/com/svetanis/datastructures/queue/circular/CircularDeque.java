package com.svetanis.datastructures.queue.circular;

// 641. Design Circular Deque
//
// Input: a double-ended queue of ints with a fixed capacity k, stored in one array used as a
// ring. insertFront and insertLast add at either end and return false when full; deleteFront and
// deleteLast return false when empty; getFront and getRear return -1 when empty. On LeetCode:
// 1 <= k <= 1000, 0 <= value <= 1000, at most 2000 calls.
//
// The one idea: one array slot is one place in the line, and the line wraps: after the last slot
// comes slot 0 again, and before slot 0 comes the last. Only front (the slot of the first item)
// and size are stored. The last item sits at (front + size - 1) % capacity, so deleteLast only
// has to shrink size. Every index wraps modulo capacity, so no item is ever shifted.
//
// Time: every operation O(1) -- index arithmetic only
// Space: O(k) -- one array of capacity k
//
// Siblings:
//   CircularQueue      LC 622, the same front + size ring, adding only at the back and
//                      removing only at the front
//   CircularQueueRear  LC 622 with rear stored as well; every index moves by (x + 1) % capacity
//   CircularBuffer     not LeetCode: when full, an append overwrites the oldest item
//   DynamicQueue       not LeetCode: when full, the array doubles

public final class CircularDeque {
	private static final String BAD_CAPACITY = "capacity must be at least 1, got %d";

	private int size; // current size of the queue
	private int front; // index of front element
	private int capacity; // max buffer size
	private int[] queue; // data storage array

	public CircularDeque(int k) {
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

	// enqueue
	public boolean insertFront(int value) {
		if (isFull()) { // FULL?
			return false;
		}
		front = (front - 1 + capacity) % capacity; // STEP back; + capacity: in Java, -1 % capacity is -1
		queue[front] = value; // WRITE
		size++; // GROW
		return true;
	}

	public boolean insertLast(int value) {
		if (isFull()) { // FULL?
			return false;
		}
		int index = (front + size) % capacity; // the first free position after the last item
		queue[index] = value; // WRITE
		size++; // GROW
		return true;
	}

	// dequeue
	public boolean deleteFront() {
		if (isEmpty()) { // EMPTY?
			return false;
		}
		front = (front + 1) % capacity; // ADVANCE
		size--; // SHRINK
		return true;
	}

	public boolean deleteLast() {
		if (isEmpty()) { // EMPTY?
			return false;
		}
		size--; // SHRINK: the last item is found from front + size, so this alone drops it
		return true;
	}

	public int getFront() {
		if (isEmpty()) { // EMPTY?
			return -1;
		} else {
			return queue[front];
		}
	}

	public int getRear() {
		if (isEmpty()) { // EMPTY?
			return -1;
		} else {
			int index = (front + size - 1) % capacity; // the last: one fewer step than size
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
