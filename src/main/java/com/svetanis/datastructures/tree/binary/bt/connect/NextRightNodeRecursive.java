package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.IntWrapper;

// given a binary tree and a node in it, write an efficient algorithm
// to find its next node in same level as given node

public final class NextRightNodeRecursive {

  public static Optional<Node> nextRight(Node root, int k) {
    IntWrapper i = newIntWrapper();
    Node nextRight = nextRight(root, k, 1, i);
    return isNull(nextRight) ? absent() : of(nextRight);
  }

  private static Node nextRight(Node root, int k, int level, IntWrapper i) {
    // Time complexity: O(n)

    if (isNull(root)) {
      return null;
    }

    if (root.data == k) {
      i.value = level;
      return null;
    } else if (i.value != 0) {
      if (i.value == level) {
        return root;
      }
    }

    Node left = nextRight(root.left, k, level + 1, i);
    if (left != null) {
      return left;
    }
    return nextRight(root.right, k, level + 1, i);
  }

  public static void main(String[] args) {
    Node root = new Node(10);
    root.left = new Node(2);
    root.right = new Node(6);
    root.left.left = new Node(8);
    root.left.right = new Node(4);
    root.right.right = new Node(5);

    System.out.println(nextRight(root, 10)); // absent
    System.out.println(nextRight(root, 2)); // 6
    System.out.println(nextRight(root, 6)); // absent
    System.out.println(nextRight(root, 5)); // absent
    System.out.println(nextRight(root, 8)); // 4
    System.out.println(nextRight(root, 4)); // 5
  }
}
