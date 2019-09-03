package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.bt.compare.IdenticalBinaryTreesRecursive.identical;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class SubTreeNodeComparison {

  public static boolean isSubTree(Node root1, Node root2) {
    // Time Complexity: O(n*m)

    // base case
    if (root2 == null) {
      return true;
    }

    if (root1 == null) {
      return false;
    }

    // check the tree with
    // root as current node
    if (identical(root1, root2)) {
      return true;
    }

    // if the tree with root as current node doesn't match
    // then try left and right subtrees one by one
    boolean left = isSubTree(root1.left, root2);
    boolean right = isSubTree(root1.right, root2);
    return left || right;
  }

  public static void main(String[] args) {
    Node root1 = newNode(26);
    root1.right = newNode(3);
    root1.right.right = newNode(3);
    root1.left = newNode(10);
    root1.left.left = newNode(4);
    root1.left.left.right = newNode(30);
    root1.left.right = newNode(6);

    Node root2 = newNode(10);
    root2.left = newNode(4);
    root2.right = newNode(6);
    root2.left.right = newNode(30);

    System.out.println(isSubTree(root1, root2));
  }
}
