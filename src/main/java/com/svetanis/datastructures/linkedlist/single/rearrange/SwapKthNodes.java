package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// Given a singly linked list, swap kth node from beginning with kth node from end. 
// Swapping of data is not allowed, only pointers should be changed. 

public final class SwapKthNodes {

  public static Node swapKth(Node head, int k) {

    Node currX = head;
    Node prevX = null;
    while (--k >= 0) {
      prevX = currX;
      currX = currX.next;
    }

    Node node = currX;
    Node currY = head;
    Node prevY = null;
    while (node != null) {
      node = node.next;
      prevY = currY;
      currY = currY.next;
    }

    if (prevX != null) {
      prevX.next = currY;
    } else {
      // prevX == null : currX is head
      // after swap, currX will become head
      head = prevX;
    }

    // same thing applies to prevY
    if (prevY != null) {
      prevY.next = currX;
    } else {
      head = prevY;
    }

    // swap next pointers of x and y
    Node temp = currX.next;
    currX.next = currY.next;
    currY.next = temp;
    return head;
  }

  public static void main(String[] args) {
    // 1->2->3->4->5->6->7->8

    Node head = null;
    for (int i = 8; i >= 1; --i) {
      head = insertAtHead(head, i);
    }
    print(head);

    head = swapKth(head, 1);
    print(head);

    head = swapKth(head, 2);
    print(head);

    head = swapKth(head, 3);
    print(head);

    head = swapKth(head, 4);
    print(head);

    head = swapKth(head, 5);
    print(head);

    head = swapKth(head, 6);
    print(head);

    head = swapKth(head, 7);
    print(head);

    head = swapKth(head, 8);
    print(head);

    head = swapKth(head, 9);
    print(head);
  }
}

