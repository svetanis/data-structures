package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LeftViewLevelOrder {

  public static ImmutableList<Node> leftView(Node root) {
    // time complexity: O(n)

    if (isNull(root)) {
      return newList();
    }

    List<Node> list = newArrayList();
    Queue<Node> queue = newLinkedList();
    queue.offer(root);

    int size = queue.size();
    int level = 1;
    int max = 0;
    while (!queue.isEmpty()) {
      Node node = queue.peek();
      if (level > max) {
        list.add(node);
        max = level;
      }
      if (node.left != null) {
        queue.offer(node.left);
      }
      if (node.right != null) {
        queue.offer(node.right);
      }
      queue.poll();
      size--;
      if (size == 0) {
        size = queue.size();
        level++;
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    Node root = newNode(12);
    root.left = newNode(10);
    root.left.left = newNode(30);
    root.left.left = newNode(25);
    root.left.right = newNode(40);
    print(leftView(root));
  }
}
