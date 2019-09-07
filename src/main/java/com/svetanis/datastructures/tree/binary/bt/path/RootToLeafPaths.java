package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, print all of the paths from the root to leaf nodes.

public final class RootToLeafPaths {

  public static ImmutableList<ImmutableList<Integer>> paths(Node root) {
    // Time complexity: O(n)

    Deque<Integer> dq = new ArrayDeque<>();
    List<ImmutableList<Integer>> lists = newArrayList();
    path(root, dq, lists);
    return newList(lists);
  }

  private static void path(Node node, Deque<Integer> dq, List<ImmutableList<Integer>> lists) {
    if (isNull(node)) {
      return;
    }
    dq.addLast(node.data);
    if (isLeaf(node)) {
      lists.add(newList(dq));
    }
    path(node.left, dq, lists);
    path(node.right, dq, lists);
    dq.removeLast();
  }

  public static void main(String[] args) {
    // 10->8->3
    // 10->8->5
    // 10->2->2
    Node root = newNode(10);
    root.left = newNode(8);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.left.right = newNode(5);
    root.right.left = newNode(2);

    inOrder(root);
    System.out.println();

    // print Root-to-leaf path
    System.out.println("Root-to-leaf paths: ");
    printLists(paths(root));
    System.out.println();
  }
}
