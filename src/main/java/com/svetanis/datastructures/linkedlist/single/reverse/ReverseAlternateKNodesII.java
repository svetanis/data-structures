package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class ReverseAlternateKNodesII {

  public static Node reverse(Node head, int k) {
    return reverse(head, k, true);
  }

  public static Node reverse(Node head, int k, boolean reverseNextK) {

    if (head == null) {
      return null;
    }

    int count = 1;
    Node prev = null;
    Node curr = head;

    // the loop serves two purposes
    // 1. if reverseNextK is true, then it reverses the k nodes
    // 2. if reverseNextK is false, then it moves the current pointer
    while (curr != null && count <= k) {
      Node next = curr.next;

      // reverse the nodes only if b is true
      if (reverseNextK) {
        curr.next = prev;
      }
      prev = curr;
      curr = next;
      count++;
    }

    // 3. if reverseNextK is true, then node is the kth node.
    // so attach rest of the list after node
    // 4. after attaching, return the new head
    if (reverseNextK) {
      head.next = reverse(curr, k, !reverseNextK);
      return prev;
    } else {
      // attach rest of the list after prev
      prev.next = reverse(curr, k, !reverseNextK);
      return head;
    }
  }

  public static void main(String[] args) {
    int k = 3;
    Node head = null;
    for (int i = 20; i > 0; i--) {
      head = insertAtHead(head, i);
    }
    print(head);
    head = reverse(head, k);
    print(head);

    Node head2 = null;
    for (int i = 20; i > 0; i--) {
      head2 = insertAtHead(head2, i);
    }
    print(head2);
    head2 = reverse(head2, k);
    print(head2);
  }
}
