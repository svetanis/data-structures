package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.Stack;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class BstIterator {

  private Stack<Node> stack;

  public BstIterator(Node root) {
    this.stack = new Stack<>();
    pushLeft(root);
  }

  public boolean hasNext() {
    return !stack.isEmpty();
  }

  public int next() {
    Node node = stack.pop();
    pushLeft(node.right);
    return node.data;
  }

  private void pushLeft(Node node) {
    if (isNotNull(node)) {
      stack.push(node);
      pushLeft(node.left);
    }
  }

  private void pushLeftIterative(Node node) {
    while (node != null) {
      stack.push(node);
      node = node.left;
    }
  }

  public static void main(String[] args) {
    Node root = newNode(10);
    root.left = newNode(1);
    root.right = newNode(11);
    root.left.right = newNode(6);
    root.right.right = newNode(12);
    BstIterator iter = new BstIterator(root);
    while (iter.hasNext()) {
      System.out.print(iter.next() + " ");
    }
  }
}
