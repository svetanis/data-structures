package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 232. Implement Queue using Stacks

// You must use only standard operations of a stack,
// which means only push to top, peek/pop from top,
// size, and is empty operations are valid.

// The oldest element sits at the bottom of a stack. Pouring one stack onto another reverses
// the order, so the oldest ends on top of the second. New arrivals go onto the first; the
// first is poured only when the second is empty, so each element is poured once in its life:
// any single pop can be O(n), but n calls cost O(n) in total -- amortized O(1).
// QueueTwoStacks is the same design for any type.

public final class QueueFromTwoStacks {
	// Time Complexity: O(1) amortized per call
	// Space Complexity: O(n)

	private final Deque<Integer> first; // the back of the queue: new arrivals, newest on top
	private final Deque<Integer> second; // the front of the queue: oldest on top

	public QueueFromTwoStacks() {
		this.first = new ArrayDeque<>();
		this.second = new ArrayDeque<>();
	}

	public void push(int x) {
		first.push(x); // always onto the first, never disturbs the second
	}

	public int pop() {
		unload();
		return second.pop(); // the oldest; NoSuchElementException if the queue is empty
	}

	public int peek() {
		unload();
		return second.peek(); // NullPointerException if the queue is empty: LC promises it is not
	}

	public boolean empty() {
		return first.isEmpty() && second.isEmpty(); // elements can be in either stack
	}

	// pour only when the second is EMPTY: pouring onto older elements would put the newest on top
	private void unload() {
		if (second.isEmpty()) {
			while (!first.isEmpty()) {
				second.push(first.pop()); // pouring reverses: the oldest ends on top
			}
		}
	}

	public static void main(String[] args) {
		QueueFromTwoStacks queue = new QueueFromTwoStacks();
		queue.push(1);
		queue.push(2);
		System.out.println(queue.peek()); // 1
		System.out.println(queue.pop()); // 1
		System.out.println(queue.empty()); // false
		queue.push(3);
		System.out.println(queue.pop()); // 2
		System.out.println(queue.pop()); // 3
		System.out.println(queue.empty()); // true
	}
}
