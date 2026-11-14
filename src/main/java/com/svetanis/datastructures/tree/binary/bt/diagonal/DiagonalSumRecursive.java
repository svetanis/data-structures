package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Diagonal Sum of a Binary Tree
//
// Input: the root of a binary tree. Draw it so that a left child hangs one step down-left of its
// parent and a right child stays on the parent's diagonal line. Returns the sum of the node
// values on each diagonal, the first diagonal (the one through the root) first.
//
// The one idea: a node's diagonal number is the number of left steps on the way from the root.
// Going to the right child keeps the number; going to the left child adds one. A preorder walk
// carries that number and adds each node's value to slot number in the result list. A node sits
// at most one diagonal past the deepest one seen so far (its parent was already seen), so a new
// slot is always the next free one.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack, h is the height of the tree, plus the O(d) result.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of tens of thousands of
// nodes overflows the default stack; DiagonalSumIterative is the loop version.
//
// Siblings: DiagonalSumIterative does the same with a queue and a loop along each right chain.

public final class DiagonalSumRecursive {

  public static List<Integer> diagonalSum(TreeNode root) {
    List<Integer> sums = new ArrayList<>();
    diagonalSum(root, 0, sums);
    return sums;
  }

  private static void diagonalSum(TreeNode root, int diagonal, List<Integer> sums) {

    if (isNull(root)) {
      return;
    }
    if (diagonal == sums.size()) {
      sums.add(0); // OPEN: the first node of a new diagonal
    }
    sums.set(diagonal, sums.get(diagonal) + root.val); // ADD to this diagonal's sum
    diagonalSum(root.left, diagonal + 1, sums); // WALK: left child, next diagonal
    diagonalSum(root.right, diagonal, sums); // WALK: right child, same diagonal
  }

  public static void main(String[] args) {
    // 9 is sum of 1, 3 and 5.
    // 19 is sum of 2, 6, 4 and 7.
    // 42 is sum of 9, 10, 11 and 12.

    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(9);
    root.left.right = newNode(6);
    root.right.left = newNode(4);
    root.right.right = newNode(5);
    root.left.left.right = newNode(10);
    root.left.right.left = newNode(11);
    root.right.left.left = newNode(12);
    root.right.left.right = newNode(7);
    System.out.println(diagonalSum(root)); // [9, 19, 42]
    System.out.println(diagonalSum(null)); // []
  }
}
