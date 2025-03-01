package com.svetanis.datastructures.tree.binary.bt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.Print;

// 508. Most Frequent Subtree Sum

public final class MostFrequentSubtree {
	// Time Complexity: O(n)
	
	private int max = Integer.MIN_VALUE;

	public int[] mfs(Node root) {
		List<Integer> list = new ArrayList<>();
		Map<Integer, Integer> map = new HashMap<>();
		dfs(root, map);
		for (int key : map.keySet()) {
			if (map.get(key) == max) {
				list.add(key);
			}
		}
		return list.stream().mapToInt(i -> i).toArray();
	}

	private int dfs(Node root, Map<Integer, Integer> map) {
		if (root == null) {
			return 0;
		}
		int sum = root.data + dfs(root.left, map) + dfs(root.right, map);
		map.put(sum, map.getOrDefault(sum, 0) + 1);
		max = Math.max(max, map.get(sum));
		return sum;
	}

	public static void main(String[] args) {
		Node root = new Node(5);
		root.left = new Node(2);
		root.right = new Node(-3);
		MostFrequentSubtree mfs = new MostFrequentSubtree();
		int[] a = mfs.mfs(root);
		Print.print(a);

		Node root3 = new Node(5);
		root3.left = new Node(2);
		root3.right = new Node(-5);
		MostFrequentSubtree mfs2 = new MostFrequentSubtree();
		int[] a1 = mfs2.mfs(root3);
		Print.print(a1);
	}
}
