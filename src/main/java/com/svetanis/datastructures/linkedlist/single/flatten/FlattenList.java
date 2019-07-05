package com.svetanis.datastructures.linkedlist.single.flatten;

import static com.svetanis.datastructures.linkedlist.single.flatten.Nodes.printDown;
import static com.svetanis.datastructures.linkedlist.single.flatten.Nodes.push;

// 5 -> 10 -> 19 -> 28
// |     |     |     |
// 7    20    22    35
// |           |     |
// 8          50    40
// |                 |
// 30               45

// 5->7->8->10->19->20->22->28->30->35->40->45->50

public final class FlattenList {

  public static Node flatten(Node root) {

    // base case
    if (root == null || root.next == null) {
      return root;
    }

    root.next = flatten(root.next);
    // merge this list with
    // the list on next side
    return merge(root, root.next);
  }

  private static Node merge(Node node1, Node node2) {
    // if first list is empty,
    // the second list is result
    if (node1 == null) {
      return node2;
    }

    // if second list is empty,
    // the first list is result
    if (node2 == null) {
      return node1;
    }

    // compare the data members of
    // head nodes of both lists and
    // put the smaller one in result
    Node merged = null;
    if (node1.data < node2.data) {
      merged = node1;
      merged.down = merge(node1.down, node2);
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
    printDown(root);
  }
}
