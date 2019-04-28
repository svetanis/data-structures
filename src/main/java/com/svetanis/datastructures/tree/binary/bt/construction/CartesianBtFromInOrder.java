package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.java.base.utils.Arrays.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Cartesian tree : is a heap ordered binary tree, 
// where the root is greater than all the elements in the subtree. 

public final class CartesianBtFromInOrder {

  public static Node construct(int[] a) {
    return construct(a, 0, a.length - 1);
  }
  
  public static Node construct(int[] a, int start, int end) {
    // Time Complexity: O(n^2)

    if (start > end) {
      return null;
    }

    // find index of the max element from BT
    int max = max(a, start, end).getRight();
    Node root = newNode(a[max]);

    // if this is the only element in
    // inorder[start ... end],
    // then return it
    if (start == end) {
      return root;
    }
    root.left = construct(a, start, max - 1);
    root.right = construct(a, max + 1, end);
    return root;
  }

  public static void main(String[] args) {
    int inorder[] = { 5, 10, 40, 30, 28 };
    Node root = construct(inorder);
    inOrder(root);
  }
}
