package com.svetanis.datastructures.tree.binary.bt.compare;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 100. Same Tree
//
// Input: the roots of two binary trees; either may be empty. On LeetCode: 0 <= nodes <= 100 in each
// tree, -10^4 <= Node.val <= 10^4. Returns true when the two trees have the same shape and the
// same value at every position.
//
// The one idea: walk the two trees in step. One entry of the queue is a pair of nodes that sit at
// the same position -- the same path of lefts and rights from the root -- one in each tree. A pair
// passes when the two values are equal and each side has a left child exactly when the other does,
// and the same for the right child; the children are then queued as new pairs. The trees are equal
// when every pair passes, and the first pair that fails answers false at once.
//
// Time: O(n) -- each pair is queued and checked once; n is the size of the smaller tree, since a
// pair is never made when only one side has a child.
// Space: O(w) -- the queue holds at most one level of pairs; w is the widest level, up to n / 2.
//
// Siblings: IdenticalBinaryTreesRecursive does the same check by recursion, with the call stack in
// place of the queue. IdenticalStructureBinaryTrees compares the shape only, not the values.

public final class IdenticalBinaryTreesIterative {

  public static boolean identical(TreeNode root1, TreeNode root2) {
    if (isNull(root1) && isNull(root2)) {
      return true; // BASE: both empty
    }
    if (isNull(root1) || isNull(root2)) {
      return false; // BASE: one empty, one not
    }
    // both non-empty, compare them:
    Deque<TreeNode[]> queue = new ArrayDeque<>(); // PAIRS: two nodes at the same position, one per tree
    queue.add(new TreeNode[] { root1, root2 });
    while (!queue.isEmpty()) {
      TreeNode[] pair = queue.poll(); // TAKE: the oldest pair, so the trees are walked level by level
      TreeNode node1 = pair[0];
      TreeNode node2 = pair[1];

      if (node1.val != node2.val) {
        return false; // COMPARE: same position, different value
      }

      if (isNotNull(node1.left) && isNotNull(node2.left)) {
        queue.add(new TreeNode[] { node1.left, node2.left }); // QUEUE: the left pair, checked later
      } else if (isNotNull(node1.left) || isNotNull(node2.left)) {
        return false; // SHAPE: only one tree has a left child
      }

      if (isNotNull(node1.right) && isNotNull(node2.right)) {
        queue.add(new TreeNode[] { node1.right, node2.right }); // QUEUE: the right pair, checked later
      } else if (isNotNull(node1.right) || isNotNull(node2.right)) {
        return false; // SHAPE: only one tree has a right child
      }
    }
    return true; // RETURN: every pair passed
  }

  public static void main(String[] args) {
    TreeNode tree1 = newNode(26);
    tree1.right = newNode(3);
    tree1.right.right = newNode(3);
    tree1.left = newNode(10);
    tree1.left.left = newNode(4);
    tree1.left.left.right = newNode(30);
    tree1.left.right = newNode(6);

    TreeNode tree2 = newNode(10);
    tree2.left = newNode(4);
    tree2.right = newNode(6);
    tree2.left.right = newNode(30);

    System.out.println(identical(tree1, tree2)); // false

    TreeNode root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);

    TreeNode root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.left = newNode(4);
    root2.left.right = newNode(5);

    System.out.println(identical(root1, root2)); // true
  }
}
