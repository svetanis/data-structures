package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.utils.Bits.isEven;
import static com.svetanis.java.base.utils.Bits.isOdd;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a Binary Tree having odd and even elements, 
// sink all its odd valued nodes such that no node 
// with odd value could be parent of node with even value. 

public final class SinkOddNodes {

  public static void sinkOddNodes(Node root) {
    if (isNull(root) || isLeaf(root)) {
      return;
    }
    sinkOddNodes(root.left);
    sinkOddNodes(root.right);
    if (isOdd(root.data)) {
      sink(root);
    }
  }

  private static void sink(Node root) {
    if (isNull(root) || isLeaf(root)) {
      return;
    }
    if (isNotNull(root.left) && isEven(root.left.data)) {
      int temp = root.data;
      root.data = root.left.data;
      root.left.data = temp;
      sink(root.left);
    }
    if (isNotNull(root.right) && isEven(root.right.data)) {
      int temp = root.data;
      root.data = root.right.data;
      root.right.data = temp;
      sink(root.right);
    }
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(5);
    root.right = newNode(8);
    root.left.left = newNode(2);
    root.left.right = newNode(4);
    root.right.left = newNode(9);
    root.right.right = newNode(10);
    sinkOddNodes(root);
    inOrder(root); // 5 2 1 8 9 10 4
  }
}
