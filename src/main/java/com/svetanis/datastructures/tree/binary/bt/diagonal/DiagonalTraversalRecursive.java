package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given BT, print all diagonal elements in BT belonging to same line.

public final class DiagonalTraversalRecursive {

  public static ImmutableList<ImmutableList<Integer>> traverse(Node root) {
    Multimap<Integer, Integer> map = ArrayListMultimap.create();
    traverse(root, 0, map);
    List<ImmutableList<Integer>> lists = newArrayList();
    for (int key : map.keySet()) {
      lists.add(newList(map.get(key)));
    }
    return newList(lists);
  }

  private static void traverse(Node root, int d, Multimap<Integer, Integer> mm) {
    if (isNull(root)) {
      return;
    }
    mm.put(d, root.data);
    traverse(root.left, d + 1, mm);
    traverse(root.right, d, mm);
  }

  public static void main(String[] args) {
    Node root = newNode(8);
    root.left = newNode(3);
    root.right = newNode(10);
    root.left.left = newNode(1);
    root.left.right = newNode(6);
    root.right.right = newNode(14);
    root.left.right.left = newNode(4);
    root.left.right.right = newNode(7);
    root.right.right.left = newNode(13);
    printLines(traverse(root));
  }
}
