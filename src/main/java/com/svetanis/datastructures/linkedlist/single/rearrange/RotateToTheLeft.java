package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.datastructures.linkedlist.single.search.KthNode.kthNode;

import com.svetanis.datastructures.linkedlist.single.Node;

// given a Singly LinkedList,
// rotate the SLL counter-clockwise 
// by k nodes

public final class RotateToTheLeft {

  public static Node rotate(Node head, int k) {
    // Time Complexity: O(n)

    if (k <= 0 || head == null || head.next == null) {
      return head;
    }

    Node curr = kthNode(head, k - 1);

    // if current is null, k is greater
    // than or equal to count of nodes
    if (curr == null) {
      return head;
    }

    // current points to (k - 1)-th node
    // store it in a variable
    Node kthNode = curr;

    // current will point to
    // last node after this loop
    while (curr.next != null) {
      curr = curr.next;
    }
    // change next of last node
    // to previous head
    curr.next = head;

    // change head to k-th node
    head = kthNode.next;

    // change next of kth node to null
    kthNode.next = null;

    return head;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(10, 20, 30, 40, 50, 60));
    print(head);
    print(rotate(head, 4));
  }
}
