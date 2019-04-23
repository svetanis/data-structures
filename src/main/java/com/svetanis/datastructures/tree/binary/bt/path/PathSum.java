package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class PathSum {

  public static boolean hasPathSum(Node root, int k) {
    // Time complexity: O(n)

    if (isNull(root)) {
      return false;
    }

    int sum = k - root.data;
    if (sum == 0 && isLeaf(root)) {
      return true;
    }

    boolean isLeft = hasPathSum(root.left, sum);
    boolean isRight = hasPathSum(root.right, sum);
    return isLeft || isRight;
  }

  public static void main(String[] args) {
    // sum = 21 : 10->8->3
    // sum = 23 : 10->8->5
    // sum = 14 : 10->2->2

    Node root = newNode(10);
    root.left = newNode(8);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.left.right = newNode(5);
    root.right.left = newNode(2);

    System.out.println(hasPathSum(root, 21));
    System.out.println(hasPathSum(root, 25));
  }
}
