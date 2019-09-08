package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a Binary Tree, print corner nodes of every level in it.

public final class CornerNodes {

  public static ImmutableList<ImmutableList<Node>> cornerNodes(Node root) {
    // Time Complexity: O(n)

    if (isNull(root)) {
      return newList();
    }
    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    List<ImmutableList<Node>> lists = newArrayList();
    while (!queue.isEmpty()) {
      int size = queue.size();
      List<Node> list = newArrayList();
      for (int i = 0; i < size; i++) {
        Node node = queue.poll();
        if (i == 0 || i == size - 1) {
          list.add(node);
        }
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
      lists.add(newList(list));
    }
    return newList(lists);
  }

  public static void main(String[] args) {
    Node root = newNode(6);
    root.left = newNode(3);
    root.right = newNode(8);
    root.right.left = newNode(4);
    root.right.right = newNode(2);
    root.right.left.left = newNode(1);
    root.right.left.right = newNode(7);
    root.right.right.right = newNode(3);
    printLists(cornerNodes(root));
  }
}
