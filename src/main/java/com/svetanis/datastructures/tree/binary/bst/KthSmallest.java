package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class KthSmallest {

  public static int kthSmallest(Node root, int k) {

    // recursive for BST only.
    if (root == null) {
      return -1;
    }
    int left = size(root.left);
    if (k == left + 1) {
      return root.data;
    } else if (left < k) {
      return kthSmallest(root.right, k - left - 1);
    } else {
      return kthSmallest(root.left, k);
    }
  }

  public static void main(String[] args) {
    Node root = newNode(20);
    root.left = newNode(8);
    root.right = newNode(22);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    System.out.println(kthSmallest(root, 3));
  }
}