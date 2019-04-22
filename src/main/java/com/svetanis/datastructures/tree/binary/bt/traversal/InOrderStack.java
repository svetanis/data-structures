package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class InOrderStack {

  public static ImmutableList<Node> inOrder(Node root) {
    // time complexity: O(n)
    // space complexity: O(n)

    List<Node> list = newArrayList();
    Stack<Node> stack = push(new Stack<>(), root);
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      list.add(node);
      stack = push(stack, node.right);
    }
    return newList(list);
  }

  private static Stack<Node> push(Stack<Node> stack, Node node) {
    while (isNotNull(node)) {
      stack.push(node);
      node = node.left;
    }
    return stack;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    print(inOrder(root));
  }
}
