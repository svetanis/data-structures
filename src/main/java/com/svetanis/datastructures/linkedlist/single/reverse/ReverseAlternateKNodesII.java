package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Alternate k Nodes
//
// Given the head of a linked list and a number k, reverse the first k nodes, leave the next k
// as they are, reverse the k after that, and so on. A short group at the end is reversed if it
// falls on a reversing turn. Return the new head.
//
// One call handles one group, and the flag reverseNextK says whether this group is reversed
// or only walked over. The same loop does both: it always moves prev and curr forward, and
// turns the arrow only when the flag is true. A reversed group's old first node (head) is now
// its last node, so the rest of the list is attached after head. A walked group's last node
// is prev, so the rest is attached after prev. Each call passes the opposite flag to the next.

public final class ReverseAlternateKNodesII {
  // Time Complexity: O(n), each node is either reversed or walked over, once
  // Space Complexity: O(n / k) recursion stack, one call per group

  public static ListNode reverse(ListNode head, int k) {
    return reverse(head, k, true);
  }

  public static ListNode reverse(ListNode head, int k, boolean reverseNextK) {

    if (head == null) {
      return null;
    }

    int count = 1;
    ListNode prev = null;
    ListNode curr = head;

    // the loop serves two purposes
    // 1. if reverseNextK is true, then it reverses the k nodes
    // 2. if reverseNextK is false, then it moves the current pointer
    while (curr != null && count <= k) {
      ListNode next = curr.next;

      // reverse the nodes only if reverseNextK is true
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
    ListNode head = null;
    for (int i = 20; i > 0; i--) {
      head = insertAtHead(head, i);
    }
    print(head); // 1 2 3 ... 20
    head = reverse(head, k);
    print(head); // 3 2 1 4 5 6 9 8 7 10 11 12 15 14 13 16 17 18 20 19

    ListNode head2 = null;
    for (int i = 20; i > 0; i--) {
      head2 = insertAtHead(head2, i);
    }
    print(head2); // 1 2 3 ... 20
    head2 = reverse(head2, k);
    print(head2); // 3 2 1 4 5 6 9 8 7 10 11 12 15 14 13 16 17 18 20 19
  }
}
