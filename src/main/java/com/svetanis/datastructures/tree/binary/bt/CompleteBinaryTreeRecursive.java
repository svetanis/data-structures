package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class CompleteBinaryTreeRecursive {

  public static boolean isComplete(Node root) {
    int index = 0;
    int size = size(root);
    return isComplete(root, index, size);
  }

  private static boolean isComplete(Node root, int index, int size) {
    // the empty tree is complete
    if (root == null) {
      return true;
    }
    // if index assigned to current node is more than
    // number of nodes in tree, then tree is not complete
    if (index >= size) {
      return false;
    }
    // recur for left and right subtrees
    boolean left = isComplete(root.left, 2 * index + 1, size);
    boolean right = isComplete(root.right, 2 * index + 2, size);
    return left && right;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    System.out.println(isComplete(root));

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.left.left = newNode(4);
    System.out.println(isComplete(root2));

    Node root3 = newNode(1);
    root3.left = newNode(2);
    root3.right = newNode(3);
    root3.left.left = newNode(4);
    root3.left.right = newNode(5);
    System.out.println(isComplete(root3));
  }
}
