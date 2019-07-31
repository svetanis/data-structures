package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, print all of the paths from the root to leaf nodes.

public final class RootToLeafPaths {

  public static ImmutableList<ImmutableList<Integer>> paths(Node root) {
    // Time complexity: O(n)

    List<Integer> list = newArrayList();
    List<ImmutableList<Integer>> lists = newArrayList();
    path(root, list, lists);
    return newList(lists);
  }

  private static void path(Node node, List<Integer> list, List<ImmutableList<Integer>> lists) {
    if (isNull(node)) {
      return;
    }
    list.add(node.data);
    if (isLeaf(node)) {
      lists.add(newList(list));
    }
    path(node.left, list, lists);
    path(node.right, list, lists);
    list.remove(list.size() - 1);
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
