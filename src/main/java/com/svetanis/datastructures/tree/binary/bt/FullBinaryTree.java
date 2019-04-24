package com.svetanis.datastructures.tree.binary.bt;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class FullBinaryTree {

  // Full Binary Tree is defined as a Binary Tree
  // in which all nodes have either zero or two child nodes.

  public static boolean fullBt(Node root) {

    if (isNull(root)) {
      return true;
    }
    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      if (isLeaf(node)) {
        continue;
      }
      if (isNull(node.left) || isNull(node.right)) {
        return false;
      }

      queue.offer(node.left);
      queue.offer(node.right);
    }
    return true;
  }

  public static void main(String[] args) {
    Node root = newNode(10);
    root.left = newNode(20);
    root.right = newNode(30);
    root.left.left = newNode(50);
    root.left.right = newNode(40);
    root.right.left = newNode(60);
    root.right.right = newNode(70);
    root.left.left.left = newNode(80);
    root.left.left.right = newNode(90);
    root.left.right.left = newNode(80);
    root.left.right.right = newNode(90);
    root.right.left.left = newNode(80);
    root.right.left.right = newNode(90);
    root.right.right.left = newNode(80);
    root.right.right.right = newNode(90);
    System.out.println(fullBt(root));
  }
}
