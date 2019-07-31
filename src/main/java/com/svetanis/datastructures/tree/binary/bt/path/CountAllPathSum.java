package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.List;
import java.util.ListIterator;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree and a number ‘S’, 
// find all paths in the tree such that 
// the sum of all the node values of each path equals ‘S’. 
// Please note that the paths can start or end at any node 
// but all paths must follow direction from parent to child (top to bottom).

public final class CountAllPathSum {

  public static int pathCount(Node root, int k) {
    // Time complexity: O(n)
    List<Integer> list = newLinkedList();
    return pathCount(root, k, list);
  }

  private static int pathCount(Node root, int k, List<Integer> list) {

    if (isNull(root)) {
      return 0;
    }

    int sum = 0;
    int count = 0;
    list.add(root.data);
    ListIterator<Integer> iter = list.listIterator();
    while (iter.hasPrevious()) {
      sum += iter.previous();
      if (sum == k) {
        count++;
      }
    }

    count += pathCount(root.left, k, list);
    count += pathCount(root.right, k, list);

    list.remove(list.size() - 1);
    return count;
  }

  public static void main(String[] args) {
    Node root = newNode(12);
    root.left = newNode(7);
    root.right = newNode(1);
    root.left.left = newNode(4);
    root.right.left = newNode(10);
    root.right.right = newNode(5);
    System.out.println(pathCount(root, 11));
  }
}
