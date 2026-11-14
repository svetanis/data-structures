package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;

// 1457. Pseudo-Palindromic Paths in a Binary Tree
//
// Input: the root of a binary tree whose values are digits 1 to 9. A root-to-leaf path is
// pseudo-palindromic if its values can be rearranged into a palindrome. Returns how many
// root-to-leaf paths are. On LeetCode: 1 <= n <= 10^5, 1 <= val <= 9.
//
// The one idea: a list of digits can be rearranged into a palindrome exactly when at most one
// digit occurs an odd number of times. The walk is the one in CountPseudoPalindromicPaths, with
// the recursion replaced by a stack. One stack entry is a node plus a flag: "false" means the
// node is about to be entered (add its digit to the counter, look at the leaf, push the
// children); "true" means its children are done (take its digit out of the counter again).
//
// Time: O(n) -- each node is pushed twice and popped twice; each leaf looks at 9 counters.
// Space: O(h) -- the stack holds at most the path from the root plus one pending sibling per
//   level (h = height, n on a chain).
//
// Siblings: CountPseudoPalindromicPaths is the recursive version; it overflows the default stack
// on a chain tens of thousands of nodes deep, this one does not.
public final class CountPseudoPalindromicPathsIterative {

	public static int pathCount(TreeNode root) {
		if (root == null) { // BASE: no tree, no paths
			return 0;
		}
		int[] counter = new int[10]; // COUNTER: counter[d] = times digit d is on the current path
		Deque<TreeNode> nodes = new ArrayDeque<>();
		Deque<Boolean> leaving = new ArrayDeque<>(); // FLAG: true = the node's children are done
		nodes.push(root);
		leaving.push(false);
		int count = 0;
		while (!nodes.isEmpty()) {
			TreeNode node = nodes.pop();
			boolean done = leaving.pop();
			if (done) { // UNDO: leave the node
				counter[node.val]--;
				continue;
			}
			counter[node.val]++; // TAKE: the current node's digit joins the path
			if (isLeaf(node)) {
				if (pseudoPalindrome(counter)) { // RECORD: the whole path is known at a leaf
					count++;
				}
				counter[node.val]--; // UNDO: a leaf has no children to wait for
				continue;
			}
			nodes.push(node); // LEAVE: come back here after both children
			leaving.push(true);
			if (node.right != null) { // WALK: right subtree (pushed first, so it is visited second)
				nodes.push(node.right);
				leaving.push(false);
			}
			if (node.left != null) { // WALK: left subtree
				nodes.push(node.left);
				leaving.push(false);
			}
		}
		return count;
	}

	private static boolean pseudoPalindrome(int[] counter) {
		int odd = 0;
		for (int i = 1; i <= 9; i++) {
			if (counter[i] % 2 == 1) { // ODD: this digit has no partner
				odd++;
			}
		}
		return odd == 0 || odd == 1; // RETURN: at most one digit may stand alone, in the middle
	}

	public static void main(String[] args) {
		TreeNode root = newNode(2);
		root.left = newNode(3);
		root.right = newNode(1);
		root.left.left = newNode(3);
		root.left.right = newNode(1);
		root.right.right = newNode(1);
		System.out.println(pathCount(root)); // 2

		TreeNode root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(1);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.left.right.right = newNode(1);
		System.out.println(pathCount(root2)); // 1

		TreeNode root3 = newNode(9);
		System.out.println(pathCount(root3)); // 1
	}
}
