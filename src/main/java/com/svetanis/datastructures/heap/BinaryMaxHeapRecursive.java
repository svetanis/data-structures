package com.svetanis.datastructures.heap;

// Check whether an array is a binary max heap (no LeetCode number)
//
// Input: an int array a, any length, empty included, and the node i to start from (0 for the
// whole array). Returns true when the subtree rooted at node i keeps the max-heap order: every
// parent is greater than or equal to each of its children.
//
// The one idea: one array entry is one node of a complete binary tree, filled row by row; node
// i's children sit at 2i + 1 and 2i + 2, when those indexes exist. A subtree is a max heap when
// its root is at least each child it has and both child subtrees are max heaps. A node with no
// left child, 2i + 1 >= n, has no children at all and is a heap by itself; a node can have a
// left child and no right one (the last parent when n is even), so the right child's index is
// checked before it is read. A missing right child is reached as a call on 2i + 2 >= n, which
// the leaf test answers true.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(log n) -- the recursion follows one root-to-leaf path at a time, and a complete tree
// of n nodes is about log n levels deep.
//
// Siblings: BinaryMaxHeapIterative checks the same rule with one loop over the parents.

public final class BinaryMaxHeapRecursive {

  public static boolean isHeap(int[] a, int i) {
    int n = a.length;
    // leaf node
    if (2 * i + 1 >= n) { // LEAF: no left child means no children at all
      return true;
    }

    boolean one = a[i] >= a[2 * i + 1]; // LEFT child no greater than the parent
    boolean two = 2 * i + 2 >= n || a[i] >= a[2 * i + 2]; // RIGHT child, if there is one
    boolean left = isHeap(a, 2 * i + 1); // RECURSE into both subtrees
    boolean right = isHeap(a, 2 * i + 2);
    return one && two && left && right; // HEAP: this node and everything below it
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
    System.out.println(isHeap(a, 0)); // true
    int[] b = { 90, 15, 10, 7, 12, 2, 7, 8 }; // the last parent, index 3, is below its child 8
    System.out.println(isHeap(b, 0)); // false
    int[] c = { 5 };
    System.out.println(isHeap(c, 0)); // true
  }
}
