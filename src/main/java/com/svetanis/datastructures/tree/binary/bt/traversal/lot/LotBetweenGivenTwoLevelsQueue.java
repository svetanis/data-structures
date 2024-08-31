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

// given a binary tree and two level numbers low and high
// print nodes from level low to level high

public final class LotBetweenGivenTwoLevelsQueue {

  public static ImmutableList<Node> traverse(Node root, int low, int high) {
    // time complexity: O(n)

    if (isNull(root)) {
      return newList();
    }

    List<Node> list = newArrayList();
    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    int level = 1;
    int size = queue.size();
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      if (level >= low && level <= high) {
        list.add(node);
      }
      if (isNotNull(node.left)) {
        queue.offer(node.left);
      }
      if (isNotNull(node.right)) {
        queue.offer(node.right);
      }
      if (--size == 0) {
        size = queue.size();
        level++;
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    Node root = newNode(20);
    root.left = newNode(8);
    root.right = newNode(22);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    print(traverse(root, 2, 4));
  }
}
