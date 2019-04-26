package com.svetanis.datastructures.tree.binary.bst;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class KthLargest {

  public static Optional<Integer> kthLargest(Node root, int k) {

    if (isNull(root)) {
      return absent();
    }
    int right = size(root.right);
    if (k == right + 1) {
      return of(root.data);
    } else if (right < k) {
      return kthLargest(root.left, k - right - 1);
    } else {
      return kthLargest(root.right, k);
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
    System.out.println(kthLargest(root, 5));
  }
}