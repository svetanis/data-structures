package com.svetanis.datastructures.linkedlist.single.flatten;

import static com.svetanis.datastructures.linkedlist.single.flatten.Nodes.printDown;
import static com.svetanis.datastructures.linkedlist.single.flatten.Nodes.push;

// Flattening a Linked List
//
// Input: the head of a top row, a list joined by next pointers. Each node of the top row also
// starts a column: a list joined by down pointers, sorted from smallest to largest. Returns
// one list, joined by down pointers, that holds every node of every column in sorted order.
// The next pointers are not cleared. There is no size limit in the problem; the recursion
// below is one frame per node placed, so it suits a few thousand nodes.
//
// The one idea: one column is already a sorted list, and two sorted lists merge into one, as in
// Merge Two Sorted Lists (LC 21): of the two front nodes, the smaller goes first and the rest
// is merged behind it, along down pointers. So flatten first turns everything after the first
// column into one sorted down list (the same job on a shorter row), then merges the first
// column into it.
//
// 5 -> 10 -> 19 -> 28
// |     |     |     |
// 7    20    22    35
// |           |     |
// 8          50    40
// |                 |
// 30               45

// 5->7->8->10->19->20->22->28->30->35->40->45->50
//
// Time: O(N * k) -- N nodes in all and k columns: each of the k merges can walk the whole merged
//   list.
// Space: O(N) -- merge calls itself once for every node it places.

public final class FlattenList {

  public static Node flatten(Node root) {

    // base case
    if (root == null || root.next == null) {
      return root; // BASE: no column, or one column: already one sorted list
    }

    root.next = flatten(root.next); // every column to the right, as one sorted down list
    // merge this list with
    // the list on next side
    return merge(root, root.next); // MERGE the first column into the rest
  }

  private static Node merge(Node node1, Node node2) {
    // if first list is empty,
    // the second list is result
    if (node1 == null) {
      return node2; // BASE
    }

    // if second list is empty,
    // the first list is result
    if (node2 == null) {
      return node1; // BASE
    }

    // compare the data members of
    // head nodes of both lists and
    // put the smaller one in result
    Node merged = null;
    if (node1.val < node2.val) {
      merged = node1; // TAKE the smaller front node
      merged.down = merge(node1.down, node2); // the rest is merged behind it
    } else {
      merged = node2;
      merged.down = merge(node1, node2.down);
    }
    return merged;
  }

  public static void main(String[] args) {
    Node root = null;
    root = push(root, 30);
    root = push(root, 8);
    root = push(root, 7);
    root = push(root, 5);
    root.next = push(root.next, 20);
    root.next = push(root.next, 10);
    root.next.next = push(root.next.next, 50);
    root.next.next = push(root.next.next, 22);
    root.next.next = push(root.next.next, 19);
    root.next.next.next = push(root.next.next.next, 45);
    root.next.next.next = push(root.next.next.next, 40);
    root.next.next.next = push(root.next.next.next, 35);
    root.next.next.next = push(root.next.next.next, 28);

    root = flatten(root);
    printDown(root); // 5 7 8 10 19 20 22 28 30 35 40 45 50
  }
}
