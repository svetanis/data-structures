package com.svetanis.datastructures.tree.binary.bt.iterator;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.Exceptions.illegalArgument;

import java.util.Stack;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LeafIterator {

  private Node leaf;
  private Stack<Node> stack;

  public LeafIterator(Node root) {
    this.stack = new Stack<>();
    stack.push(root);
  }

  public boolean hasNext() {
    if (isNotNull(leaf)) {
      return true;
    }
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      if (isLeaf(node)) {
        leaf = node;
        return true;
      }
      if (isNotNull(node.right)) {
        stack.push(node.right);
      }
      if (isNotNull(node.left)) {
        stack.push(node.left);
      }
    }
    return false;
  }

  public Node next() {
    if (!hasNext()) {
      throw illegalArgument("stack underflow");
    }
    Node node = leaf;
    leaf = null;
    return node;
  }

  public static void main(String[] args) {
    Node root = newNode(10);
    root.left = newNode(1);
    root.right = newNode(11);
    root.left.right = newNode(6);
    root.right.right = newNode(12);
    LeafIterator iter = new LeafIterator(root);
    while (iter.hasNext()) {
      System.out.print(iter.next() + " ");
    }
  }
}
