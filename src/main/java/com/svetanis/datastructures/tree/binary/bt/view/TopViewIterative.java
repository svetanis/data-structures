package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Maps.newTreeMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class TopViewIterative {

  public static ImmutableList<Integer> topView(Node root) {
    // Time Complexity: O(n)

    // base case
    if (root == null) {
      return newList();
    }

    Map<Integer, Integer> map = newTreeMap();
    Queue<Item> queue = new ArrayDeque<>();
    // horizontal distance of root is 0
    queue.offer(new Item(root, 0));

    // standard BFS or
    // level order traversal loop
    while (!queue.isEmpty()) {
      // remove the front item
      // and get its details
      Item item = queue.poll();
      int hDist = item.hDist;
      Node node = item.node;

      // if this is the first node
      // at its horizontal dist,
      // then this node is in top view
      if (!map.containsKey(hDist)) {
        map.put(hDist, node.data);
      }

      // enqueue left and right
      // children of current node
      if (node.left != null) {
        queue.offer(new Item(node.left, hDist - 1));
      }
      if (node.right != null) {
        queue.offer(new Item(node.right, hDist + 1));
      }
    }
    return newList(map.values());
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    print(topView(root)); // 4 2 1 3 7

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.right = newNode(4);
    root2.left.right.right = newNode(5);
    root2.left.right.right.right = newNode(6);
    print(topView(root2)); // 2 1 3 6
  }
}
