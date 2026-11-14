package com.svetanis.datastructures.tree.binary.bt.path;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1372. Longest ZigZag Path in a Binary Tree
//
// Input: the root of a binary tree. A zigzag path picks a start node and a first direction
// (left or right), moves to that child, then switches direction at every step, and stops when
// the child it wants is missing. Returns the length of the longest zigzag path, counted in
// edges (a single node is 0). On LeetCode: 1 <= n <= 5 * 10^4, 1 <= val <= 100.
//
// The one idea: at every node keep two numbers. left = the length, in edges, of the longest
// zigzag path that ends at this node and whose last step went LEFT into it; right = the same for
// a path whose last step went RIGHT into it. A path that arrives by a left step must have been at
// the parent after a right step, so a left child gets left = the parent's right + 1, and its
// right is 0 (no path arrives at it by a right step). The right child is the mirror image:
// right = the parent's left + 1, left = 0. The answer is the largest left or right value seen at
// any node.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack (h = height, n on a chain).
//
// Known limit: a chain tens of thousands of nodes deep (the constraints allow 5 * 10^4)
// overflows the default thread stack, because the recursion is as deep as the tree. It only
// happens on very large input.
//
// Siblings: LongestZigZagPath1372Iterative is the same walk with an explicit stack.
public final class LongestZigZagPath1372 {

  public static int lzzp(TreeNode root) {
    return dfs(root, 0, 0, 0);
  }

  private static int dfs(TreeNode root, int left, int right, int max) {
    if (root == null) { // BASE: nothing below; hand back the best so far
      return max;
    }
    max = Math.max(max, Math.max(left, right)); // RECORD: the longest path ending here
    int leftLen = dfs(root.left, right + 1, 0, max); // WALK: left child extends a right arrival
    int rightLen = dfs(root.right, 0, left + 1, max); // WALK: right child extends a left arrival
    return Math.max(max, Math.max(leftLen, rightLen)); // RETURN: best of this node and below
  }

  public static void main(String[] args) {
    TreeNode root1 = new TreeNode(1);
    root1.right = new TreeNode(1);
    root1.right.left = new TreeNode(1);
    root1.right.right = new TreeNode(1);
    root1.right.right.left = new TreeNode(1);
    root1.right.right.right = new TreeNode(1);
    root1.right.right.left.right = new TreeNode(1);
    root1.right.right.left.right.right = new TreeNode(1);
    System.out.println(lzzp(root1)); // 3

    TreeNode root2 = new TreeNode(1);
    root2.left = new TreeNode(1);
    root2.right = new TreeNode(1);
    root2.left.right = new TreeNode(1);
    root2.left.right.left = new TreeNode(1);
    root2.left.right.right = new TreeNode(1);
    root2.left.right.left.right = new TreeNode(1);
    System.out.println(lzzp(root2)); // 4

    TreeNode root3 = new TreeNode(1);
    System.out.println(lzzp(root3)); // 0
  }
}
