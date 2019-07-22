package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given two binary trees and imagine that when you put one of them to cover the other, 
// some nodes of the two trees are overlapped while the others are not.

// You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, 
// then sum node values up as the new value of the merged node. 
// Otherwise, the NOT null node will be used as the node of new tree.

public final class MergeBinaryTrees {

  public static Node merge(Node root1, Node root2) {
    if (isNull(root1)) {
      return root2;
    }
    if (isNull(root2)) {
      return root1;
    }
    Node root = newNode(root1.data + root2.data);
    root.left = merge(root1.left, root2.left);
    root.right = merge(root1.right, root2.right);
    return root;
  }

  public static void main(String[] args) {

    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    inOrder(root1);
    System.out.println();

    Node root2 = newNode(1);
    root2.left = newNode(3);
    root2.right = newNode(2);
    root2.right.left = newNode(5);
    root2.right.right = newNode(4);
    inOrder(root2);
    System.out.println();

    inOrder(merge(root1, root2));
  }
}
