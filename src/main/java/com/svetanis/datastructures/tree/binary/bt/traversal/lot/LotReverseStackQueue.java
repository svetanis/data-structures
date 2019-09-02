package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Arrays.toList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LotReverseStackQueue {

  public static ImmutableList<Integer> traverse(Node root) {
    // time complexity: O(n)

    if (isNull(root)) {
      return newList();
    }

    Queue<Node> queue = newLinkedList();
    Deque<Integer> stack = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      stack.push(node.data);
      if (isNotNull(node.right)) {
        queue.offer(node.right);
      }
      if (isNotNull(node.left)) {
        queue.offer(node.left);
      }
    }
    return toList(stack);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);

    print(traverse(root));
  }
}
