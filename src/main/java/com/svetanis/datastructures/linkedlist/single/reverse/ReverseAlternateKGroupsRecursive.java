package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Alternate k Nodes
//
// Input: the head of a singly linked list and a number k >= 1. Not a LeetCode problem, so there
// are no official limits; the lists here are a few dozen nodes. Returns: the head after the
// first k nodes are reversed, the next k are left as they are, the k after that are reversed,
// and so on. A short group at the end is reversed if it falls on a reversing turn.
//
// The one idea: a group is k nodes in a row; one node holds a value and an arrow to the next
// node. One call handles two groups. It reverses the first k nodes with the usual four lines
// (save the next node, turn the arrow, move prev, move curr). head was the group's first node,
// so after the reversal it is the group's last node, and it is pointed at the first node of the
// group to leave alone. curr then walks to the last node of that group, and that node is
// pointed at whatever the recursive call returns for the rest of the list.
//
// Time: O(n) -- each node is either reversed or walked over, once.
// Space: O(n / k) -- recursion stack, one waiting call for every two groups.
//
// Siblings: ReverseAlternateKGroupsFlag does the same with one call per group and a flag that says
// whether this group is reversed; ReverseAlternateKGroups does it with a loop and O(1) space.

public final class ReverseAlternateKGroupsRecursive {

  public static ListNode reverse(ListNode head, int k) {

    int count = 0;
    ListNode prev = null;
    ListNode curr = head;

    // 1. reverse first k nodes of the linked list
    while (curr != null && count < k) { // REVERSE: up to k nodes, fewer at the end of the list
      ListNode next = curr.next; // SAVE: the next line destroys the only way forward
      curr.next = prev; // FLIP: this node points at the one behind it
      prev = curr; // KEEP: the node just finished
      curr = next; // MOVE: by the saved name
      count++;
    }

    // 2. now head points to the kth node.
    // so change next of head to (k + 1)-th node
    if (head != null) {
      head.next = curr; // SEW: head is the group's last node now; it leads to the group left alone
    }

    // 3. we don't want to reverse next k nodes.
    // so move the pointer to skip next k nodes
    count = 0;
    while (count < k - 1 && curr != null) { // SKIP: walk to the last node of the group left alone
      curr = curr.next;
      count++;
    }

    // 4. recursively call for the list starting from current.next.
    // and make rest of the list as next of first node
    if (curr != null) {
      curr.next = reverse(curr.next, k); // RECURSE: the rest of the list, attached after the skipped group
    }
    // 5. prev is new head of the input list
    return prev; // RETURN: the first group's new first node
  }

  public static void main(String[] args) {
    int k = 3;
    ListNode head = null;
    for (int i = 20; i > 0; i--) {
      head = insertAtHead(head, i);
    }
    print(head); // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
    head = reverse(head, k);
    print(head); // 3 2 1 4 5 6 9 8 7 10 11 12 15 14 13 16 17 18 20 19

    ListNode head2 = null;
    for (int i = 20; i > 0; i--) {
      head2 = insertAtHead(head2, i);
    }
    print(head2); // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
    head2 = reverse(head2, k);
    print(head2); // 3 2 1 4 5 6 9 8 7 10 11 12 15 14 13 16 17 18 20 19
  }
}
