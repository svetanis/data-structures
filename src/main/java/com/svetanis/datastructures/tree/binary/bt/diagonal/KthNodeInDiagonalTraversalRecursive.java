package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.IntWrapper;

// Given a binary tree and a value K. 
// Print the k-th node in the diagonal traversal of the binary tree. 
// If no such node exists then print -1.

public final class KthNodeInDiagonalTraversalRecursive {

  public static int traverse(Node root, int k) {
    IntWrapper count = newIntWrapper();
    return traverse(root, 0, k, count);
  }

  private static int traverse(Node root, int d, int k, IntWrapper count) {
    if (isNull(root) || count.value >= k) {
      return -1;
    }

    int left = traverse(root.left, d + 1, k, count);
    if (left != -1) {
      return left;
    }
    // increment count of visited nodes
    count.value++;
    // if c becomes k now,
    // then this is the k'th node
    if (count.value == k) {
      return root.data;
    }
    return traverse(root.right, d, k, count);
  }

  public static void main(String[] args) {
    Node root = newNode(8);
    root.left = newNode(3);
    root.right = newNode(10);
    root.left.left = newNode(1);

    root.right.left = newNode(6);
    root.right.right = newNode(14);

    root.right.left.left = newNode(4);
    root.right.left.right = newNode(7);

    root.right.right.left = newNode(13);
    System.out.println(traverse(root, 5));
  }
}
