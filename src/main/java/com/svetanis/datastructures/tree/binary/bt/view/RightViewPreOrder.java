package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, return an array containing nodes in its right view. 
// The right view of a binary tree is the set of nodes visible 
// when the tree is seen from the right side.

public final class RightViewPreOrder {

  public static ImmutableList<Node> rightView(Node root) {
    // time complexity: O(n)

    AtomicInteger max = new AtomicInteger(0);
    List<Node> list = newArrayList();
    rightView(root, 1, max, list);
    return newList(list);
  }

  private static void rightView(Node root, int level, AtomicInteger max, List<Node> list) {
    if (isNull(root)) {
      return;
    }

    if (max.get() < level) {
      list.add(root);
      max.set(level);
    }
    rightView(root.right, level + 1, max, list);
    rightView(root.left, level + 1, max, list);
  }

  public static void main(String[] args) {
    Node root = newNode(12);
    root.left = newNode(10);
    root.left.left = newNode(30);
    root.left.left = newNode(25);
    root.left.right = newNode(40);
    print(rightView(root)); // 12 10 40
  }
}
