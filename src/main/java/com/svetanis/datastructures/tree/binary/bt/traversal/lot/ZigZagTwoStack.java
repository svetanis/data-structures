package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 103. Binary Tree Zigzag Level Order Traversal
//
// Input: root of a binary tree. Returns the values level by level as a list of lists: the first
// level left to right, the second right to left, and so on, alternating. On LeetCode:
// 0 <= number of nodes <= 2000, -100 <= node value <= 100.
//
// The one idea: a stack hands things back last-in first, so the children pushed in one order come
// out in the opposite order. Two stacks: curr holds the level being read, next collects the level
// below. Popping curr reads the level in whatever order it was pushed; each popped node pushes its
// children on next, and the order of those two pushes decides how the next level will be read.
// Pushing left then right makes the next level pop right to left; pushing right then left makes it
// pop left to right. leftToRight says how the level being read is read, so a left-to-right level
// pushes left then right (the next level pops right to left), and the other kind pushes right then
// left. When curr is empty the level is complete: its list is stored, the flag is flipped and the
// two stacks swap roles.
//
// Time: O(n) -- each node is pushed and popped once.
// Space: O(n) -- the output; each stack holds at most one level.
//
// Siblings: ZigZagQueue gets the same lists from a queue and front inserts; ZigZagRecursive walks
// the tree once per level; ZigZagStackQueue returns one flat list.

public final class ZigZagTwoStack {

	public static List<List<Integer>> traverse(TreeNode root) {
		List<List<Integer>> lists = new ArrayList<>();
		if (root == null) {
			return lists;
		}
		boolean leftToRight = true; // DIRECTION: of the level being read
		Deque<TreeNode> curr = new ArrayDeque<>(); // STACK: the level being read
		Deque<TreeNode> next = new ArrayDeque<>(); // STACK: the level below, being filled
		curr.push(root);
		List<Integer> list = new ArrayList<>();
		while (!curr.isEmpty()) {
			TreeNode node = curr.pop(); // TAKE: the next node of this level
			list.add(node.val); // RECORD: its value
			if (leftToRight) { // PUSH: left then right, so the next level pops right to left
				if (node.left != null) {
					next.push(node.left);
				}
				if (node.right != null) {
					next.push(node.right);
				}
			} else { // PUSH: right then left, so the next level pops left to right
				if (node.right != null) {
					next.push(node.right);
				}
				if (node.left != null) {
					next.push(node.left);
				}
			}

			if (curr.isEmpty()) { // LEVEL DONE: store it and move down
				lists.add(new ArrayList<>(list));
				list.clear();
				leftToRight = !leftToRight; // FLIP: the next level goes the other way
				Deque<TreeNode> temp = curr; // SWAP: the filled stack becomes the one being read
				curr = next;
				next = temp;
			}
		}
		return lists;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(7);
		root.left.right = newNode(6);
		root.right.left = newNode(5);
		root.right.right = newNode(4);
		System.out.println(traverse(root)); // [[1], [3, 2], [7, 6, 5, 4]]
	}
}
