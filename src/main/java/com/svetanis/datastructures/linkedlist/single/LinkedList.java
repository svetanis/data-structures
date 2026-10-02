package com.svetanis.datastructures.linkedlist.single;

// 707. Design Linked List
//
// A list of ints with get, addAtHead, addAtTail, addAtIndex and deleteAtIndex, where index 0
// is the first node. get returns -1 for an index outside the list. addAtIndex with an index
// past the end, and deleteAtIndex with an index outside the list, change nothing.
//
// A fake node sits in front of the first real node, so every insert and every delete
// changes the next of the node just before the position, even at index 0. size is kept up
// to date on every change, so checking an index needs no walk.

public class LinkedList {
	// Time Complexity: O(index) per call, a walk from the fake node to the position; addAtTail is O(n)
	// Space Complexity: O(n), one node per value held

	private int size;
	private ListNode dummyHead;

	public LinkedList() {
		this.dummyHead = new ListNode(0);
	}

	public int get(int index) {
		if (index < 0 || index >= size) {
			return -1;
		}
		ListNode node = dummyHead.next;
		while (index-- > 0) {
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
		node.next = new ListNode(val, node.next);
		size++;
	}

	public void deleteAtIndex(int index) {
		if (index < 0 || index >= size) {
			return;
		}
		ListNode node = dummyHead;
		while (index-- > 0) {
			node = node.next;
		}
		ListNode toDelete = node.next;
		node.next = toDelete.next;
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
