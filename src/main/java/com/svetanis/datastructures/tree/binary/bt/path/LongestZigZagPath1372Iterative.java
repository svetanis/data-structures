package com.svetanis.datastructures.tree.binary.bt.path;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;

// 1372. Longest ZigZag Path in a Binary Tree
//
// Input: the root of a binary tree. A zigzag path picks a start node and a first direction
// (left or right), moves to that child, then switches direction at every step, and stops when
// the child it wants is missing. Returns the length of the longest zigzag path, counted in
// edges (a single node is 0). On LeetCode: 1 <= n <= 5 * 10^4, 1 <= val <= 100.
//
// The one idea: at every node keep two numbers. left = the length, in edges, of the longest
// zigzag path that ends at this node and whose last step went LEFT into it; right = the same for
// a path whose last step went RIGHT into it. A left child gets left = the parent's right + 1 and
// right = 0; a right child gets right = the parent's left + 1 and left = 0. The answer is the
// largest left or right value seen at any node. One stack entry holds a node with its two
// numbers, so the walk needs no recursion and no return value: every entry only updates the best.
//
// Time: O(n) -- each node is pushed and popped once.
// Space: O(h) -- the stack holds at most one pending sibling per level (h = height).
//
// Siblings: LongestZigZagPath1372 is the recursive version; it overflows the default stack on a
// chain tens of thousands of nodes deep, this one does not.
public final class LongestZigZagPath1372Iterative {

  private static final class Entry {
    private final TreeNode node;
    private final int left;
    private final int right;

    private Entry(TreeNode node, int left, int right) {
      this.node = node;
      this.left = left;
      this.right = right;
    }
  }

  public static int lzzp(TreeNode root) {
    int best = 0;
    Deque<Entry> stack = new ArrayDeque<>();
    if (root != null) {
      stack.push(new Entry(root, 0, 0)); // START: the root has no arrival step
    }
    while (!stack.isEmpty()) {
      Entry e = stack.pop();
      best = Math.max(best, Math.max(e.left, e.right)); // RECORD: the longest path ending here
      if (e.node.left != null) { // WALK: left child extends a right arrival
        stack.push(new Entry(e.node.left, e.right + 1, 0));
      }
      if (e.node.right != null) { // WALK: right child extends a left arrival
        stack.push(new Entry(e.node.right, 0, e.left + 1));
      }
    }
    return best;
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
