package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Multimaps.newMultimap;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Queue;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.Pair;

public final class VerticalOrderIterative {

  public static ImmutableMultimap<Integer, Integer> verticalOrder(Node root) {

    // Time Complexity: O(n)
    // Aux Space: O(n)

    Multimap<Integer, Integer> mm = LinkedHashMultimap.create();
    Queue<Pair<Node, Integer>> queue = newLinkedList();
    queue.offer(Pair.build(root, 0));
    while (!queue.isEmpty()) {
      Pair<Node, Integer> pair = queue.poll();
      int dist = pair.getRight();
      Node node = pair.getLeft();
      mm.put(dist, node.data);
      if (isNotNull(node.left)) {
        queue.offer(Pair.build(node.left, dist - 1));
      }
      if (isNotNull(node.right)) {
        queue.offer(Pair.build(node.right, dist + 1));
      }
    }
    return newMultimap(mm);
  }

  public static void main(String[] args) {

    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);
    root.right.right.right = newNode(9);
    root.right.right.left = newNode(10);
    root.right.right.left.right = newNode(11);
    root.right.right.left.right.right = newNode(12);
    print(verticalOrder(root));
  }

  // -2: [4]
  // -1: [2]
  //  0: [1, 5, 6]
  //  1: [3, 8, 10]
  //  2: [7, 11]
  //  3: [9, 12]
}
