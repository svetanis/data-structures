package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.IntWrapper;

public final class MaxSumPathBetweenAnyTwoNodes {

  public static int maxSum(Node root) {
    IntWrapper max = newIntWrapper();
    maxSum(root, max);
    return max.value;
  }

  // this function calculates two values:
  // 1. max path sum between two nodes which is stored in max.value
  // 2. the max root-to-leaf path sum which is returned
  private static int maxSum(Node root, IntWrapper max) {
    // base case
    if (root == null) {
      return 0;
    }
    // max root-to-leaf sum in left and right subtree
    int left = max(0, maxSum(root.left, max));
    int right = max(0, maxSum(root.right, max));

    // max sum path passing through current node
    int sum = root.data + left + right;
    // max sum path found so far
    max.value = max(max.value, sum);
    // max sum node-to-leaf path starting from current node
    return root.data + max(left, right);
  }

  public static void main(String[] args) {
    // Max sum is 27 = 3 + 6 + 9 + 0 - 1 + 10
    Node root = newNode(-15);
    root.left = newNode(5);
    root.right = newNode(6);
    root.left.left = newNode(-8);
    root.left.right = newNode(1);
    root.right.left = newNode(3);
    root.right.right = newNode(9);
    root.left.left.left = newNode(2);
    root.left.left.right = newNode(6);
    root.right.right.right = newNode(0);
    root.right.right.right.left = newNode(4);
    root.right.right.right.right = newNode(-1);
    root.right.right.right.right.left = newNode(10);

    inOrder(root);
    System.out.println();
    System.out.println(maxSum(root));

    // Max sum is 42 = 20 + 2 + 10 + 10
    Node root2 = newNode(10);
    root2.left = newNode(2);
    root2.right = newNode(10);
    root2.left.left = newNode(20);
    root2.left.right = newNode(1);
    root2.right.right = newNode(-25);
    root2.right.right.left = newNode(3);
    root2.right.right.right = newNode(4);
    System.out.println(maxSum(root2));

  }
}
