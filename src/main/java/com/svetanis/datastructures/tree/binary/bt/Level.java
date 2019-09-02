package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class Level {

  public static int level(Node root, Node node) {
    return level(root, node.data);
  }

  public static int level(Node root, int data) {
    return level(root, data, 1);
  }

  public static int level(Node root, int data, int level) {

    if (isNull(root)) {
      return 0;
    }

    if (root.data == data) {
      return level;
    }
    // return level if node is present in left subtree
    int downLevel = level(root.left, data, level + 1);
    if (downLevel != 0) {
      return downLevel;
    }
    // else search in right subtree
    return level(root.right, data, level + 1);
  }

  public static void givenLevel(Node node, int level) {
    givenLevel(node, level, true);
  }

  public static void givenLevel(Node node, int level, boolean ltr) {
    if (isNull(node)) {
      return;
    }
    if (level == 1) {
      System.out.print(node + " ");
    } else if (level > 1) {
      if (ltr) {
        givenLevel(node.left, level - 1, ltr);
        givenLevel(node.right, level - 1, ltr);
      } else {
        givenLevel(node.right, level - 1, ltr);
        givenLevel(node.left, level - 1, ltr);
      }
    }
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    System.out.println(level(root, 4));
  }
}