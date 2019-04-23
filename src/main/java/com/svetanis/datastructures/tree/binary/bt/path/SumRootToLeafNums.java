package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class SumRootToLeafNums {

  private static final int MOD = 1003;
  
  public static int sum(Node root) {
    return sum(root, 0);
  }

  private static int sum(Node root, int value) {
    // Time Complexity: O(n)

    if (isNull(root)) {
      return 0;
    }
    value = value * 10 + root.data;
    if (isLeaf(root)) {
      return value;
    }
    int left = sum(root.left, value);
    int right = sum(root.right, value);
    return left + right;
  }

  public static int sumMod(Node root) {
    return sumMod(root, 0);
  }

  private static int sumMod(Node root, int value) {
    // Time Complexity: O(n)

    if (isNull(root)) {
      return 0;
    }
    value = (value * 10 + root.data) % MOD;
    if (isLeaf(root)) {
      return value;
    }
    int left = sumMod(root.left, value);
    int right = sumMod(root.right, value);
    return (left + right) % MOD;
  }

  public static void main(String[] args) {
    Node root = newNode(6);
    root.left = newNode(3);
    root.right = newNode(5);
    root.left.left = newNode(2);
    root.right.right = newNode(4);
    root.left.right = newNode(5);
    root.left.right.left = newNode(7);
    root.left.right.right = newNode(4);
    System.out.println(sum(root));
    System.out.println(sumMod(root));
  }
}
