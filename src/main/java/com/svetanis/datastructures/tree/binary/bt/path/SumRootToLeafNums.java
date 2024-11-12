package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree where each node can only have a digit (0-9) value, 
// each root-to-leaf path will represent a number. 
// Find the total sum of all the numbers represented by all paths.

public final class SumRootToLeafNums {
  // Time Complexity: O(n)
	// Space Complexity: O(log n)
	
  private static final int MOD = 1003;
  
  public static int sum(Node root) {
    return sum(root, 0);
  }

  private static int sum(Node root, int sum) {
    if (isNull(root)) {
      return 0;
    }
    sum = sum * 10 + root.data;
    if (isLeaf(root)) {
      return sum;
    }
    int left = sum(root.left, sum);
    int right = sum(root.right, sum);
    return left + right;
  }

  public static int sumMod(Node root) {
    return sumMod(root, 0);
  }

  private static int sumMod(Node root, int sum) {
    if (isNull(root)) {
      return 0;
    }
    sum = (sum * 10 + root.data) % MOD;
    if (isLeaf(root)) {
      return sum;
    }
    int left = sumMod(root.left, sum);
    int right = sumMod(root.right, sum);
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
