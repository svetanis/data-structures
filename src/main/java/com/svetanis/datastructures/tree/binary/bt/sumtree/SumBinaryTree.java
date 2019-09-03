package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// A SumTree is a Binary Tree where the value of a node is equal to sum 
// of the nodes present in its left subtree and right subtree.

public final class SumBinaryTree {

  public static boolean sumTree(Node root) {
    // Time Complexity: O(n)

    // if tree is empty or
    // it's a leaf node then return true
    if (isNull(root) || isLeaf(root)) {
      return true;
    }

    boolean isLeft = sumTree(root.left);
    boolean isRight = sumTree(root.right);

    if (isLeft && isRight) {
      // get the sum of nodes in left subtree
      int left = getValue(root.left);

      // get the sum of nodes in right subtree
      int right = getValue(root.right);

      // if root's data is equal to sum of nodes
      // in left and right subtrees
      // then return true else return false
      return root.data == left + right;
    }
    return false;
  }

  private static int getValue(Node node) {
    if (isNull(node)) {
      return 0;
    }
    return isLeaf(node) ? node.data : 2 * node.data;
  }

  public static void main(String[] args) {
    Node root = newNode(26);
    root.left = newNode(10);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(6);
    root.right.right = newNode(3);
    System.out.println(sumTree(root));
  }
}
