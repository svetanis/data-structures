package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given binary tree, in-place convert it to its sum tree.
// in sum tree, value at each node is equal to the sum of
// all elements present in its left and right subtree.
// the value of an empty node is considered as 0

public final class ConvertBtToSumTree {

  public static int convert(Node root) {
    // Time Complexity: O(n)

    // base case
    if (isNull(root)) {
      return 0;
    }
    
    // recursively convert left and right subtrees
    int left = convert(root.left);
    int right = convert(root.right);

    // store the old value
    int old = root.data;

    // update root to sum of left and right subtree
    root.data = left + right;

    // return sum of values of nodes
    // in left and right subtrees
    // and old value of this node
    return root.data + old;
  }

  public static void main(String[] args) {

    Node root = newNode(10);
    root.left = newNode(-2);
    root.right = newNode(6);
    root.left.left = newNode(8);
    root.left.right = newNode(-4);
    root.right.left = newNode(7);
    root.right.right = newNode(5);

    inOrder(root);
    System.out.println();

    convert(root);

    inOrder(root);
    System.out.println();
  }
}
