package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree with distinct nodes
// (no two nodes have the same have data values). 
// Print the path from root to a given node x.

public final class RootToGivenNodePath {

  public static ImmutableList<Node> path(Node root, int x) {
    // Time Complexity: O(n)

    List<Node> list = newArrayList();
    path(root, x, list);
    return newList(list);
  }

  public static boolean path(Node root, int x, List<Node> list) {
    if (isNull(root)) {
      return false;
    }
    boolean equal = root.data == x;
    boolean left = path(root.left, x, list);
    boolean right = path(root.right, x, list);
    if (equal || left || right) {
      list.add(0, root);
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    Node root = newNode(5);
    root.left = newNode(10);
    root.right = newNode(15);
    root.left.left = newNode(20);
    root.left.right = newNode(25);
    root.left.right.right = newNode(45);
    root.right.left = newNode(30);
    root.right.right = newNode(35);
    System.out.println(path(root, 45));
  }
}
