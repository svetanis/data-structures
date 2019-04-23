package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.depth;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LotRecursive {

  public static void traverse(Node root) {
    // time complexity: O(n^2)

    int height = depth(root);
    for (int i = 1; i <= height; i++) {
      givenLevel(root, i);
      System.out.println();
    }
  }

  private static void givenLevel(Node node, int k) {
    if (isNull(node)) {
      return;
    }
    if (k == 1) {
      System.out.print(node + " ");
    } else if (k > 1) {
      givenLevel(node.left, k - 1);
      givenLevel(node.right, k - 1);
    }
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);

    traverse(root);
    System.out.println();
  }
}
