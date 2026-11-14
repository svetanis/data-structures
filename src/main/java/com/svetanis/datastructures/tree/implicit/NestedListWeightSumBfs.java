package com.svetanis.datastructures.tree.implicit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 339. Nested List Weight Sum
//
// Input: nestedList, a list whose elements are each an integer or a list of further elements.
// The depth of an integer is the number of lists around it: 1 for an integer in the outer list,
// 2 for one inside a list in the outer list, and so on. Returns the sum of every integer times
// its depth. On LeetCode: 1 <= nestedList.length <= 50, the integers are in [-100, 100], and the
// maximum depth is at most 50.
//
// The one idea: one queue entry is an element together with its depth. Start with the elements
// of the outer list at depth 1. Taking an entry off the front, if it is an integer add
// integer * depth to the sum; its elements, if it is a list, go to the back of the queue at
// depth + 1. The queue holds every depth-1 entry, then every depth-2 entry, and so on, so each
// integer is met exactly once and always with the depth of the list that holds it.
//
// Time: O(n) -- every element enters and leaves the queue once.
// Space: O(n) -- the queue can hold a whole level of elements.
//
// Siblings: NestedListWeightSumDfs makes the same sum with a recursion that passes the depth
// down.

public final class NestedListWeightSumBfs {

	public static int bfs(List<NestedInteger> list) {
		int sum = 0;
		Queue<Map.Entry<NestedInteger, Integer>> queue = queue(list); // QUEUE: (element, depth)
		while (!queue.isEmpty()) {
			Map.Entry<NestedInteger, Integer> pair = queue.poll(); // TAKE: the front entry
			NestedInteger nested = pair.getKey();
			int depth = pair.getValue();
			if (nested.isInteger()) {
				sum += nested.getInteger().get() * depth; // ADD: the integer times its depth
			}
			for (NestedInteger child : nested.getList()) { // WALK: a list's elements, if any
				queue.add(Map.entry(child, depth + 1)); // PUSH: one level deeper
			}
		}
		return sum;
	}

	private static Queue<Map.Entry<NestedInteger, Integer>> queue(List<NestedInteger> list) {
		Queue<Map.Entry<NestedInteger, Integer>> queue = new ArrayDeque<>();
		for (NestedInteger nested : list) {
			queue.add(Map.entry(nested, 1)); // START: the outer list is depth 1
		}
		return queue;
	}

	public static void main(String[] args) {
		NestedInteger nested1 = new NestedInteger();
		nested1.add(new NestedInteger(2));
		nested1.add(new NestedInteger(2));

		NestedInteger nested2 = new NestedInteger();
		nested2.add(new NestedInteger(3));

		NestedInteger nested3 = new NestedInteger();
		nested3.add(new NestedInteger(2));
		nested3.add(nested2);

		List<NestedInteger> list = new ArrayList<>();
		list.add(new NestedInteger(1));
		list.add(nested1);
		list.add(nested3);
		list.add(new NestedInteger(1));
		// [1,[2,2],[[3],2],1]
		// 1*1 + 2*2 + 2*2 + 3*3 + 2*2 + 1*1
		System.out.println(bfs(list)); // 23

		// LeetCode example 1: [[1,1],2,[1,1]]
		NestedInteger pair1 = new NestedInteger();
		pair1.add(new NestedInteger(1));
		pair1.add(new NestedInteger(1));
		NestedInteger pair2 = new NestedInteger();
		pair2.add(new NestedInteger(1));
		pair2.add(new NestedInteger(1));
		List<NestedInteger> ex1 = new ArrayList<>();
		ex1.add(pair1);
		ex1.add(new NestedInteger(2));
		ex1.add(pair2);
		System.out.println(bfs(ex1)); // 10

		// LeetCode example 2: [1,[4,[6]]]
		NestedInteger six = new NestedInteger();
		six.add(new NestedInteger(6));
		NestedInteger four = new NestedInteger();
		four.add(new NestedInteger(4));
		four.add(six);
		List<NestedInteger> ex2 = new ArrayList<>();
		ex2.add(new NestedInteger(1));
		ex2.add(four);
		System.out.println(bfs(ex2)); // 27
	}
}
