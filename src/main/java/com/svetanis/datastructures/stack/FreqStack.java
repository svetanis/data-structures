package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// 895. Maximum Frequency Stack

public final class FreqStack {

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
		fsm.computeIfAbsent(freq, k -> new ArrayDeque<>()).push(val);
		max = Math.max(max, freq);
	}

	public int pop() {
		int val = fsm.get(max).pop();
		fmap.put(val, fmap.get(val) - 1);
		if (fsm.get(max).isEmpty()) {
			max--;
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
