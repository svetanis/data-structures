package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 742. Closest Leaf in a Binary Tree
//
// Input: root -- the root of a binary tree whose node values are all different; k -- the value of
// one node. A leaf is a node with no children. Returns the value of the leaf nearest to the node
// with value k, counting edges in the tree, going up to a parent as well as down to a child; if k
// is itself a leaf, the answer is k. On LeetCode: 1 <= number of nodes <= 1000, 1 <= Node.val <= 1000,
// all values unique, a node with value k exists. If two leaves tie, the one reached first is
// returned. (The method is called amountOfTime, a leftover name; it answers this problem.)
//
// The one idea: a tree node only knows its children, but the nearest leaf may lie above k, so first
// turn the tree into a graph that can be walked both ways: map each node to its parent and its
// children. Then walk that graph outward from k, one edge further each round. One queue entry is a
// node at the current distance from k. The queue serves the nearest first, so the first leaf taken
// off it is the nearest leaf, and the search stops there. The map is keyed by the node object, not
// its value; visited is kept by value, which is safe because the values are unique.
//
// Time: O(n) -- the graph is built in one pass, and the walk queues each node once.
// Space: O(n) -- the map, the visited set and the queue.

public final class ClosestLeafInBT {

	private Map<TreeNode, List<TreeNode>> map = new IdentityHashMap<>(); // GRAPH: a node -> its parent and children (the root's parent is null)

	public int amountOfTime(TreeNode root, int k) {
		buildGraph(root, null); // BUILD: the tree as a two-way graph
		Deque<TreeNode> dq = init(k); // START: the node whose value is k
		Set<Integer> visited = new HashSet<>(); // VISITED: by value; values are unique

		while (!dq.isEmpty()) {
			TreeNode node = dq.poll(); // TAKE: the nearest node not yet taken
			visited.add(node.val); // MARK
			if (node.left == null && node.right == null) { // BASE: a leaf; the first one taken is the nearest
				return node.val; // RETURN
			}
			for (TreeNode neighbor : map.get(node)) { // SPREAD: one edge further out
				if (neighbor != null && !visited.contains(neighbor.val)) { // SKIP: no parent at the root, or a node already taken
					dq.offer(neighbor);
				}
			}
		}
		return 0; // RETURN: only if no node has value k
	}

	private Deque<TreeNode> init(int k) {
		Deque<TreeNode> dq = new ArrayDeque<>();
		for (TreeNode node : map.keySet()) { // FIND: the node whose value is k
			if (node != null && node.val == k) {
				dq.offer(node);
				break;
			}
		}
		return dq;
	}

	private void buildGraph(TreeNode root, TreeNode parent) {
		if (root != null) { // BASE: nothing to do for an empty subtree
			map.computeIfAbsent(root, k -> new ArrayList<>()).add(parent); // RECORD: root knows its parent
			map.computeIfAbsent(parent, k -> new ArrayList<>()).add(root); // RECORD: and the parent knows root
			buildGraph(root.left, root); // WALK
			buildGraph(root.right, root); // WALK
		}
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(6);
		root.right.left.left = new TreeNode(7);
		ClosestLeafInBT aot = new ClosestLeafInBT();
		System.out.println(aot.amountOfTime(root, 2)); // 4
		System.out.println(new ClosestLeafInBT().amountOfTime(new TreeNode(1), 1)); // 1
		TreeNode r3 = new TreeNode(1);
		r3.left = new TreeNode(2);
		r3.right = new TreeNode(3);
		r3.left.left = new TreeNode(4);
		r3.left.left.left = new TreeNode(5);
		r3.left.left.left.left = new TreeNode(6);
		System.out.println(new ClosestLeafInBT().amountOfTime(r3, 2)); // 3
	}
}
