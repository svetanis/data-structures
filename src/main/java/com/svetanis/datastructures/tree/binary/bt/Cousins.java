package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.bt.Level.level;
import static com.svetanis.datastructures.tree.binary.bt.Sublings.sublings;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class Cousins {

  public static boolean cousins(Node root, Node a, Node b) {
    // Time Complexity: O(n)

    // 1. two nodes should be on the same level in BT
    // 2. two nodes should not be sublings that is
    // they should not have the same parent node

    boolean sameLevel = level(root, a) == level(root, b);
    return sameLevel && !sublings(root, a, b);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.left.right.right = newNode(15);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);

    Node a = root.left.left; // 4
    Node b = root.right.right; // 7

    System.out.println(cousins(root, a, b));
    
    
    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    root1.right.left = newNode(6);
    root1.right.right = newNode(7);
    System.out.println(cousins(root1, root1.left.right, root1.right.left));

  }
}