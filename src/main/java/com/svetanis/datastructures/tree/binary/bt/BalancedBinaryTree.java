package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.insert;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.IntWrapper;

public final class BalancedBinaryTree {

  public static boolean isBalanced(Node root) {
    IntWrapper height = newIntWrapper();
    return isBalanced(root, height);
  }

  private static boolean isBalanced(Node root, IntWrapper height) {
    // Time complexity: O(n)

    IntWrapper left = newIntWrapper();
    IntWrapper right = newIntWrapper();

    if (isNull(root)) {
      height.value = 0;
      return true;
    }

    boolean isLeft = isBalanced(root.left, left);
    boolean isRight = isBalanced(root.right, right);

    // height of current node is max of heights
    // of left and right subtrees + 1
    height.value = 1 + max(left.value, right.value);

    if (abs(left.value - right.value) >= 2) {
      return false;
    }
    return isLeft && isRight;
  }

  public static void main(String[] args) {
    Node root1 = new Node(5);
    insert(root1, 3);
    insert(root1, 2);
    insert(root1, 1);
    insert(root1, 4);
    insert(root1, 6);
    System.out.println(isBalanced(root1));

    Node root2 = new Node(1);
    root2.left = new Node(2);
    root2.right = new Node(3);
    root2.left.left = new Node(4);
    root2.left.right = new Node(5);
    System.out.println(isBalanced(root2));
  }
}
