package com.svetanis.datastructures.linkedlist.single;

// 707. Design Linked List
//
// Input: a list of ints with get(index), addAtHead(val), addAtTail(val), addAtIndex(index, val)
// and deleteAtIndex(index), where index 0 is the first node. get returns -1 for an index outside
// the list. addAtIndex with an index past the end, and deleteAtIndex with an index outside the
// list, change nothing. On LeetCode: 0 <= index, val <= 1000, at most 2000 calls.
//
// The one idea: one node holds a value and the arrow to the next node. The change for every
// insert and every delete is the same: set the next arrow of the node just before the position.
// The first real node has no node before it, so a fake node sits in front of it; then index 0
// is no special case. size is kept up to date on every change, so checking an index needs no walk.
//
// Time: O(index) per call -- a walk from the fake node to the position; addAtTail is O(n) because
//   its position is the end.
// Space: O(n) -- one node per value held.

public class LinkedList {

	private int size;
	private ListNode dummyHead;

	public LinkedList() {
		this.dummyHead = new ListNode(0);
	}

	public int get(int index) {
		if (index < 0 || index >= size) {
			return -1; // outside the list
		}
		ListNode node = dummyHead.next;
		while (index-- > 0) { // WALK index nodes from the first real node
			node = node.next;
		}
		return node.val;
	}

	public void addAtHead(int val) {
		addAtIndex(0, val);
	}

	public void addAtTail(int val) {
		addAtIndex(size, val);
	}

	public void addAtIndex(int index, int val) {
		if (index > size) { // index == size is allowed: it adds after the last node
			return;
		}
		ListNode node = dummyHead;
		while (index-- > 0) { // index steps from the fake node: the node before the position
			node = node.next;
		}
		node.next = new ListNode(val, node.next); // LINK: the new node takes over the old next
		size++;
	}

	public void deleteAtIndex(int index) {
		if (index < 0 || index >= size) {
			return; // outside the list: nothing to delete
		}
		ListNode node = dummyHead;
		while (index-- > 0) { // WALK to the node before the one to delete
			node = node.next;
		}
		ListNode toDelete = node.next;
		node.next = toDelete.next; // SKIP the deleted node
		toDelete.next = null;
		size--;
	}

	public static void main(String[] args) {
		LinkedList ll = new LinkedList();
		ll.addAtHead(1);
		ll.addAtTail(3);
		ll.addAtIndex(1, 2);
		System.out.println(ll.get(1)); // 2
		ll.deleteAtIndex(1);
		System.out.println(ll.get(1)); // 3
	}

	// a node type of its own; inside this class it is used instead of the package's ListNode
	private static class ListNode {

		private int val;
		private ListNode next;

		public ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}

		public ListNode(int val) {
			this(val, null);
		}
	}
}
