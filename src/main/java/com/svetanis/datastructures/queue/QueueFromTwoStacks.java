package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 232. Implement Queue using Stacks
//
// Input: calls to push(x), pop(), peek() and empty(), using only the standard operations of a
// stack: push to top, peek/pop from top, size and is empty. pop removes and returns the front of
// the queue, peek returns it. On LeetCode: 1 <= x <= 9, at most 100 calls, and every pop and peek
// is made on a non-empty queue.
//
// The one idea: the queue is two stacks, and one entry is one value in exactly one of them.
// first holds the newest arrivals, newest on top. second holds the older values with the oldest on
// top, which is the front of the queue. Pouring one stack onto another reverses its order, so
// pouring first onto second turns "newest on top" into "oldest on top". Pour only when second is
// empty: everything in second is older than everything in first, so second must be used up
// first. Then each value is poured once in its whole life: one pop can cost O(n), but n calls
// cost O(n) in all.
//
// Time: O(1) amortized per call -- each value moves from first to second at most once
// Space: O(n) -- every value sits in exactly one of the two stacks
//
// Siblings: QueueTwoStacks is the same design for any element type; QueueOneStack uses one stack
// and recursion, so its dequeue costs O(n) every time.

public final class QueueFromTwoStacks {
	private final Deque<Integer> first; // the back of the queue: new arrivals, newest on top
	private final Deque<Integer> second; // the front of the queue: oldest on top

	public QueueFromTwoStacks() {
		this.first = new ArrayDeque<>();
		this.second = new ArrayDeque<>();
	}

	public void push(int x) {
		first.push(x); // TAKE: always onto the first, never disturbs the second
	}

	public int pop() {
		unload();
		return second.pop(); // RETURN: the oldest; NoSuchElementException if the queue is empty
	}

	public int peek() {
		unload();
		return second.peek(); // RETURN: the oldest; NullPointerException if the queue is empty: LC promises it is not
	}

	public boolean empty() {
		return first.isEmpty() && second.isEmpty(); // RETURN: elements can be in either stack
	}

	// POUR only when the second is EMPTY: pouring onto older elements would put the newest on top
	private void unload() {
		if (second.isEmpty()) {
			while (!first.isEmpty()) {
				second.push(first.pop()); // MOVE: pouring reverses: the oldest ends on top
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
