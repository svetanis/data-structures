package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.java.base.utils.IntWrapper;

public final class KthToLastRecursive {

  // Recursive with wrapper class to pass i by reference
  public static Node kthToLast(Node node, int k, IntWrapper i) {
    if (node == null || k == 0) {
      return null;
    }

    Node current = kthToLast(node.next, k, i);
    i.value = i.value + 1;

    if (i.value == k) {
      return node;
    }
    return current;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
    print(head);
    System.out.println(kthToLast(head, 3, newIntWrapper()));
  }
}
