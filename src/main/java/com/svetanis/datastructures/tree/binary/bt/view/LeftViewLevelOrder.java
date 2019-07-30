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

// Given a binary tree, return an array containing nodes in its left view. 
// The left view of a binary tree is the set of nodes visible 
// when the tree is seen from the left side.

public final class LeftViewLevelOrder {

  public static ImmutableList<Node> leftView(Node root) {
    // time complexity: O(n)
    if (isNull(root)) {
      return newList();
    }
    List<Node> list = newArrayList();
    Queue<Node> queue = newLinkedList();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Node node = queue.poll();
        if (i == 0) {
          list.add(node);
        }
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
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
    print(leftView(root)); // 12 10 25
  }
}
