package com.svetanis.datastructures.queue.circular;

public final class CircularDeque {

	private int size; // current size of the queue
	private int front; // index of front element
	private int capacity; // max buffer size
	private int[] queue; // data storage array

	public CircularDeque(int k) {
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
		front = (front - 1 + capacity) % capacity;
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
		size--;
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
