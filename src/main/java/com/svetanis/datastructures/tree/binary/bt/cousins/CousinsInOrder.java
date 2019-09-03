package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given binary tree, determine if two given
// nodes are cousins of each other or not.
// two nodes of binary tree are cousins of 
// each other only if they have different 
// parents but they have same level

public final class CousinsInOrder {

  public static boolean cousins(Node root, int a, int b) {
    // Time Complexity: O(n)
    
    if (isNull(root)) {
      return true;
    }

    NodeInfo x = new NodeInfo(a, 1);
    NodeInfo y = new NodeInfo(b, 1);

    inorder(root, null, 1, x, y);
    return x.level == y.level && x.parent != y.parent;
  }

  private static void inorder(Node root, Node parent, 
                  int level, NodeInfo x, NodeInfo y) {

    if (isNull(root)) {
      return;
    }

    inorder(root.left, root, level + 1, x, y);

    if (root.data == x.key) {
      x.level = level;
      x.parent = parent;
    }

    if (root.data == y.key) {
      y.level = level;
      y.parent = parent;
    }

    inorder(root.right, root, level + 1, x, y);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.left.right.right = newNode(15);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);
    System.out.println(cousins(root, 4, 7));
    
    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    root1.right.left = newNode(6);
    root1.right.right = newNode(7);
    System.out.println(cousins(root1, 5, 6));
  }

  private static class NodeInfo {
    private int key;
    private int level;
    private Node parent;

    public NodeInfo(int key, int level) {
      this(key, level, null);
    }

    public NodeInfo(int key, int level, Node parent) {
      this.key = key;
      this.level = level;
      this.parent = parent;
    }
  }
}
