package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class ZigZagRecursive {

  public static ImmutableList<ImmutableList<Integer>> traverse(Node root) {
    List<ImmutableList<Integer>> lists = newArrayList();

    int height = height(root);
    boolean leftToRight = true;
    for (int i = 1; i <= height; ++i) {
      List<Integer> list = new ArrayList<>();
      givenLevel(root, i, leftToRight, list);
      lists.add(newList(list));
      // revert ltr to traverse
      // next level in opposite order
      leftToRight = !leftToRight;
    }
    return newList(lists);
  }

  private static void givenLevel(Node node, int k, boolean leftToRight, List<Integer> list) {
    if (isNull(node)) {
      return;
    }
    if (k == 1) {
      list.add(node.data);
    } else if (k > 1) {
      if (leftToRight) {
        givenLevel(node.left, k - 1, leftToRight, list);
        givenLevel(node.right, k - 1, leftToRight, list);
      } else {
        givenLevel(node.right, k - 1, leftToRight, list);
        givenLevel(node.left, k - 1, leftToRight, list);
      }
    }
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);

    printLists(traverse(root));
  }
}
