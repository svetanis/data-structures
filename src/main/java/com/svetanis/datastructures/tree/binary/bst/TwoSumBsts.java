package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1214. Two Sum BSTs

public final class TwoSumBsts {
	// Time Complexity: O(n + m)
	// Space Complexity: O(n + m)

	private List<Integer>[] list = new List[2];

	public boolean twoSum(Node root1, Node root2, int target) {
		Arrays.setAll(list, x -> new ArrayList<>());
		dfs(root1, 0);
		dfs(root2, 1);
		int n = list[0].size();
		int m = list[1].size();
		int left = 0, right = m - 1;
		while (left < n && right >= 0) {
			int sum = list[0].get(left) + list[1].get(right);
			if (sum == target) {
				return true;
			} else if (sum < target) {
				left++;
			} else {
				right--;
			}
		}
		return false;
	}

	private void dfs(Node root, int index) {
		if (root == null) {
			return;
		}
		dfs(root.left, index);
		list[index].add(root.data);
		dfs(root.right, index);
	}

	public static void main(String[] args) {
		Node root1 = newNode(2);
		root1.left = newNode(1);
		root1.right = newNode(3);

		Node root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(4);
		TwoSumBsts tsb = new TwoSumBsts();
		System.out.println(tsb.twoSum(root1, root2, 5));
	}
}