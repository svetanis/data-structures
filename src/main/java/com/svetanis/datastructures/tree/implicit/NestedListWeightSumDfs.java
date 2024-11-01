package com.svetanis.datastructures.tree.implicit;

import java.util.ArrayList;
import java.util.List;

// 339. Nested List Weight Sum

public final class NestedListWeightSumDfs {
	// Time Complexity: O(n)

	public static int nws(List<NestedInteger> list) {
		return dfs(list, 1);
	}

	private static int dfs(List<NestedInteger> list, Integer depth) {
		int sum = 0;
		for (NestedInteger nested : list) {
			if (nested.isInteger()) {
				sum += nested.getInteger().get() * depth;
			} else {
				sum += dfs(nested.getList(), depth + 1);
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
	}
}
