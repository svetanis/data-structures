package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class MedianSortedLL {

  public static double median(Node head) {
    if (head == null) {
      return -1.0;
    }
    
    Node prev = head;
    Node slow = head;
    Node fast = head;
    while (fast != null && fast.next != null) {
      prev = slow;
      slow = slow.next;
      fast = fast.next.next;
    }

    if (fast != null) {
      return slow.data;
    } else {
      return (prev.data + slow.data) / 2.0;
    }
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(1, 2, 3, 4, 5, 6));
    print(head);
    System.out.println(median(head));
  }
}
