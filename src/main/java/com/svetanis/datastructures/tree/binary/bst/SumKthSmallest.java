package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.IntWrapper;

public final class SumKthSmallest {

  public static int sum(Node root, int k) {
    // Time complexity: O(k)

    // count of nodes visited
    IntWrapper count = newIntWrapper();
    return sum(root, k, count);
  }

  private static int sum(Node root, int k, IntWrapper count) {

    if (isNull(root) || count.value > k) {
      return 0;
    }

    // follow inorder traversal
    // recur for left subtree first
    int sum = sum(root.left, k, count);

    if (count.value >= k) {
      return sum;
    }

    // add root's data
    sum += root.data;

    // increment count of visited nodes
    count.value++;

    if (count.value >= k) {
      return sum;
    }

    // if count < k, recur for right subtree
    return sum + sum(root.right, k, count);
  }

  public static void main(String[] args) {
    Node root = newNode(20);
    root.left = newNode(8);
    root.right = newNode(22);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(22);
    System.out.println(sum(root, 3));
  }
}
