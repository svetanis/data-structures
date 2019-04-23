package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class ZigZagStackQueue {

  public static ImmutableList<Integer> traverse(Node root) {

    boolean reverse = true;
    Stack<Integer> stack = new Stack<>();
    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    List<Integer> list = newArrayList();
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size > 0) {
        Node front = queue.poll();
        size--;

        if (reverse) {
          stack.push(front.data);
        } else {
          list.add(front.data);
        }

        if (isNotNull(front.left)) {
          queue.offer(front.left);
        }

        if (isNotNull(front.right)) {
          queue.offer(front.right);
        }
      }

      if (reverse) {
        while (!stack.isEmpty()) {
          list.add(stack.pop());
        }
      }
      reverse = !reverse;
    }

    return newList(list);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(7);
    root.left.right = newNode(6);
    root.right.left = newNode(5);
    root.right.right = newNode(4);
    print(traverse(root));
  }
}
