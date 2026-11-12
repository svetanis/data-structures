package com.svetanis.datastructures.linkedlist.single.search;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.isNotNull;
import static com.svetanis.datastructures.linkedlist.single.Nodes.isNull;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 876. Middle of the Linked List
//
// Input: head -- the first node of a singly linked list (an empty list is accepted here and
// returns null). On LeetCode: the list has 1 to 100 nodes, each value 1 to 100. Returns the middle
// node. With an odd count n there is one middle node, at index (n - 1) / 2, counting from 0.
// With an even count there are two. middle returns the second one, at index n / 2, which is what
// LC 876 asks for. middleNode returns the first one, at index (n - 1) / 2. The two methods
// differ on even counts on purpose.
//
// The one idea: no node knows how long the list is, but two walkers of different speed can find
// the halfway point without counting. slow moves one node per turn and fast two, so fast has
// always gone twice as far as slow. When fast cannot go further, slow is halfway. middle starts
// both on the head. middleNode starts fast one node ahead, so on an even count fast runs out one
// turn sooner and slow stops on the first middle instead of the second.
//
// Time: O(n) -- fast makes about n / 2 turns, each two steps, and slow follows
// Space: O(1) -- two pointers
//
// Siblings: MedianSortedLL walks the same way but returns the middle value (or the average of
// the two middle values) instead of a node.

public final class MiddleNode {

  public static ListNode middle(ListNode head) {
    if (isNull(head)) { // BASE: an empty list has no middle
      return null;
    }
    ListNode slow = head;
    ListNode fast = head;
    while (isNotNull(fast) && isNotNull(fast.next)) { // STOP when fast cannot take two steps
      slow = slow.next; // slow takes one step
      fast = fast.next.next; // fast takes two
    }
    return slow; // RETURN: halfway; on an even count, the second middle
  }

  public static ListNode middleNode(ListNode head) {
    if (isNull(head)) { // BASE: an empty list has no middle
      return head;
    }
    ListNode slow = head;
    ListNode fast = head.next; // one node ahead: an even count stops on the first middle
    while (isNotNull(fast)) {
      fast = fast.next; // fast takes its first step of the turn
      if (isNotNull(fast)) { // slow moves only when fast completed both steps
        slow = slow.next;
        fast = fast.next; // fast takes its second step
      }
    }
    return slow; // RETURN: halfway; on an even count, the first middle
  }

  public static void main(String[] args) {
    ListNode head = fromList(Arrays.asList(50, 20, 15, 4, 10, 60));
    print(head); // 50 20 15 4 10 60
    System.out.println(middle(head)); // 4
    System.out.println(middleNode(head)); // 15
    ListNode odd = fromList(Arrays.asList(1, 2, 3, 4, 5));
    System.out.println(middle(odd)); // 3
    System.out.println(middleNode(odd)); // 3
  }
}
