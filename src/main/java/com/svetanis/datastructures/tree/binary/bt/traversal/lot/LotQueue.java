package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LotQueue {

  public static ImmutableList<Node> traverse(Node root) {
    // time complexity: O(n)

    if (isNull(root)) {
      return newList();
    }

    List<Node> list = newArrayList();
    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      list.add(node);
      if (isNotNull(node.left)) {
        queue.offer(node.left);
      }
      if (isNotNull(node.right)) {
        queue.offer(node.right);
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
    root.right.right = newNode(6);

    print(traverse(root));
  }
}
