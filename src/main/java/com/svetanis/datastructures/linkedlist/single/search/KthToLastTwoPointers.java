package com.svetanis.datastructures.linkedlist.single.search;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Kth Node from the End, two pointers
//
// Input: head -- the first node of a singly linked list, or null; k -- the place from the end,
// where k = 1 is the last node. Returns the node k places from the end of the list, or null when
// k is 0 or less or larger than the number of nodes. Not a LeetCode problem, so there are no
// constraints.
//
// The one idea: a node cannot tell how far it is from the end, but two nodes a fixed distance
// apart can. Both pointers start on the head. fast first moves k - 1 nodes ahead, so slow and
// fast are the first and last nodes of a stretch of k nodes. Then both move one node at a time
// until fast is on the last node. The stretch has not changed length, so it now ends at the last
// node, and slow, at its start, is k places from the end.
//
// Time: O(n) -- fast walks to the end once and slow follows it
// Space: O(1) -- two pointers
//
// Siblings: KthToLastRecursive finds the same node by counting on the way back out of a recursion
// that is n calls deep; KthNode counts from the head instead.

public final class KthToLastTwoPointers {

  public static ListNode kthToLast(ListNode head, int k) {
    if (k <= 0 || head == null) { // BASE: no such place from the end
      return null;
    }

    ListNode fast = head;
    ListNode slow = head;

    // advance fast by K - 1 nodes
    for (int i = 0; i < k - 1; ++i) { // GAP: open a stretch of k nodes between slow and fast
      if (fast == null) {
        return null;
      }
      fast = fast.next;
    }

    // another error check
    if (fast == null) { // k is larger than the list: the last step left the list
      return null;
    }
    // now, move fast and slow at same speed
    // when fast hits the end,
    // slow will be at the right element
    while (fast.next != null) { // STOP with fast on the last node, not past it
      slow = slow.next; // SLIDE the stretch one node to the right
      fast = fast.next;
    }
    return slow; // RETURN: the start of the stretch that ends at the last node
  }

  public static void main(String[] args) {
    ListNode head = fromList(Arrays.asList(50, 20, 15, 4, 10, 60));
    print(head); // 50 20 15 4 10 60
    System.out.println(kthToLast(head, 3)); // 4
    System.out.println(kthToLast(head, 5)); // 20
    System.out.println(kthToLast(head, 7)); // null
  }
}
