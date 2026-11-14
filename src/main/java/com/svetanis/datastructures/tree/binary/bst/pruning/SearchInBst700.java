package com.svetanis.datastructures.tree.binary.bst.pruning;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 700. Search in a Binary Search Tree
//
// Input: root -- the root of a binary search tree with distinct values; target -- the value to
// find. Returns the node holding target, whose subtree is then the answer, or null when no node
// holds it. On LeetCode: 1 <= number of nodes <= 5000, 1 <= Node.val <= 10^7, all values
// distinct, 1 <= target <= 10^7.
//
// The one idea: one node holds one value, and everything left of it is smaller, everything right
// of it larger. So one comparison with the node's value discards a whole side: a smaller target
// cannot be on the right, a larger one cannot be on the left. Walk down one path, never two, and
// either meet the target or fall off the tree.
//
// Time: O(h) -- one root-to-leaf path, never a whole level; h is the tree's height, up to n on
// a chain.
// Space: search O(1); dfs O(h) for the call stack.
//
// Siblings: search and dfs below do the same walk, one with a loop, one with recursion. The
// recursive call is the last thing dfs does, so the loop carries the same information in no
// frames at all.

public final class SearchInBst700 {

  public static TreeNode search(TreeNode root, int target) {
    while (root != null) {
      if (root.val == target) {
        return root; // RETURN: found
      } else if (root.val > target) {
        root = root.left; // WALK left: target is smaller
      } else { // root.val < target
        root = root.right; // WALK right: target is larger
      }
    }
    return null; // RETURN: fell off the tree, target is absent
  }

  public static TreeNode dfs(TreeNode root, int target) {
    if (root == null || root.val == target) {
      return root; // BASE: found, or fell off the tree
    }
    if (root.val < target) {
      return dfs(root.right, target); // WALK right: target is larger
    } else {
      return dfs(root.left, target); // WALK left: target is smaller
    }
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(4);
    root.left = new TreeNode(2);
    root.right = new TreeNode(7);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(3);
    Nodes.preOrder(dfs(root, 2)); // 2 1 3
    System.out.println();
    Nodes.preOrder(search(root, 5)); // prints nothing: 5 is not in the tree
    System.out.println();
  }
}
