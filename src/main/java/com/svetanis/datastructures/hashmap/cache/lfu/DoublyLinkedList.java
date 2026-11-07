package com.svetanis.datastructures.hashmap.cache.lfu;

// The list of nodes that share one use count, for LfuCache.
//
// Input: nodes added at the front and removed from the back or from anywhere. Returns, from
// removeLast, the node at the back, or null when the list is empty.
//
// The one idea: the most recently used node of a count sits at the front, so the eviction victim
// among them is at the back. Two sentinel nodes, head and tail, are always there, so no splice
// ever has to check for a missing neighbour.
//
// Time: O(1) per operation -- a fixed number of pointer changes.
// Space: O(1) besides the nodes -- the two sentinels.

public final class DoublyLinkedList {

	private Node head;
	private Node tail;

	public DoublyLinkedList() {
		this.head = new Node(-1, -1);
		this.tail = new Node(-1, -1);
		head.next = tail;
		tail.prev = head;
	}

	public boolean isEmpty() {
		return head.next == tail; // EMPTY: only the two sentinels
	}

	public void addFirst(Node node) {
		node.prev = head; // LINK: between head and the old first node
		node.next = head.next;
		head.next.prev = node;
		head.next = node;
	}

	public Node removeLast() {
		if (isEmpty()) {
			return null; // EMPTY: nothing to remove
		}
		return remove(tail.prev);
	}

	public Node remove(Node node) {
		node.prev.next = node.next; // UNLINK: the neighbours point past it
		node.next.prev = node.prev;
		node.next = null;
		node.prev = null;
		return node;
	}
}
