package com.svetanis.datastructures.tree.binary.bt.iterator;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.Stack;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Implement an iterator over a binary search tree (BST). 
// Your iterator will be initialized with the root node of a BST. 
// Calling next() will return the next smallest number in the BST. 
// Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, 
// where h is the height of the tree.

public final class BtIterator {

  private Stack<Node> stack;

  public BtIterator(Node root) {
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
    BtIterator iter = new BtIterator(root);
    while (iter.hasNext()) {
      System.out.print(iter.next() + " ");
    }
  }
}
