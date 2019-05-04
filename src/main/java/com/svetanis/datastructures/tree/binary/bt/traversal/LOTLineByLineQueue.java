package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LOTLineByLineQueue {

  public static ImmutableList<ImmutableList<Integer>> lot(Node root) {
    List<Integer> list = newArrayList();
    List<ImmutableList<Integer>> lists = newArrayList();

    if (isNull(root)) {
      return newList(lists);
    }
    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    int size = queue.size();

    while (!queue.isEmpty()) {

      Node node = queue.poll();
      list.add(node.data);
      size--;

      if (node.left != null) {
        queue.offer(node.left);
      }
      if (node.right != null) {
        queue.offer(node.right);
      }

      if (size == 0) {
        size = queue.size();
        lists.add(newList(list));
        list = newArrayList();
      }
    }
    return newList(lists);
  }

  public static void main(String[] args) {
    Node root = new Node(1);
    root.left = new Node(2);
    root.right = new Node(3);
    root.left.left = new Node(4);
    root.left.right = new Node(5);
    root.right.right = new Node(6);
    printLists(lot(root));
  }
}
