package com.svetanis.datastructures.tree.implicit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.java.base.Pair;

// 339. Nested List Weight Sum

public final class NestedListWeightSumBfs {
	// Time Complexity: O(n)

	public static int bfs(List<NestedInteger> list) {
		int sum = 0;
		Queue<Pair<NestedInteger, Integer>> queue = queue(list);
		while (!queue.isEmpty()) {
			Pair<NestedInteger, Integer> pair = queue.poll();
			NestedInteger nested = pair.getLeft();
			int depth = pair.getRight();
			if (nested.isInteger()) {
				sum += nested.getInteger().get() * depth;
			}
			for (NestedInteger child : nested.getList()) {
				queue.add(Pair.build(child, depth + 1));
			}
		}
		return sum;
	}

	private static Queue<Pair<NestedInteger, Integer>> queue(List<NestedInteger> list) {
		Queue<Pair<NestedInteger, Integer>> queue = new ArrayDeque<>();
		for (NestedInteger nested : list) {
			queue.add(Pair.build(nested, 1));
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
	}
}
