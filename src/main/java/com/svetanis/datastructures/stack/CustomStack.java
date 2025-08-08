package com.svetanis.datastructures.stack;

// 1381. Design a Stack With Increment Operation

public class CustomStack {

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
			increments[topIndex - 1] += increments[topIndex];
		}
		increments[topIndex] = 0;
		return result;
	}

	public void increment(int k, int val) {
		if (topIndex > 0) {
			int index = Math.min(topIndex, k) - 1;
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
		stk.push(4); // stack still [1, 2, 3], Do not add another elements as size is 4
		stk.increment(5, 100); // stack becomes [101, 102, 103]
		stk.increment(2, 100); // stack becomes [201, 202, 103]
		System.out.println(stk.pop()); // return 103 --> Return top of the stack 103, stack becomes [201, 202]
		System.out.println(stk.pop()); // return 202 --> Return top of the stack 202, stack becomes [201]
		System.out.println(stk.pop()); // return 201 --> Return top of the stack 201, stack becomes []
		System.out.println(stk.pop()); // return -1 --> Stack is empty return -1.
	}
}