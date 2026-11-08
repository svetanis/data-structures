package com.svetanis.datastructures.heap;

// Check whether an array is a binary max heap (no LeetCode number)
//
// Input: an int array a, any length, empty included. Returns true when a, read as a binary heap,
// keeps the max-heap order: every parent is greater than or equal to each of its children.
//
// The one idea: one array entry is one node of a complete binary tree, filled row by row. Node
// i's children sit at 2i + 1 and 2i + 2, when those indexes exist. Only parents have anything to
// check, and the parents are exactly 0..(n - 2) / 2: the last index, n - 1, has its parent at
// (n - 1 - 1) / 2 = (n - 2) / 2, and every later index would need a child past the end. So walk
// the parents up to and including that last one and compare each with the children it has. The
// last parent can have a left child and no right one (when n is even), and with n = 1 Java's
// (1 - 2) / 2 rounds to 0, not -1, so each child's index is checked before it is read.
//
// Time: O(n) -- about n / 2 parents, two comparisons each.
// Space: O(1) -- a loop counter.
//
// Siblings: BinaryMaxHeapRecursive checks the same rule top-down, one call per node.

public final class BinaryMaxHeapIterative {

  public static boolean isHeap(int[] a) {
    int n = (a.length - 2) / 2; // LAST PARENT: the parent of index a.length - 1
    for (int i = 0; i <= n; i++) { // WALK every parent, the last one included

      // if left child is greater
      if (2 * i + 1 < a.length && a[i] < a[2 * i + 1]) { // LEFT child, if there is one
        return false; // BROKEN: a child above its parent
      }

      // if right child is greater
      if (2 * i + 2 < a.length && a[i] < a[2 * i + 2]) { // RIGHT child, if there is one
        return false; // BROKEN
      }
    }
    return true; // HEAP: no parent below a child
  }

  private static int left(int index) {
    return 2 * index + 1; // LEFT child's index
  }

  private static int right(int index) {
    return 2 * index + 2; // RIGHT child's index
  }

  public static void main(String[] args) {
    int[] a = { 90, 15, 10, 7, 12, 2, 7, 3 };
    // to explain (n - 2)/2 boundary
    for (int i = 0; i < a.length; i++) {
      System.out.println(i + ": " + left(i) + " " + right(i)); // 0: 1 2 / 1: 3 4 / 2: 5 6 / 3: 7 8 / ... / 7: 15 16
    }
    System.out.println(isHeap(a)); // true
    int[] b = { 90, 15, 10, 7, 12, 2, 7, 8 }; // the last parent, index 3, is below its child 8
    System.out.println(isHeap(b)); // false
    int[] c = { 5 };
    System.out.println(isHeap(c)); // true
  }
}

