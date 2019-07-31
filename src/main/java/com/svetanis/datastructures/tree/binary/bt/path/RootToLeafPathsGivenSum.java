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

// Given a binary tree and a number ‘S’, 
// find all paths from root-to-leaf such that
// the sum of all the node values of each path equals ‘S’.

public final class RootToLeafPathsGivenSum {

  public static ImmutableList<ImmutableList<Integer>> paths(Node node, int k) {
    List<Integer> list = newArrayList();
    List<ImmutableList<Integer>> lists = newArrayList();
    paths(node, k, list, lists);
    return newList(lists);
  }

  private static void paths(Node node, int k, List<Integer> list, List<ImmutableList<Integer>> lists) {
    // Time complexity: O(n)

    if (isNull(node)) {
      return;
    }
    list.add(node.data);
    if (isLeaf(node) && k == node.data) {
      lists.add(newList(list));
    }
    paths(node.left, k - node.data, list, lists);
    paths(node.right, k - node.data, list, lists);
    list.remove(list.size() - 1);
  }

  public static void main(String[] args) {
    // sum = 21 : 10->8->3
    // sum = 23 : 10->8->5
    // sum = 21 : 10->9->2

    Node root = newNode(10);
    root.left = newNode(8);
    root.right = newNode(9);
    root.left.left = newNode(3);
    root.left.right = newNode(5);
    root.right.left = newNode(2);

    inOrder(root);
    System.out.println();

    // given sum root-to-leaf path
    printLists(paths(root, 21));
  }
}
