package com.svetanis.datastructures.stack.design;

import java.util.ArrayDeque;
import java.util.Deque;

// 1381. Design a Stack With Increment Operation
//
// A stack of at most maxSize integers. push ignores a value when the stack is full, pop returns
// -1 when it is empty, and increment(k, val) adds val to each of the bottom k elements (to all
// of them when fewer than k are held).
//
// The values sit in a Deque, and increments[i] holds the total added so far to the element at
// position i counted from the bottom. increment walks the bottom k positions and adds val to
// each. pop finds the top element's position, size - 1, adds that total to the value it
// returns, and clears the slot for the next element pushed there.

public class CustomStackSimple {
	// Time Complexity: push, pop O(1); increment O(min(k, size)), one slot per element it reaches
	// Space Complexity: O(maxSize) for the deque and the array

	private int maxSize;
	private int[] increments;
	private Deque<Integer> dq;

	public CustomStackSimple(int maxSize) {
		this.maxSize = maxSize;
		this.increments = new int[maxSize];
		this.dq = new ArrayDeque<>();
	}

	public void push(int x) {
		if (dq.size() < maxSize) {
			dq.push(x);
		}
	}

	public int pop() {
		if (dq.isEmpty()) {
			return -1;
		}
		int index = dq.size() - 1; // the top element's position, counted from the bottom
		int result = dq.pop() + increments[index];
		increments[index] = 0; // the next push to this position starts from 0
		return result;
	}

	public void increment(int k, int val) {
		for (int i = 0; i < Math.min(dq.size(), k); i++) {
			increments[i] += val;
		}
	}

	public static void main(String[] args) {
		CustomStackSimple stk = new CustomStackSimple(3); // Stack is Empty []
		stk.push(1); // stack becomes [1]
		stk.push(2); // stack becomes [1, 2]
		System.out.println(stk.pop()); // return 2 --> Return top of the stack 2, stack becomes [1]
		stk.push(2); // stack becomes [1, 2]
		stk.push(3); // stack becomes [1, 2, 3]
		stk.push(4); // stack still [1, 2, 3]: it already holds maxSize = 3 values
		stk.increment(5, 100); // stack becomes [101, 102, 103]
		stk.increment(2, 100); // stack becomes [201, 202, 103]
		System.out.println(stk.pop()); // return 103 --> Return top of the stack 103, stack becomes [201, 202]
		System.out.println(stk.pop()); // return 202 --> Return top of the stack 202, stack becomes [201]
		System.out.println(stk.pop()); // return 201 --> Return top of the stack 201, stack becomes []
		System.out.println(stk.pop()); // return -1 --> Stack is empty return -1.
	}
}