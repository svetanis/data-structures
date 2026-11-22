package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 636. Exclusive Time of Functions
//
// Input: n functions, ids 0 to n - 1, running on one thread, and log lines "id:start:time" or
// "id:end:time", in time order. Returns, for each id, the number of time units it ran itself,
// not counting the time spent in the calls it made.
//
// Time units are slots 0, 1, 2, ...: a start at t begins at the beginning of slot t, an end at
// t finishes at the end of slot t, so a call's SPAN is end - start + 1, both slots included.
//
// The one idea: a call's own time is its span minus the spans of the calls it made directly.
// The log is nested like brackets -- one thread, and an end pops the call stack -- so the call
// that ends is always the one on top, and after the pop the top is its caller. Each stack
// entry keeps {id, start}. When a call ends, it adds its span to its own cell and takes the
// same span off its caller's cell, right then. A caller's cell can go negative for a while:
// the debt arrives before its own span, because the inner call always ends first. Nobody
// looks deeper than one level -- a callee's span already contains its own callees.
//
// Three levels, 0 calls 1 calls 2:
//   2:end:4   span 2   a[2] += 2, caller 1: a[1] -= 2
//   1:end:6   span 6   a[1] += 6, caller 0: a[0] -= 6
//   0:end:8   span 9   a[0] += 9                         -> [9 - 6, 6 - 2, 2] = [3, 4, 2]
//
// Sibling: ExclusiveTimeOfFunctions credits the running call piece by piece at every log line,
// with one moving prev, and never subtracts. This file credits each call once, when it ends.

public final class ExclusiveTimeOfFunctionsSpan {
	// Time Complexity: O(m + n), m log lines, one push and at most one pop each; n for the answer
	// Space Complexity: O(m + n), the stack of open calls and the answer array

	public static int[] exclusiveTime(int n, List<String> logs) {
		int[] ans = new int[n]; // ANSWER: one cell per function
		Deque<int[]> dq = new ArrayDeque<>(); // STACK: {id, start}; top = running
		for (String log : logs) { // WALK: one line, in time order
			String[] tokens = log.split(":"); // READ: id, "start" or "end", time
			int id = Integer.parseInt(tokens[0]);
			int time = Integer.parseInt(tokens[2]);
			if (tokens[1].equals("start")) {
				dq.push(new int[] { id, time }); // OPEN: the old top is suspended
			} else {
				int[] ended = dq.pop(); // CLOSE: an end always belongs to the top
				int span = time - ended[1] + 1; // SPAN: both slots included
				ans[ended[0]] += span;
				if (!dq.isEmpty()) {
					ans[dq.peek()[0]] -= span; // DEBT: the caller did not run during those slots
				}
			}
		}
		return ans; // RETURN: each id's own time
	}

	public static void main(String[] args) {
		List<String> list1 = List.of("0:start:0", "1:start:2", "1:end:5", "0:end:6");
		System.out.println(Arrays.toString(exclusiveTime(2, list1))); // [3, 4]

		List<String> list2 = List.of("0:start:0", "0:start:2", "0:end:5", "0:start:6", "0:end:6", "0:end:7");
		System.out.println(Arrays.toString(exclusiveTime(1, list2))); // [8] -- recursion: one id, two frames

		List<String> list3 = List.of("0:start:0", "1:start:1", "2:start:3", "2:end:4", "1:end:6", "0:end:8");
		System.out.println(Arrays.toString(exclusiveTime(3, list3))); // [3, 4, 2] -- three levels deep
	}
}
