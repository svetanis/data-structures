package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LotRecursive {

  public static ImmutableList<ImmutableList<Integer>> traverse(Node root) {
    // time complexity: O(n^2)

    int height = height(root);
    List<ImmutableList<Integer>> lists = newArrayList();
    for (int i = 1; i <= height; i++) {
      List<Integer> list = newArrayList();
      givenLevel(root, i, list);
      lists.add(newList(list));
    }
    return newList(lists);
  }

  private static void givenLevel(Node node, int k, List<Integer> list) {
    if (isNull(node)) {
      return;
    }
    if (k == 1) {
      list.add(node.data);
    } else if (k > 1) {
      givenLevel(node.left, k - 1, list);
      givenLevel(node.right, k - 1, list);
    }
  }

  public static void main(String[] args) {
    Node root = newNode(3);
    root.left = newNode(9);
    root.right = newNode(20);
    root.right.left = newNode(15);
    root.right.right = newNode(7);
    printLists(traverse(root));
    System.out.println();
  }
}
