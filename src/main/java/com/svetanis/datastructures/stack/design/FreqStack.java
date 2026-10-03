package com.svetanis.datastructures.stack.design;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// 895. Maximum Frequency Stack
//
// push adds a value. pop removes and returns the value with the most copies in the stack; among
// values tied for the most copies, the one whose latest copy was pushed most recently.
//
// fmap counts each value's copies. fsm maps a count f to a stack of the values that reached
// count f, in push order, so a value with 3 copies sits in the stacks for 1, 2 and 3. The
// stack for the highest count, max, has the answer on top. Popping it leaves that value still
// in the stack for max - 1, so nothing else has to move.

public final class FreqStack {
	// Time Complexity: push, pop: O(1), hash map lookups and the top of one stack
	// Space Complexity: O(n), each push adds one entry to one stack in fsm

	private int max;
	private Map<Integer, Integer> fmap;
	private Map<Integer, Deque<Integer>> fsm;

	public FreqStack() {
		this.max = 0;
		this.fmap = new HashMap<>();
		this.fsm = new HashMap<>();
	}

	public void push(int val) {
		int freq = fmap.getOrDefault(val, 0) + 1;
		fmap.put(val, freq);
		fsm.computeIfAbsent(freq, k -> new ArrayDeque<>()).push(val); // under its new count
		max = Math.max(max, freq);
	}

	public int pop() {
		if (max == 0) { // no value has any copies left
			throw illegalState("stack underflow");
		}
		int val = fsm.get(max).pop();
		fmap.put(val, fmap.get(val) - 1);
		if (fsm.get(max).isEmpty()) {
			max--; // every value counted max times also sits in the stack for max - 1
		}
		return val;
	}

	public static void main(String[] args) {
		FreqStack fs = new FreqStack();
		fs.push(5); // The stack is [5]
		fs.push(7); // The stack is [5,7]
		fs.push(5); // The stack is [5,7,5]
		fs.push(7); // The stack is [5,7,5,7]
		fs.push(4); // The stack is [5,7,5,7,4]
		fs.push(5); // The stack is [5,7,5,7,4,5]
		System.out.println(fs.pop());
		System.out.println(fs.pop());
		System.out.println(fs.pop());
		System.out.println(fs.pop());
	}
}
