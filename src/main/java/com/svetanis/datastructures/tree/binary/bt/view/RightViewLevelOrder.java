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

// Given a binary tree, return an array containing nodes in its right view. 
// The right view of a binary tree is the set of nodes visible 
// when the tree is seen from the right side.

public final class RightViewLevelOrder {

  public static ImmutableList<Node> rightView(Node root) {
    // Time Complexity: O(n)

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
        if (i == size - 1) {
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
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.right.right = newNode(8);

    // root.right.left.right = new Node(8);
    print(rightView(root));
  }
}
