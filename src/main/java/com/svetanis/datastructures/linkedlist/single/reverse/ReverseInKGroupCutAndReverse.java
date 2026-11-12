package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 25. Reverse Nodes in k-Group
//
// Input: the head of a singly linked list and a number k. LeetCode: 1 <= k <= n <= 5000 nodes,
// values 0..1000. Returns: the head after every group of k nodes, starting from the head, is
// reversed. If the number of nodes is not a multiple of k, the left-out nodes at the end stay
// as they are. Only the nodes move, never their values.
//
// The one idea: one node holds a value and an arrow to the next node; a group is k nodes in a
// row. A fake node in front of the list means every group has a node before it, and before is
// that node. curr walks k nodes ahead of before; if it falls off the end, the last group is
// short and the list is returned as it is. Otherwise the group is cut off after curr, reversed
// on its own as a separate list, and attached back: before is pointed at the reversed group's
// new first node, and start, the group's old first node and now its last, is pointed at the
// rest of the list. start is then the node before the next group.
//
// Time: O(n) -- each node is counted once (the walk to curr) and reversed once.
// Space: O(1) -- a few markers; the reversal is a loop, so n = 5000 needs no deep stack.
//
// Siblings: ReverseInKGroup is the version to carry: the same look-ahead, then LC 92's counted loop
// with no cut. ReverseInKGroupMoveToFront counts all the nodes first, then moves nodes one at a
// time to the front of their group, with no separate reversal. ReverseInKGroupRecursive reverses
// the rest of the list first and builds each group in front of it. ReverseInKGroupLeftoverToo and
// ReverseInKGroupLeftoverTooRecursive reverse a short last group too, which LC 25 does not want.

public final class ReverseInKGroupCutAndReverse {

  public static ListNode reverse(ListNode head, int k) {
    if (k <= 1 || head == null) { // BASE: k = 1 moves nothing
      return head;
    }

    ListNode dummy = new ListNode(0, head); // FAKE: the first group gets a node before it
    ListNode before = dummy; // the node before the current group
    ListNode curr = dummy;
    while (curr != null) { // ONE TURN: one group
      for (int i = 0; i < k && curr != null; i++) { // AHEAD: walk k nodes ahead; curr ends on the group's last node
        curr = curr.next;
      }
      if (curr == null) { // SHORT: fewer than k nodes are left, so they stay as they are
        return dummy.next;
      }
      // temporarily store the next segment
      ListNode temp = curr.next; // SAVE: the first node after the group
      // detach the k nodes from the rest of the list
      curr.next = null; // CUT: the group is now a list of its own
      // start will be the new tail after reversal
      ListNode start = before.next; // the group's first node, its last after the reversal
      // reverse k nodes
      before.next = reverse(start); // SEW FRONT: the reversed group follows before
      // connect the new tail with the temp segment
      start.next = temp; // SEW TAIL: the group's last node leads to the rest
      // move before and curr pointers k nodes ahead
      before = start; // NEXT: the node before the next group
      curr = before;
    }
    return dummy.next; // RETURN: dummy.next, since the first group changes the head
  }

  private static ListNode reverse(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) { // WALK the group, one node per turn
      ListNode next = curr.next; // SAVE
      curr.next = prev; // FLIP
      prev = curr; // KEEP
      curr = next; // MOVE
    }
    return prev; // RETURN: the old last node, now first
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
    print(reverse(head, 3)); // 3 2 1 6 5 4 7 8

    ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
    print(reverse(head1, 2)); // 2 1 4 3 5

    ListNode head2 = fromList(asList(1, 2, 3, 4, 5));
    print(reverse(head2, 3)); // 3 2 1 4 5
  }
}
