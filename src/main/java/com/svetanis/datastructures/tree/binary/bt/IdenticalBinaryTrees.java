package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Two trees are identical when they have same data and arrangement of data is also same.

public final class IdenticalBinaryTrees {

  public static boolean identical(Node root1, Node root2) {
    // 1. both empty
    if (isNull(root1) && isNull(root2)) {
      return true;
    }
    // 2. one empty, one not
    if (isNull(root1) || isNull(root2)) {
      return false;
    }
    // 3. both non-emty, compare them:
    boolean equal = root1.data == root2.data;
    boolean left = identical(root1.left, root2.left);
    boolean right = identical(root1.right, root2.right);
    return equal && left && right;
  }

  public static void main(String[] args) {
    Node tree1 = newNode(26);
    tree1.right = newNode(3);
    tree1.right.right = newNode(3);
    tree1.left = newNode(10);
    tree1.left.left = newNode(4);
    tree1.left.left.right = newNode(30);
    tree1.left.right = newNode(6);

    Node tree2 = newNode(10);
    tree2.left = newNode(4);
    tree2.right = newNode(6);
    tree2.left.right = newNode(30);

    System.out.println(identical(tree1, tree2));

    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.left = newNode(4);
    root2.left.right = newNode(5);

    System.out.println(identical(root1, root2));
  }
}
