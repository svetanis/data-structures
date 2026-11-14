package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Delete a binary tree, level by level
//
// Input: root of a binary tree (not a LeetCode problem). Returns null, to be assigned to the
// caller's root variable: root = delete(root).
//
// The one idea: in Java a tree is gone once nothing refers to it, so "delete" means letting go of
// the root. This version walks the tree in level order and lets go of each node as it is taken
// off the queue. It does not clear any left or right link, so a caller that kept its own
// reference to the old root still sees the whole tree; the walk changes nothing in the tree and
// the null return does the work. DeleteRecursive does clear the links.
//
// Time: O(n) -- every node is queued and taken off once.
// Space: O(w) -- the queue holds one level at a time, w = the widest level (up to n / 2).
//
// Siblings: DeleteRecursive visits in post-order (children before the node) and sets every link to
// null, so the tree really is taken apart; this one only returns null.

public final class DeleteIterative {

  // ITERATIVE: LEVEL ORDER TRAVERSAL
  public static TreeNode delete(TreeNode root) {
    // Base case
    if (isNull(root)) {
      return null;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      root = queue.poll(); // TAKE: the next node in level order
      if (isNotNull(root.left)) {
        queue.offer(root.left); // QUEUE: its children
      }
      if (isNotNull(root.right)) {
        queue.offer(root.right);
      }
      root = null; // RELEASE: only the local variable; the links are untouched
    }
    return root; // RETURN: null, by the last line of the loop
  }

  public static void main(String[] args) {

    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);

    inOrder(root); // 4 2 5 1 3
    System.out.println();
    root = delete(root);
    inOrder(root); // prints nothing: the tree is gone
    System.out.println();

    TreeNode tree = newNode(15);
    tree.left = newNode(10);
    tree.right = newNode(20);
    tree.left.left = newNode(8);
    tree.left.right = newNode(12);
    tree.right.left = newNode(16);
    tree.right.right = newNode(25);

    inOrder(tree); // 8 10 12 15 16 20 25
    System.out.println();
    tree = delete(tree);
    inOrder(tree); // prints nothing: the tree is gone
    System.out.println();
  }
}
