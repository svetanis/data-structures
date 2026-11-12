package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 19. Remove Nth Node From End of List
//
// Input: the head of a singly linked list and a number k. LeetCode: the list has sz nodes,
// 1 <= sz <= 30, node values 0..100, and 1 <= k <= sz. Returns: the head of the list after the
// node that is k places from the end is removed (k = 1 is the last node).
//
// The one idea: a list cannot be measured from the end, but two markers can hold a fixed
// distance. One marker is one node of the list. Put fast k nodes ahead of slow, then move both
// one node at a time until fast is on the last node. The distance never changed, so slow is k
// nodes behind the last node, which is the node just before the one to remove. Both markers
// start on a fake node placed before the head, so removing the head is not a special case.
//
// Time: O(n) -- fast walks the list once; slow walks the same distance less k.
// Space: O(1) -- the fake node and two markers.
//
// Siblings: DeleteKthToLastNodeFastToNull solves the same problem; it lets fast run off the end
// onto null instead of stopping on the last node, so its gap is k + 1 instead of k, and it
// throws on a k larger than the list where this file returns the list unchanged.

public final class DeleteKthToLastNodeDummy {

  public static ListNode remove(ListNode head, int k) {
    if (k < 1 || head == null) { // GUARD: nothing to remove; LC 19 never sends these
      return head;
    }
    // 1. create a dummy node
    ListNode dummy = new ListNode(0, head); // FAKE: the head gets a node before it
    // 2. initialize two pointers
    ListNode fast = dummy;
    ListNode slow = dummy;
    // 3. put fast k nodes ahead of slow
    for (int i = 0; fast != null && i < k; ++i) { // GAP: k steps for fast alone
      fast = fast.next;
    }
    // k is larger than the list: there is no kth node from the end, so nothing to remove.
    // LC 19 promises this cannot happen.
    if (fast == null) { // GUARD: ran off the end during the gap
      return head;
    }
    // 4. move both pointers until fast is on the last node
    while (fast.next != null) { // WALK together until fast is on the last node
      slow = slow.next;
      fast = fast.next;
    }
    // 5. remove the target node, the one after slow
    slow.next = slow.next.next; // SKIP: slow is on the node before the one to remove
    // 6. return updated list: dummy.next, since the head itself may have been removed
    return dummy.next; // RETURN: never head, because the head itself may have been removed
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(50, 20, 15, 4, 10, 60));
    print(remove(head, 3)); // 50 20 15 10 60

    ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
    print(remove(head1, 2)); // 1 2 3 5

    ListNode head2 = fromList(asList(1));
    print(remove(head2, 1)); // (empty line)

    ListNode head3 = fromList(asList(1, 2));
    print(remove(head3, 1)); // 1
  }
}
