package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.insert;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.concurrent.atomic.AtomicBoolean;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a BT, write an efficient algorithm to check 
// if tree is balanced or not. In balanced tree, the
// abs diff between height of left and right subtree
// for every node is 0 or 1.

public final class BalancedBinaryTree {

  public static boolean isBalanced(Node root) {
    // Time complexity: O(n)
    AtomicBoolean balanced = new AtomicBoolean(true);
    isBalanced(root, balanced);
    return balanced.get();
  }

  private static int isBalanced(Node root, AtomicBoolean balanced) {
    // time complexity: O(n)

    if (isNull(root) || !balanced.get()) {
      return 0;
    }
    int left = isBalanced(root.left, balanced);
    int right = isBalanced(root.right, balanced);
    if (abs(left - right) > 1) {
      balanced.set(false);
    }
    return 1 + max(left, right);
  }

  public static void main(String[] args) {
    Node root1 = newNode(5);
    insert(root1, 3);
    insert(root1, 2);
    insert(root1, 1);
    insert(root1, 4);
    insert(root1, 6);
    System.out.println(isBalanced(root1));

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.left = newNode(4);
    root2.left.right = newNode(5);
    System.out.println(isBalanced(root2));
  }
}
