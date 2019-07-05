package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class MergeTwoSortedIterative {

  public static Node merge(Node head1, Node head2) {
    
    if (head1 == null) {
      return head2;
    }
    
    if (head2 == null) {
      return head1;
    }

    Node head = (head1.data < head2.data) ? head1 : head2;
    
    if (head1.data < head2.data) {
      head1 = head1.next;
    } else {
      head2 = head2.next;
    }

    Node curr = head;
    while (head1 != null || head2 != null) {
      
      if (head1 == null) {
        curr.next = head2;
        return head;
      }
      
      if (head2 == null) {
        curr.next = head1;
        return head;
      }

      if (head1.data < head2.data) {
        curr.next = head1;
        curr = curr.next;
        head1 = head1.next;
      } else {
        curr.next = head2;
        curr = curr.next;
        head2 = head2.next;
      }
    }
    return head;
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 2, 3, 5, 5, 6 };
    int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
    Node head1 = fromArray(a1);
    Node head2 = fromArray(a2);
    Node merged = merge(head1, head2);
    print(merged);
  }
}
