package com.svetanis.datastructures.heap;

import java.util.Arrays;

// Convert a min heap to a max heap (no LeetCode number)
//
// Input: an int array a holding a binary min heap. Rearranges a in place so that it holds a
// binary max heap of the same values. The min-heap order of the input is never used: any array
// comes out as a max heap.
//
// The one idea: one array entry is one node of a complete binary tree; node i's children sit at
// 2i + 1 and 2i + 2, when those indexes are below a.length. maxHeapify(i) assumes both subtrees
// under i are already max heaps and sinks a[i]: swap it with its larger child while that child
// is bigger, then repeat one level down. Leaves are max heaps on their own, so calling it on
// every parent from the last one, (a.length - 2) / 2, back to the root makes each subtree a max
// heap before its parent is handled, and the whole array last.
//
// Time: O(n) -- a node h levels above the leaves sinks at most h levels, and half the nodes are
// leaves, a quarter one level up, and so on, which adds up to under 2n swaps.
// Space: O(log n) -- maxHeapify recurses at most once per level.

public final class ConvertMinHeapToMaxHeap {

  public static void convert(int[] a) {
    int n = (a.length - 2) / 2; // LAST PARENT: the parent of index a.length - 1
    for (int i = n; i >= 0; i--) { // WALK the parents from the last one back to the root
      maxHeapify(a, i, a.length); // SINK a[i] within the whole array
    }
  }

  private static void maxHeapify(int[] a, int i, int n) {
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    int index = i; // LARGEST of the node and its children, so far the node

    if (left < n && a[left] > a[i]) { // LEFT child bigger
      index = left;
    }

    if (right < n && a[right] > a[index]) { // RIGHT child bigger still
      index = right;
    }

    if (index != i) {
      swap(a, i, index); // SWAP the larger child up
      maxHeapify(a, index, n); // SINK on into that child's subtree
    }
  }

  private static void swap(int[] a, int i, int j) {
    int temp = a[i]; // SWAP a[i] and a[j]
    a[i] = a[j];
    a[j] = temp;
  }

  public static void main(String[] args) {
    int[] a = { 3, 5, 9, 6, 8, 20, 10, 12, 18, 9 };
    System.out.println(Arrays.toString(a)); // [3, 5, 9, 6, 8, 20, 10, 12, 18, 9]
    convert(a);
    System.out.println(Arrays.toString(a)); // [20, 18, 10, 12, 9, 9, 3, 5, 6, 8]
  }
}