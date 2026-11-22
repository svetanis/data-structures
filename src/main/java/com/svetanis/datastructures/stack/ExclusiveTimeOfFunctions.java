package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 636. Exclusive Time of Functions
//
// Input: n functions, ids 0 to n - 1, running on one thread, and log lines "id:start:time" or
// "id:end:time". Returns, for each id, the number of time units it ran itself, not counting
// the time spent in the calls it made.
//
// The stack holds the calls that have started and not ended: the RUNNING one on top, the
// SUSPENDED ones beneath it, each waiting for the call it made to finish. The running call
// changes only at a log line -- a start suspends it, an end finishes it and the one beneath
// resumes. So at every log line, the call that was running until now is credited with the
// time units since it last started running, and prev marks where that was:
//
//   prev = the first time unit not yet credited to any call, which is the unit where the
//          call on top started (or resumed) running.
//
// A start at t begins AT unit t, so the call that was running gets the units prev .. t - 1,
// which is t - prev, and the new call runs from t. An end at t runs THROUGH unit t, so the
// ending call gets prev .. t, which is t - prev + 1, and the caller resumes at t + 1.
//
// Example 1, 0:start:0, 1:start:2, 1:end:5, 0:end:6:
//   0:start:0  nothing ran yet                         push 0     prev = 0
//   1:start:2  0 ran units 0, 1:     2 - 0     = 2     push 1     prev = 2
//   1:end:5    1 ran units 2 .. 5:   5 - 2 + 1 = 4     pop 1      prev = 6   (0 resumes)
//   0:end:6    0 ran unit 6:         6 - 6 + 1 = 1     pop 0      prev = 7
//   answer [2 + 1, 4] = [3, 4]
//
// Sibling: ExclusiveTimeOfFunctionsSpan credits each call once, when it ends -- its whole
// span, end - start + 1, minus the spans of the calls it made. This file credits the running
// call piece by piece, at every log line, so it never subtracts anything.

public final class ExclusiveTimeOfFunctions {
	// Time Complexity: O(m + n), m log lines, each split and read once; n to create the answer
	// Space Complexity: O(m + n), the stack of open calls and the answer array

	public static int[] exclusiveTime(int n, List<String> logs) {
		int prev = -1; // PREV: never read before the first start sets it
		int[] a = new int[n]; // ANSWER: each id's own time
		Deque<Integer> stack = new ArrayDeque<>(); // STACK: ids; top = running, beneath = suspended
		for(String log : logs) { // WALK: the log lines, in time order
			String[] tokens = log.split(":"); // READ: id, start or end, time
			int id = Integer.parseInt(tokens[0]);
			int timestamp = Integer.parseInt(tokens[2]);
			if("start".equals(tokens[1])) {
				if(!stack.isEmpty()) { // SUSPEND: a call was running until now
					int top = stack.peek();
					a[top] += timestamp - prev; // CREDIT: the caller ran until this call began -- units prev .. timestamp - 1
				}
				stack.push(id); // PUSH: the new call is now the one running
				prev = timestamp; // MARK: the new call owns this unit onward -- it runs from here
			} else {
				int top = stack.pop(); // POP: the running call ends
				a[top] += timestamp - prev + 1; // CREDIT: an end includes its own time unit
				prev = timestamp + 1; // MARK: the next unit starts after this end -- the caller resumes here
			}
		}
		return a; // RETURN: each id's own time
	}

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
		list1.add("0:start:0");
		list1.add("1:start:2");
		list1.add("1:end:5");
		list1.add("0:end:6");
		System.out.println(Arrays.toString(exclusiveTime(2, list1))); // [3,4]

		List<String> list2 = new ArrayList<>();
		list2.add("0:start:0");
		list2.add("0:start:2");
		list2.add("0:end:5");
		list2.add("0:start:6");
		list2.add("0:end:6");
		list2.add("0:end:7");
		System.out.println(Arrays.toString(exclusiveTime(1, list2))); // [8]

		List<String> list3 = List.of("0:start:0", "1:start:1", "2:start:3", "2:end:4", "1:end:6", "0:end:8");
		System.out.println(Arrays.toString(exclusiveTime(3, list3))); // [3, 4, 2] -- three levels deep
	}

}
