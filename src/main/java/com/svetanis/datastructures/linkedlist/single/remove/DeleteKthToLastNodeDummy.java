package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 19. Remove Nth Node From End of List

public final class DeleteKthToLastNodeDummy {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static Node remove(Node head, int k) {
    if (k < 1 || head == null) {
      return head;
    }
    // 1. create a dummy node
    Node dummy = new Node(0, head);
    // 2. initialize two pointers
    Node fast = dummy;
    Node slow = dummy;
    // 3. advance fast pointer by K - 1 nodes
    for (int i = 0; fast != null && i < k; ++i) {
      fast = fast.next;
    }
    // 4. move both pointers
    while (fast.next != null) {
      slow = slow.next;
      fast = fast.next;
    }
    // 5. remove the target node
    slow.next = slow.next.next;
    // 6. return updated list
    return dummy.next;
  }

  public static void main(String[] args) {
    Node head = fromList(asList(50, 20, 15, 4, 10, 60));
    print(remove(head, 3)); // 50 20 15 10 60

    Node head1 = fromList(asList(1, 2, 3, 4, 5));
    print(remove(head1, 2)); // 1 2 3 5

    Node head2 = fromList(asList(1));
    print(remove(head2, 1)); // []

    Node head3 = fromList(asList(1, 2));
    print(remove(head3, 1)); // 1
  }
}
