package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class BoundaryTraversalIterative {

  public static ImmutableList<Node> boundary(Node root) {
    // time complexity: O(n)

    List<Node> leftView = newArrayList();
    List<Node> rightView = newArrayList();
    Queue<Node> queue = newLinkedList();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Node node = queue.poll();
        if (isLeaf(node)) {
          continue;
        } else if (i == 0) {
          leftView.add(node);
        } else if (i == size - 1) {
          rightView.add(node);
        }

        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
    }

    List<Node> list = newArrayList();
    list.addAll(leftView);
    list.addAll(leaves(root));
    list.addAll(rightView);
    return newList(list);
  }

  private static ImmutableList<Node> leaves(Node root) {
    List<Node> list = newArrayList();
    Deque<Node> stack = new ArrayDeque<>();
    stack.push(root);
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      if (isLeaf(node)) {
        list.add(node);
      }
      if (node.right != null) {
        stack.push(node.right);
      }
      if (node.left != null) {
        stack.push(node.left);
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    Node tree = newNode(1);
    tree.left = newNode(2);
    tree.right = newNode(3);
    tree.left.left = newNode(4);
    tree.left.right = newNode(5);
    tree.right.right = newNode(6);

    print(boundary(tree));
    System.out.println();

    Node root = newNode(20);
    root.left = newNode(8);
    root.right = newNode(22);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    root.right.right = newNode(25);

    inOrder(root);
    System.out.println();

    print(boundary(root));
  }
}
