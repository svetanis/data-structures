package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 636. Exclusive Time of Functions

public final class ExclusiveTimeOfFunctions {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] exclusiveTime(int n, List<String> logs) {
		int prev = -1;
		int[] a = new int[n];
		Deque<Integer> stack = new ArrayDeque<>();
		for(String log : logs) {
			String[] tokens = log.split(":");
			int id = Integer.parseInt(tokens[0]);
			int timestamp = Integer.parseInt(tokens[2]);
			if("start".equals(tokens[1])) {
				if(!stack.isEmpty()) {
					int top = stack.peek();
					a[top] += timestamp - prev;
				}
				stack.push(id);
				prev = timestamp;
			} else {
				int top = stack.pop();
				a[top] += timestamp - prev + 1;
				prev = timestamp + 1;
			}
		}
		return a;
	}

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
		list1.add("0:start:0");
		list1.add("1:start:2");
		list1.add("1:end:5");
		list1.add("0:end:6");
		Print.print(exclusiveTime(2, list1)); // [3,4]

		List<String> list2 = new ArrayList<>();
		list2.add("0:start:0");
		list2.add("0:start:2");
		list2.add("0:end:5");
		list2.add("0:start:6");
		list2.add("0:end:6");
		list2.add("0:end:7");
		Print.print(exclusiveTime(1, list2)); // [8]
	}

}
