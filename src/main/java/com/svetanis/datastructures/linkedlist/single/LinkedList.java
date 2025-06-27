package com.svetanis.datastructures.linkedlist.single;

// 707. Design Linked List

public class LinkedList {

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
		return node.value;
	}

	public void addAtHead(int val) {
		addAtIndex(0, val);
	}

	public void addAtTail(int val) {
		addAtIndex(size, val);
	}

	public void addAtIndex(int index, int val) {
		if (index > size) {
			return;
		}
		ListNode node = dummyHead;
		while (index-- > 0) {
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

	private static class ListNode {

		private int value;
		private ListNode next;

		public ListNode(int val, ListNode next) {
			this.value = val;
			this.next = next;
		}

		public ListNode(int val) {
			this(val, null);
		}
	}
}
