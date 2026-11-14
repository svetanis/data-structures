package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Diagonal Traversal of a Binary Tree
//
// Input: the root of a binary tree. Draw it so that a left child hangs one step down-left of its
// parent and a right child stays on the parent's diagonal line. Returns the node values grouped
// by diagonal, the diagonal through the root first; inside a diagonal, nodes come in the order
// they are met walking down from the root and then right.
//
// The one idea: a node's diagonal number is the number of left steps on the way from the root.
// Going to the right child keeps the number; going to the left child adds one. A preorder walk
// carries that number and appends each node's value to list number in the result. A node sits at
// most one diagonal past the deepest one seen so far (its parent was already seen), so a new
// list is always the next free slot.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack, h is the height of the tree, plus the O(n) result.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of tens of thousands of
// nodes overflows the default stack; DiagonalTraversalIterative is the loop version.
//
// Siblings: DiagonalTraversalIterative gets the same lists with a queue and a loop along each
// right chain.

public final class DiagonalTraversalRecursive {

  public static List<List<Integer>> traverse(TreeNode root) {
    List<List<Integer>> lists = new ArrayList<>();
    traverse(root, 0, lists);
    return lists;
  }

  private static void traverse(TreeNode root, int d, List<List<Integer>> lists) {
    if (isNull(root)) {
      return;
    }
    if (d == lists.size()) {
      lists.add(new ArrayList<>()); // OPEN: the first node of a new diagonal
    }
    lists.get(d).add(root.val); // TAKE this node into its diagonal
    traverse(root.left, d + 1, lists); // WALK: left child, next diagonal
    traverse(root.right, d, lists); // WALK: right child, same diagonal
  }

  public static void main(String[] args) {
    TreeNode root = newNode(8);
    root.left = newNode(3);
    root.right = newNode(10);
    root.left.left = newNode(1);
    root.left.right = newNode(6);
    root.right.right = newNode(14);
    root.left.right.left = newNode(4);
    root.left.right.right = newNode(7);
    root.right.right.left = newNode(13);
    System.out.println(traverse(root)); // [[8, 10, 14], [3, 6, 7, 13], [1, 4]]
    System.out.println(traverse(null)); // []
  }
}
