package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class PreOrderStack {

  public static ImmutableList<Node> preOrder(Node root) {
    // base case
    if (isNull(root)) {
      return newList();
    }
    List<Node> list = newArrayList();
    Stack<Node> stack = new Stack<>();
    stack.push(root);

    // pop all items one by one.
    // do the following for every popped item
    // 1. print it
    // 2. push its right child
    // 3. push its left child
    // note that right child is pushed first
    // so that left is processed first
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      list.add(node);
      if (isNotNull(node.right)) {
        stack.push(node.right);
      }
      if (isNotNull(node.left)) {
        stack.push(node.left);
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
    print(preOrder(root));
  }
}
