package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// are two given nodes siblings -- do they share the same parent?
// nothing is compared by value: the check is whether one node is a
// parent's left child while the other is that same parent's right child.
// cousins/Cousins.java is the harder question -- same depth, DIFFERENT
// parents.

public final class Siblings {

  public static boolean siblings(Node root, Node a, Node b) {

    if (isNull(root)) {
      return false;
    }

    boolean one = root.left == a && root.right == b;
    boolean two = root.left == b && root.right == a;
    if (one || two) {
      return true;
    }

    boolean left = siblings(root.left, a, b);
    boolean right = siblings(root.right, a, b);
    return left || right;
  }

  public static void main(String[] args) {
    Node root = newNode(6);
    root.left = newNode(3);
    root.right = newNode(5);
    root.left.left = newNode(7);
    root.left.right = newNode(8);
    root.right.left = newNode(1);
    root.right.right = newNode(3);

    Node a = root.left.left; // 7
    Node b = root.right.right; // 3

    System.out.println(siblings(root, a, b));
  }
}
