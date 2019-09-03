package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class Sublings {

  public static boolean sublings(Node root, Node a, Node b) {

    if (isNull(root)) {
      return false;
    }

    boolean one = root.left == a && root.right == b;
    boolean two = root.left == b && root.right == a;
    if (one || two) {
      return true;
    }

    boolean left = sublings(root.left, a, b);
    boolean right = sublings(root.right, a, b);
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

    System.out.println(sublings(root, a, b));
  }
}
