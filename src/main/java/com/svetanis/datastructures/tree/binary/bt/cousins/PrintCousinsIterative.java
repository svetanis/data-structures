package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class PrintCousinsIterative {

  public static ImmutableList<Integer> cousins(Node root, Node node) {
    // Time complexity: O(n)

    if (root.equals(node)) {
      return newList();
    }

    boolean found = false;
    List<Integer> list = newArrayList();
    Queue<Node> queue = newLinkedList();
    queue.offer(root);

    while (!queue.isEmpty() && !found) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Node front = queue.poll();
        if (node.equals(front.left) || node.equals(front.right)) {
          found = true;
        } else {
          if (isNotNull(front.left)) {
            queue.offer(front.left);
          }
          if (isNotNull(front.right)) {
            queue.offer(front.right);
          }
        }
      }

      if (found) {
        for (int i = 0; i < size; i++) {
          list.add(queue.poll().data);
        }
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.left.right.right = newNode(15);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);
    print(cousins(root, root.left.right));
  }
}
