package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isFull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 958. Check Completeness of a Binary Tree
//
// Input: the root of a binary tree. A tree is complete when every level except possibly the last is
// full, and the last level has its nodes as far left as possible. Returns true when the tree is
// complete (the empty tree is). On LeetCode: 1 <= number of nodes <= 100, 1 <= Node.val <= 1000.
//
// The one idea: read the nodes level by level, left to right. Once a node is found that is not
// full -- a leaf, or a node with only a left child -- every node read after it must be a leaf,
// because the last level has started and no node may have a child below it. A node with a right
// child but no left child is a gap on its own. So keep one flag, "a non-full node has been seen",
// and fail when a node arrives after the flag is up that is full or has a child.
//
// Time: O(n) -- each node enters the queue and leaves it once.
// Space: O(n) -- the queue holds about one level of nodes, which is O(n) for a wide tree.
//
// Siblings: BinaryTreeCompletenessBfs -- the same level-order reading, deciding by empty slots
// instead of by the children of each node. BinaryTreeCompletenessDfs and
// CompleteBinaryTreeRecursive number the nodes like an array instead.

public final class CompleteBinaryTreeLot {

  public static boolean isComplete(TreeNode root) {
    if (root == null) {
      return true; // BASE: the empty tree is complete
    }
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);

    // set true when a non-full node is seen
    boolean nonFull = false; // FLAG: from here on every node must be a leaf
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();

      if (isFull(node)) {
        if (nonFull) {
          return false; // MISMATCH: a node with two children after a non-full one
        }
        queue.offer(node.left);
        queue.offer(node.right);
      }

      if (isLeaf(node)) {
        nonFull = true; // FLAG: a leaf is not full, so only leaves may follow
      }

      if (isNotNull(node.left) && isNull(node.right)) {
        if (nonFull) {
          return false; // MISMATCH: a node with a child after a non-full one
        }
        nonFull = true; // FLAG: a left child alone ends the full nodes
        queue.add(node.left);
      }

      if (isNull(node.left) && isNotNull(node.right)) {
        return false; // MISMATCH: a right child with no left child is a gap
      }
    }
    return true;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    System.out.println(isComplete(root)); // false

    TreeNode root2 = newNode(1);
    root2.left = newNode(2);
    root2.left.left = newNode(4);
    System.out.println(isComplete(root2)); // false

    TreeNode root3 = newNode(1);
    root3.left = newNode(2);
    root3.right = newNode(3);
    root3.left.left = newNode(4);
    root3.left.right = newNode(5);
    System.out.println(isComplete(root3)); // true
  }
}
