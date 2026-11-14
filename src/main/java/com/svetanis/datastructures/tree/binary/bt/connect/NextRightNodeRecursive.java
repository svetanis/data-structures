package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Optional;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Next Right Node of a Given Key (not a LeetCode problem)
//
// Input: root -- the root of a binary tree, possibly null; k -- the key to look for. Keys are
// assumed distinct: with repeated keys this file answers for the first k in preorder, while
// NextRightNodeIterative answers for the first k in level order. Returns the node immediately to
// the right of the node holding k on the same level, or empty when k is the last node of its level
// or is not in the tree.
//
// The one idea: a preorder walk reaches the nodes of one level in left to right order. So when the
// node holding k is found, write down its level and stop going below it; from then on, the first
// node reached at that same level is its right neighbour, and it is passed back up. Nothing is
// returned for a node on any other level, and nothing is returned at all when k is the last node of
// its level or is absent.
//
// Time: O(n) -- each node is visited at most once.
// Space: O(h) -- the call stack and the one remembered level; h is lg n on a balanced tree and n on
// a skewed one.
//
// Siblings: NextRightNodeIterative takes the tree level by level with a queue (O(n) space).

public final class NextRightNodeRecursive {

  public static Optional<TreeNode> nextRight(TreeNode root, int k) {
    int[] foundLevel = { 0 }; // LEVEL: level of k once seen, 0 until then
    TreeNode nextRight = nextRight(root, k, 1, foundLevel);
    return isNull(nextRight) ? Optional.empty() : Optional.of(nextRight);
  }

  private static TreeNode nextRight(TreeNode root, int k, int level, int[] foundLevel) {
    if (isNull(root)) { // BASE: nothing here
      return null;
    }

    if (root.val == k) { // FOUND: remember its level, do not go below it
      foundLevel[0] = level;
      return null;
    } else if (foundLevel[0] != 0) {
      if (foundLevel[0] == level) { // ANSWER: first node on k's level after k
        return root;
      }
    }

    TreeNode left = nextRight(root.left, k, level + 1, foundLevel); // LEFT first: preorder order
    if (left != null) {
      return left;
    }
    return nextRight(root.right, k, level + 1, foundLevel);
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(2);
    root.right = new TreeNode(6);
    root.left.left = new TreeNode(8);
    root.left.right = new TreeNode(4);
    root.right.right = new TreeNode(5);

    System.out.println(nextRight(root, 10)); // Optional.empty
    System.out.println(nextRight(root, 2)); // Optional[6]
    System.out.println(nextRight(root, 6)); // Optional.empty
    System.out.println(nextRight(root, 5)); // Optional.empty
    System.out.println(nextRight(root, 8)); // Optional[4]
    System.out.println(nextRight(root, 4)); // Optional[5]
  }
}
