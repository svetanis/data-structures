package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 19. Remove Nth Node From End of List
//
// Remove the node that is k places from the end (k = 1 is the last node) and return the
// head.
//
// A list cannot be measured from the end, but two pointers can hold a fixed distance. Put
// fast k nodes ahead of slow, then move both until fast is on the last node: slow is then
// k + 1 places from the end, just before the node to remove. Both start on a fake node, so
// removing the head is not a special case.

public final class DeleteKthToLastNodeDummy {
  // Time Complexity: O(n), one pass
  // Space Complexity: O(1)

  public static ListNode remove(ListNode head, int k) {
    if (k < 1 || head == null) {
      return head;
    }
    // 1. create a dummy node
    ListNode dummy = new ListNode(0, head);
    // 2. initialize two pointers
    ListNode fast = dummy;
    ListNode slow = dummy;
    // 3. put fast k nodes ahead of slow
    for (int i = 0; fast != null && i < k; ++i) {
      fast = fast.next;
    }
    // k is larger than the list: there is no kth node from the end, so nothing to remove.
    // LC 19 promises this cannot happen.
    if (fast == null) {
      return head;
    }
    // 4. move both pointers until fast is on the last node
    while (fast.next != null) {
      slow = slow.next;
      fast = fast.next;
    }
    // 5. remove the target node, the one after slow
    slow.next = slow.next.next;
    // 6. return updated list: dummy.next, since the head itself may have been removed
    return dummy.next;
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(50, 20, 15, 4, 10, 60));
    print(remove(head, 3)); // 50 20 15 10 60

    ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
    print(remove(head1, 2)); // 1 2 3 5

    ListNode head2 = fromList(asList(1));
    print(remove(head2, 1)); // []

    ListNode head3 = fromList(asList(1, 2));
    print(remove(head3, 1)); // 1
  }
}
