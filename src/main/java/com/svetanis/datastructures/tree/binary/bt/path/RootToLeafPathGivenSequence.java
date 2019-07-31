package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree and a number sequence, 
// find if the sequence is present as a root-to-leaf 
// path in the given tree.

public final class RootToLeafPathGivenSequence {

  public static boolean path(Node root, int[] a) {
    // Time complexity: O(n)

    if (isNull(root)) {
      return a.length == 0;
    }
    return path(root, a, 0);
  }

  private static boolean path(Node root, int[] a, int i) {

    if (isNull(root)) {
      return false;
    }

    if (i >= a.length || root.data != a[i]) {
      return false;
    }

    if (isLeaf(root) && i == a.length - 1) {
      return true;
    }

    return path(root.left, a, i + 1) || path(root.right, a, i + 1);
  }

  public static void main(String[] args) {

    Node root = newNode(1);
    root.left = newNode(0);
    root.right = newNode(1);
    root.left.left = newNode(1);
    root.right.left = newNode(6);
    root.right.right = newNode(5);

    int[] a1 = { 1, 0, 7 };
    System.out.println(path(root, a1));

    int[] a2 = { 1, 1, 6 };
    System.out.println(path(root, a2));
  }
}
