package com.svetanis.datastructures.stack.design;

// 1381. Design a Stack With Increment Operation
//
// A stack of at most maxSize integers. push ignores a value when the stack is full, pop returns
// -1 when it is empty, and increment(k, val) adds val to each of the bottom k elements (to all
// of them when fewer than k are held).
//
// increment writes val once, at the highest index it reaches, instead of adding it k times:
// increments[i] is an amount still owed to the element at index i and to every element below
// it. pop adds the amount owed at the top element's index to the value it returns, and passes
// that amount down to the element below, which is still owed it.

public class CustomStack {
	// Time Complexity: push, pop, increment: O(1), increment writes one slot
	// Space Complexity: O(maxSize) for the two arrays

	private int topIndex;
	private int[] stack;
	private int[] increments;

	public CustomStack(int maxSize) {
		this.topIndex = 0;
		this.stack = new int[maxSize];
		this.increments = new int[maxSize];
	}

	public void push(int x) {
		if (topIndex < stack.length) {
			stack[topIndex++] = x;
		}
	}

	public int pop() {
		if (topIndex <= 0) {
			return -1;
		}
		int result = stack[--topIndex] + increments[topIndex];
		if (topIndex > 0) {
			increments[topIndex - 1] += increments[topIndex]; // the element below is owed it too
		}
		increments[topIndex] = 0; // the next push to this index starts owing nothing
		return result;
	}

	public void increment(int k, int val) {
		if (topIndex > 0 && k > 0) { // k <= 0 reaches no element
			int index = Math.min(topIndex, k) - 1; // the highest element the increment reaches
			increments[index] += val;
		}
	}

	public static void main(String[] args) {
		CustomStack stk = new CustomStack(3); // Stack is Empty []
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