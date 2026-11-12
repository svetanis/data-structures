package com.svetanis.datastructures.linkedlist.single;

// One node of a singly linked list: a value and the arrow to the next node. It matches
// LeetCode's ListNode: the fields are val and next, and they are public on purpose, so a solution
// method written against it can be pasted into LeetCode. Two differences: new ListNode() holds
// -1 where LeetCode's holds 0, and this class adds a copy constructor (it copies val and next,
// so the copy shares the rest of the list) and appendToTail.

public final class ListNode {

  public int val;
  public ListNode next;

  public ListNode() {
    this(-1);
  }

  public ListNode(int val) {
    this(val, null);
  }

  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }

  public ListNode(ListNode node) {
    this(node.val, node.next);
  }

  public void appendToTail(int val) {
    ListNode end = new ListNode(val);
    ListNode current = this;
    while (current.next != null) { // WALK to the last node
      current = current.next;
    }
    current.next = end; // LINK the new node after the last one
  }

  @Override
  public String toString() {
    return Integer.toString(this.val); // RETURN: the value alone, so print shows 1 2 3
  }
}