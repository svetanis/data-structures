package com.svetanis.datastructures.tree.implicit;

import java.util.ArrayList;
import java.util.List;

// 339. Nested List Weight Sum
//
// Input: nestedList, a list whose elements are each an integer or a list of further elements.
// The depth of an integer is the number of lists around it: 1 for an integer in the outer list,
// 2 for one inside a list in the outer list, and so on. Returns the sum of every integer times
// its depth. On LeetCode: 1 <= nestedList.length <= 50, the integers are in [-100, 100], and the
// maximum depth is at most 50.
//
// The one idea: the sum over a list is the sum over its elements. An integer in the list at
// depth d adds integer * d. An element that is itself a list is the same problem one level
// deeper: the sum over that list at depth d + 1. So one function that takes a list and its
// depth, and calls itself on each inner list with the depth plus one, covers every case.
//
// Time: O(n) -- every element is looked at once.
// Space: O(d) -- the recursion is one frame per level of nesting, d <= 50.
//
// Siblings: NestedListWeightSumBfs makes the same sum with a queue that carries each element's
// depth alongside it.

public final class NestedListWeightSumDfs {

	public static int nws(List<NestedInteger> list) {
		return dfs(list, 1); // START: the outer list is depth 1
	}

	private static int dfs(List<NestedInteger> list, Integer depth) {
		int sum = 0;
		for (NestedInteger nested : list) { // WALK: every element of this list
			if (nested.isInteger()) {
				sum += nested.getInteger().get() * depth; // ADD: the integer times its depth
			} else {
				sum += dfs(nested.getList(), depth + 1); // RECURSE: the inner list, one deeper
			}
		}
		return sum;
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
		System.out.println(nws(list)); // 23

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
		System.out.println(nws(ex1)); // 10

		// LeetCode example 2: [1,[4,[6]]]
		NestedInteger six = new NestedInteger();
		six.add(new NestedInteger(6));
		NestedInteger four = new NestedInteger();
		four.add(new NestedInteger(4));
		four.add(six);
		List<NestedInteger> ex2 = new ArrayList<>();
		ex2.add(new NestedInteger(1));
		ex2.add(four);
		System.out.println(nws(ex2)); // 27
	}
}
