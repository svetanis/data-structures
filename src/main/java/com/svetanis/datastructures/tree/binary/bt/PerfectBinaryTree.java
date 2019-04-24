package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// A Binary tree is Perfect Binary Tree in which 
// 1. all internal nodes have two children 
// 2. all leaves are at same level.

public final class PerfectBinaryTree {

  public static boolean perfectBt(Node root) {
    int height = height(root);
    return perfectBT(root, height, 0);
  }

  private static boolean perfectBT(Node root, int height, int level) {
    if (isNull(root)) {
      return true;
    }
    if (isLeaf(root)) {
      return height == level + 1;
    }
    if (isNull(root.left) || isNull(root.right)) {
      return false;
    }
    boolean left = perfectBT(root.left, height, level + 1);
    boolean right = perfectBT(root.right, height, level + 1);
    return left && right;
  }

  public static void main(String[] args) {
    Node root = newNode(10);
    root.left = newNode(20);
    root.right = newNode(30);
    root.left.left = newNode(40);
    root.left.right = newNode(50);
    root.right.left = newNode(60);
    root.right.right = newNode(70);
    System.out.println(perfectBt(root));
  }
}
