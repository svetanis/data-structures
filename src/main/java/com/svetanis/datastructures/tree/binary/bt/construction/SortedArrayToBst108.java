package com.svetanis.datastructures.tree.binary.bt.construction;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 108. Convert Sorted Array to BST

public final class SortedArrayToBst108 {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static Node arrayToBst(int[] a) {
    return arrayToBst(a, 0, a.length - 1);
  }

  private static Node arrayToBst(int[] a, int left, int right) {
    if (left > right) {
      return null;
    }
    int mid = left + (right - left) / 2;
    Node lst = arrayToBst(a, left, mid - 1);
    Node rst = arrayToBst(a, mid + 1, right);
    return new Node(a[mid], lst, rst);
  }

  public static void main(String[] args) {
    int[] a = { -10, -3, 0, 5, 9 };
    Nodes.preOrder(arrayToBst(a)); // 0, -3, 9, -10, 5
    System.out.println();
  }
}
