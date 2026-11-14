package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.HashMap;
import java.util.Map;

// 437. Path Sum III
//
// Input: the root of a binary tree and an integer targetSum. A path starts at any node and goes
// down to one of its descendants. Returns how many such paths have values adding up to
// targetSum. On LeetCode: 0 <= n <= 1000, -10^9 <= val <= 10^9, -10^9 <= targetSum <= 10^9.
//
// The one idea: give every node its prefix sum, the total of the values from the root down to
// and including that node. A downward path from just below node A to node B has sum
// prefix(B) - prefix(A). So at node B, the number of paths ending at B with sum targetSum is the
// number of ancestors A (or the empty start above the root) whose prefix equals
// prefix(B) - targetSum. The map holds, for the ancestors of the current node only, how many
// times each prefix sum occurs; a node adds its own prefix before going down and takes it back
// out when leaving, so the map never mixes in another branch.
//
// Time: O(n) -- one map lookup and two updates per node.
// Space: O(n) -- the recursion stack plus the map; a count that drops back to 0 stays in the map.
//
// Siblings: CountAllPathSum adds each tail of the root path up again at every node, O(n * h);
// AllPathSum lists the paths instead of counting them.
public final class CountAllPathSumHashing {

	public static int pathCount(TreeNode root, int target) {
		Map<Long, Integer> map = new HashMap<>(); // MAP: prefix sum -> how often it occurs above
		return pathCount(root, target, 0, map);
	}

	private static int pathCount(TreeNode node, int target, long sum, Map<Long, Integer> map) {
		if (node == null) { // BASE: nothing below
			return 0;
		}
		int count = 0;
		sum += node.val; // PREFIX: root-to-current total; long, since 1000 values reach 10^12
		if (sum == target) { // COUNT: the path from the root itself
			count++;
		}
		// COUNT: each earlier prefix equal to sum - target starts a path that ends here
		count += map.getOrDefault(sum - target, 0);
		map.put(sum, map.getOrDefault(sum, 0) + 1); // TAKE: this node's prefix joins the map

		count += pathCount(node.left, target, sum, map); // WALK: left subtree
		count += pathCount(node.right, target, sum, map); // WALK: right subtree
		map.put(sum, map.getOrDefault(sum, 1) - 1); // UNDO: leave the node
		return count;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(12);
		root.left = newNode(7);
		root.right = newNode(1);
		root.left.left = newNode(4);
		root.right.left = newNode(10);
		root.right.right = newNode(5);
		System.out.println(pathCount(root, 11)); // 2

		// LeetCode example 1
		TreeNode root2 = newNode(10);
		root2.left = newNode(5);
		root2.right = newNode(-3);
		root2.left.left = newNode(3);
		root2.left.right = newNode(2);
		root2.left.left.left = newNode(3);
		root2.left.left.right = newNode(-2);
		root2.left.right.right = newNode(1);
		root2.right.right = newNode(11);
		System.out.println(pathCount(root2, 8)); // 3

		// LeetCode example 2
		TreeNode root3 = newNode(5);
		root3.left = newNode(4);
		root3.right = newNode(8);
		root3.left.left = newNode(11);
		root3.right.left = newNode(13);
		root3.right.right = newNode(4);
		root3.left.left.left = newNode(7);
		root3.left.left.right = newNode(2);
		root3.right.right.left = newNode(5);
		root3.right.right.right = newNode(1);
		System.out.println(pathCount(root3, 22)); // 3
	}
}
