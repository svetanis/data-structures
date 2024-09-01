package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.svetanis.datastructures.tree.binary.bt.view.Item;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given BT, print all diagonal elements in BT belonging to same line.

public final class DiagonalTraversalIterative {

  public static ImmutableList<ImmutableList<Integer>> traverse(Node root) {
    if (isNull(root)) {
      return newList();
    }
    Queue<Item> queue = newLinkedList();
    queue.offer(new Item(root, 0));
    Multimap<Integer, Integer> mm = ArrayListMultimap.create();
    while (!queue.isEmpty()) {
      Item item = queue.poll();
      Node node = item.node;
      int dist = item.hd;
      while (isNotNull(node)) {
        mm.put(dist, node.data);
        if (isNotNull(node.left)) {
          queue.offer(new Item(node.left, dist + 1));
        }
        node = node.right;
      }
    }

    List<ImmutableList<Integer>> lists = newArrayList();
    for (int key : mm.keySet()) {
      lists.add(newList(mm.get(key)));
    }
    return newList(lists);
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
