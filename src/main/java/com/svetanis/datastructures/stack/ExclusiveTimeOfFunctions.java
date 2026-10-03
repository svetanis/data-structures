package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 636. Exclusive Time of Functions
//
// Input: n functions, ids 0 to n - 1, running on one thread, and log lines "id:start:time" or
// "id:end:time". Returns, for each id, the number of time units it ran itself, not counting
// the time spent in the calls it made.
//
// The stack holds the calls that have started and not ended, the running one on top. prev is
// the first time unit not yet credited to any call. At each log line, the time from prev up to
// now belongs to the call on top. A start begins AT its time unit, so the caller is credited
// up to it; an end finishes at the END of its unit, hence the + 1.

public final class ExclusiveTimeOfFunctions {
	// Time Complexity: O(m + n), m log lines, each split and read once; n to create the answer
	// Space Complexity: O(m + n), the stack of open calls and the answer array

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
					a[top] += timestamp - prev; // the caller ran until this call began
				}
				stack.push(id);
				prev = timestamp;
			} else {
				int top = stack.pop();
				a[top] += timestamp - prev + 1; // an end includes its own time unit
				prev = timestamp + 1; // the next unit starts after this end
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
