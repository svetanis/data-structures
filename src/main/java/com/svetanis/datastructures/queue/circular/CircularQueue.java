package com.svetanis.datastructures.queue.circular;

public final class CircularQueue {

	private int size; // current size of the queue
	private int front; // index of front element
	private int capacity; // max buffer size
	private int[] queue; // data storage array

	public CircularQueue(int k) {
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
		int index = (front + size) % capacity;
		queue[index] = value;
		size++;
		return true;
	}

	public boolean dequeue() {
		if (isEmpty()) {
			return false;
		}
		front = (front + 1) % capacity;
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
