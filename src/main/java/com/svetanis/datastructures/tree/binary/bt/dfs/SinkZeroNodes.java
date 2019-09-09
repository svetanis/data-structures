package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given BT containing many zero nodes, sink nodes 
// having zero value to the bottom of the sub-tree
// rooted at that node. 

public final class SinkZeroNodes {

  public static void sinkZeroNodes(Node root) {

    if (isNull(root)) {
      return;
    }
    sinkZeroNodes(root.left);
    sinkZeroNodes(root.right);
    if (root.data == 0) {
      sink(root);
    }
  }

  private static void sink(Node root) {
    if (isNull(root)) {
      return;
    }
    if (isNotNull(root.left) && root.left.data != 0) {
      int temp = root.data;
      root.data = root.left.data;
      root.left.data = temp;
      sink(root.left);
    } else if (isNotNull(root.right) && root.right.data != 0) {
      int temp = root.data;
      root.data = root.right.data;
      root.right.data = temp;
      sink(root.right);
    }
  }

  public static void main(String[] args) {
    Node root = newNode(0);
    root.left = newNode(1);
    root.right = newNode(0);
    root.right.left = newNode(0);
    root.right.right = newNode(2);
    root.right.left.left = newNode(3);
    root.right.left.right = newNode(4);
    sinkZeroNodes(root);
    inOrder(root); 
  }
}
