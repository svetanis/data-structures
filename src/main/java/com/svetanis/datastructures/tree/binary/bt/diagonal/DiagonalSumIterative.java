package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Map;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.bt.view.Item;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given BT, calculate sum of all nodes for each diagonal
// having negative slope. Assume that the left and right 
// child of a node makes 45 degree angle with the parent.

public final class DiagonalSumIterative {

  public static ImmutableList<Integer> diagonalSum(Node root) {

    if (isNull(root)) {
      return newList();
    }

    Queue<Item> queue = newLinkedList();
    Map<Integer, Integer> map = newHashMap();
    queue.offer(new Item(root, 0));
    while (!queue.isEmpty()) {
      Item item = queue.poll();
      Node node = item.node;
      int dist = item.hd;
      while (isNotNull(node)) {
        int sum = 0;
        if (map.containsKey(dist)) {
          sum = map.get(dist);
        }
        sum += node.data;
        map.put(dist, sum);

        if (isNotNull(node.left)) {
          queue.offer(new Item(node.left, dist + 1));
        }
        node = node.right;
      }
    }
    return newList(map.values());
  }

  public static void main(String[] args) {
    // 9 is sum of 1, 3 and 5.
    // 19 is sum of 2, 6, 4 and 7.
    // 42 is sum of 9, 10, 11 and 12.

    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(9);
    root.left.right = newNode(6);
    root.right.left = newNode(4);
    root.right.right = newNode(5);
    root.left.left.right = newNode(10);
    root.left.right.left = newNode(11);
    root.right.left.left = newNode(12);
    root.right.left.right = newNode(7);
    print(diagonalSum(root));
  }
}
