package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// K-th Node in the Diagonal Traversal of a Binary Tree
//
// Input: the root of a binary tree and k, counted from 1. Draw the tree so that a left child
// hangs one step down-left of its parent and a right child stays on the parent's diagonal line.
// The diagonal traversal lists the diagonal through the root first, then the next one, and so on;
// inside a diagonal, nodes come in the order they are met walking down from the root and then
// right. Returns the value of the k-th node in that list, or -1 if the tree has fewer than k
// nodes (or k < 1).
//
// The one idea: the k-th node of the traversal is the k-th value of all the diagonals joined
// end to end. DiagonalTraversalRecursive already builds the diagonals as lists, so count off
// down them: if k fits inside the first diagonal, the answer is its k-th value; otherwise take
// that diagonal's size off k and look in the next.
//
// Time: O(n) -- the diagonals are built in one walk, and counting off visits each at most once.
// Space: O(n) -- the diagonal lists.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of tens of thousands of
// nodes overflows the default stack.
//
// Siblings: DiagonalTraversalRecursive builds the lists this file reads;
// DiagonalTraversalIterative builds them with a queue.

public final class KthNodeInDiagonalTraversalRecursive {

  public static int traverse(TreeNode root, int k) {
    if (k < 1) {
      return -1; // BASE: counting starts at 1
    }
    int remaining = k;
    for (List<Integer> diagonal : DiagonalTraversalRecursive.traverse(root)) {
      if (remaining <= diagonal.size()) {
        return diagonal.get(remaining - 1); // RETURN: the k-th falls inside this diagonal
      }
      remaining -= diagonal.size(); // SKIP: the whole diagonal comes before the k-th
    }
    return -1; // RETURN: fewer than k nodes
  }

  public static void main(String[] args) {
    TreeNode root = newNode(8);
    root.left = newNode(3);
    root.right = newNode(10);
    root.left.left = newNode(1);

    root.right.left = newNode(6);
    root.right.right = newNode(14);

    root.right.left.left = newNode(4);
    root.right.left.right = newNode(7);

    root.right.right.left = newNode(13);
    // diagonals: [8, 10, 14] [3, 6, 7, 13] [1, 4]
    System.out.println(traverse(root, 5)); // 6
  }
}
