package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Root-to-Leaf Path With a Given Sum -- the same problem as 112. Path Sum
//
// Input: the root of a binary tree and a number k. Returns true if some path from the root down
// to a leaf has values adding up to k. An empty tree has no path, so the answer is false.
// Assumes the values and k are small enough that k minus a sum does not overflow an int
// (LeetCode: 0 <= n <= 5000, -1000 <= val <= 1000, -1000 <= k <= 1000).
//
// The one idea: instead of adding values up on the way down, subtract each value from k. What
// is left is the sum the rest of the path must still reach. At a leaf, the path is complete
// when nothing is left (0). Otherwise ask both children with the smaller target; either may say
// yes. A node that is not a leaf is never accepted, even if the left-over is 0, because a path
// has to end at a leaf.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack (h = height, n on a chain).
//
// Siblings: RootToLeafPathIsGivenSum is the same test written three ways, with the LeetCode
// title; RootToLeafPathGivenSum returns the path itself.
public final class PathSum {

  public static boolean hasPathSum(TreeNode root, int k) {
    if (isNull(root)) { // BASE: no node, no path
      return false;
    }
    int sum = k - root.val; // LEFT-OVER: what the rest of the path must still add up to
    if (sum == 0 && isLeaf(root)) { // FOUND: the path ends here with nothing left over
      return true;
    }
    boolean isLeft = hasPathSum(root.left, sum); // ASK: left subtree
    boolean isRight = hasPathSum(root.right, sum); // ASK: right subtree
    return isLeft || isRight;
  }

  public static void main(String[] args) {
    // sum = 21 : 10->8->3
    // sum = 23 : 10->8->5
    // sum = 14 : 10->2->2
    TreeNode root = newNode(10);
    root.left = newNode(8);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.left.right = newNode(5);
    root.right.left = newNode(2);
    System.out.println(hasPathSum(root, 21)); // true
    System.out.println(hasPathSum(root, 25)); // false
  }
}
