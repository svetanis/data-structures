package com.svetanis.datastructures.tree.binary.bt;

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
    Node root = new Node(1);
    root.left = new Node(2);
    root.right = new Node(3);
    root.left.left = new Node(4);
    root.left.right = new Node(5);
    root.right.right = new Node(6);
    System.out.println(isComplete(root));

    Node root2 = new Node(1);
    root2.left = new Node(2);
    root2.left.left = new Node(4);
    System.out.println(isComplete(root2));

    Node root3 = new Node(1);
    root3.left = new Node(2);
    root3.right = new Node(3);
    root3.left.left = new Node(4);
    root3.left.right = new Node(5);
    System.out.println(isComplete(root3));
  }
}
