package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, populate an array to represent its level-by-level  
// traversal in reverse order, i.e., the lowest level comes first. 
// Populate the values of all nodes in each level from left to right in separate sub-arrays.

public final class LOTLineByLineReverseQueue {

  public static ImmutableList<ImmutableList<Integer>> lot(Node root) {
    if (isNull(root)) {
      return newList();
    }

    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    List<ImmutableList<Integer>> lists = newLinkedList();

    while (!queue.isEmpty()) {
      int size = queue.size();
      List<Integer> list = newArrayList();
      for (int i = 0; i < size; i++) {
        Node node = queue.poll();
        list.add(node.data);
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
      lists.add(0, newList(list));
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
