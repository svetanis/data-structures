package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isAbsent;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LcaBtNoParentBottomUp {

  // without pointer to parent; Bottom Up;
  // No guarantee that p or q exist in the tree. 
  // If one value doesn’t exist in the tree then return -1.

  public static Optional<Integer> lca(Node root, int p, int q) {
    if (isNull(root) || isAbsent(root, p) || isAbsent(root, q)) {
      return absent();
    }
    return of(lcaRecursive(root, p, q));
  }

  private static int lcaRecursive(Node root, int p, int q) {
    // Time complexity: O(n)

    if (isNull(root)) {
      return -1;
    }

    if (root.data == p || root.data == q) {
      return root.data;
    }

    int left = lcaRecursive(root.left, p, q);
    int right = lcaRecursive(root.right, p, q);

    // if p and q are on both sides
    if (left != -1 && right != -1) {
      return root.data;
    }

    // either one of p, q is on one side
    // or p, q is not in left and right subtrees
    if (left != -1) {
      return left;
    } else {
      return right;
    }
  }

  public static void main(String[] args) {
    Node root = newNode(3);
    root.left = newNode(5);
    root.right = newNode(1);
    root.left.left = newNode(6);
    root.left.right = newNode(2);
    root.left.right.left = newNode(7);
    root.left.right.right = newNode(4);
    root.right.left = newNode(0);
    root.right.right = newNode(8);
    inOrder(root);
    System.out.println();
    System.out.println(lca(root, 5, 1));
    System.out.println(lca(root, 6, 4));
    System.out.println(lca(root, 4, 10));
  }
}
