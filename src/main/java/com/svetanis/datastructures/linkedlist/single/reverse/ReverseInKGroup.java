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
// row. Each group is LC 92 (ReverseSubList) unchanged: before is the node in front of the
// group, the reversal is the same four lines counted k turns, and both ends are sewn back, the
// tail first. Two lines are new. Before reversing, start names the group's first node, which
// becomes its last; after sewing, before moves to start, the node in front of the next group.
// And before each group, a look-ahead steps k nodes from before: a node means a full group,
// null means fewer than k are left, and the loop stops with them as they are. The look-ahead
// must stop at null itself, or it reads null.next whenever fewer than k - 1 nodes are left.
//
// Time: O(n) -- every node is stepped over twice, once by the look-ahead and once by the
// reversal.
// Space: O(1) -- a few markers; there is no recursion, so n = 5000 needs no deep stack.
//
// Siblings: ReverseInKGroupCutAndReverse also looks ahead, but cuts each group off and reverses it
// as a list of its own. ReverseInKGroupMoveToFront counts all the nodes first, then moves nodes one
// at a time to the front of their group. ReverseInKGroupRecursive reverses the rest of the list
// first and builds each group in front of it. ReverseInKGroupLeftoverToo and
// ReverseInKGroupLeftoverTooRecursive reverse a short last group too, which LC 25 does not want.

public final class ReverseInKGroup {

  public static ListNode reverse(ListNode head, int k) {
    ListNode dummy = new ListNode(0, head); // FAKE: the first group gets a node before it
    ListNode before = dummy; // the node before the current group
    while (hasFullGroup(before, k)) { // AHEAD: stop when fewer than k nodes are left
      ListNode start = before.next; // NAME: the group's first node, its last once reversed
      ListNode prev = null;
      ListNode curr = before.next;
      for (int i = 0; i < k; i++) { // COUNT: k turns
        ListNode next = curr.next; // SAVE
        curr.next = prev; // FLIP
        prev = curr; // KEEP
        curr = next; // MOVE
      }
      before.next.next = curr; // SEW TAIL first: reached through before's old arrow
      before.next = prev; // SEW FRONT: before -> the group's new first node
      before = start; // NEXT: the old first node sits before the next group
    }
    return dummy.next; // RETURN: dummy.next, since the first group changes the head
  }

  private static boolean hasFullGroup(ListNode before, int k) {
    ListNode ahead = before; // AHEAD: a temporary marker, starting on before
    for (int step = 0; step < k && ahead != null; step++) { // stop at null: null.next throws
      ahead = ahead.next;
    }
    return ahead != null; // a node: the group's last node exists; null: the group is short
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
    print(reverse(head, 3)); // 3 2 1 6 5 4 7 8

    ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
    print(reverse(head1, 2)); // 2 1 4 3 5

    ListNode head2 = fromList(asList(1, 2, 3, 4, 5));
    print(reverse(head2, 3)); // 3 2 1 4 5

    ListNode head3 = fromList(asList(1, 2, 3, 4));
    print(reverse(head3, 3)); // 3 2 1 4 -- one node left: the look-ahead meets null early
  }
}
