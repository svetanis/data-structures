package com.svetanis.datastructures.tree.binary.bt.path;

import java.util.HashMap;
import java.util.Map;

// 666. Path Sum IV 

public final class PathSumIV {
	// Time complexity: O(n)

	private int total;
	private Map<Integer, Integer> map;

	public int pathSum(int[] a) {
		this.total = 0;
		this.map = new HashMap<>();
		for (int num : a) {
			map.put(num / 10, num % 10);
		}
		dfs(11, 0);
		return total;
	}

	private void dfs(int node, int sum) {
		if (!map.containsKey(node)) {
			return;
		}
		sum += map.get(node);
		int level = node / 10;
		int position = node % 10;
		int left = (level + 1) * 10 + (2 * position) - 1;
		int right = left + 1;
		// leaf node
		if (!map.containsKey(left) && !map.containsKey(right)) {
			total += sum;
			return;
		}
		dfs(left, sum);
		dfs(right, sum);
	}

	public static void main(String[] args) {
		int[] a = { 111, 121, 122, 131, 132 };
		PathSumIV ps = new PathSumIV();
		System.out.println(ps.pathSum(a));
	}
}
