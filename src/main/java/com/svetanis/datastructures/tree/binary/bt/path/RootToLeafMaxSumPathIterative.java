package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Integer.MIN_VALUE;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

// Root-to-Leaf Path With the Maximum Sum, without recursion -- not a LeetCode problem
//
// Input: the root of a binary tree with at least one node (an empty tree throws a
// NullPointerException). Returns the values of the root-to-leaf path with the largest sum, root
// first. Values may be negative. Assumes the sums fit in an int.
//
// The one idea: visit the nodes level by level with a queue, and remember for each node who its
// parent is, so a path can be read back from any leaf. One map entry holds "this node's parent".
// The map is keyed on the node object itself (identity), never on its value, because two
// different nodes can hold the same value and must keep their own parents. At each leaf, rebuild
// the path by following parents up to the root, add it up, and keep it if its sum beats the best
// so far.
//
// Time: O(n + L * h^2) -- each leaf (L of them) rebuilds a path of up to h nodes by inserting
//   at the front of a list, which moves up to h entries each time (h = height).
// Space: O(n) -- the queue and the parent map; the path is extra.
//
// Siblings: RootToLeafMaxSumPathRecursive and RootToLeafMaxSumPathRecursive2 do the same with
// recursion and no parent map. On a tie in the sum this version returns the shallowest leaf, and
// among leaves of the same depth the rightmost one (the right child is queued first and only a
// strictly larger sum replaces the best); the recursive versions return the leftmost.
public final class RootToLeafMaxSumPathIterative {

	private static List<Integer> maxSumPath(TreeNode root) {
		int max = MIN_VALUE; // BEST: smaller than any sum
		Deque<TreeNode> dq = new ArrayDeque<>();
		dq.add(root);
		Map<TreeNode, TreeNode> map = new IdentityHashMap<>(); // PARENT: node object -> its parent
		map.put(root, null);
		List<Integer> path = new ArrayList<>();
		while (!dq.isEmpty()) {
			TreeNode node = dq.poll();
			if (isLeaf(node)) {
				List<Integer> curr = path(node, map); // REBUILD: root to this leaf
				int sum = curr.stream().reduce(0, Integer::sum);
				if (sum > max) { // RECORD: a strictly larger sum
					max = sum;
					path.clear();
					path.addAll(curr);
				}
			}
			if (isNotNull(node.right)) { // QUEUE: right child
				dq.add(node.right);
				map.put(node.right, node);
			}
			if (isNotNull(node.left)) { // QUEUE: left child
				dq.add(node.left);
				map.put(node.left, node);
			}
		}
		return path;
	}

	private static List<Integer> path(TreeNode leaf, Map<TreeNode, TreeNode> map) {
		TreeNode node = leaf;
		List<Integer> list = new ArrayList<>();
		while (isNotNull(map.get(node))) { // WALK: up until the root, whose parent is null
			list.add(0, node.val);
			node = map.get(node);
		}
		list.add(0, node.val); // ROOT: the last node, not covered by the loop
		return list;
	}

	public static void main(String[] args) {
		// 21: 10->8->3
		// 23: 10->8->5
		// 14: 10->2->2
		TreeNode root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);
		inOrder(root); // prints 3 8 5 10 2 2
		System.out.println();

		// print Root-to-leaf path
		System.out.println("Root-to-leaf max sum path: ");
		System.out.println(maxSumPath(root)); // [10, 8, 5]

		// two leaves holding the same value under different parents.
		// The parent map is keyed on node identity, so the two leaves are
		// two entries, each with its own parent, and the heavier branch wins.
		//  9: 1->3->5
		// 26: 1->20->5   <-- the answer
		TreeNode dup = newNode(1);
		dup.left = newNode(3);
		dup.right = newNode(20);
		dup.left.left = newNode(5);
		dup.right.left = newNode(5);
		System.out.println("Two leaves of equal value, max sum path: ");
		System.out.println(maxSumPath(dup)); // [1, 20, 5]
	}
}
