package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.isNotNull;
import static com.svetanis.datastructures.linkedlist.single.Nodes.isNull;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// given a head of a Singly LinkedList,
// find the middle node of the SLL

public final class MiddleNode {
	// Time Complexity: O(n)

	public static Node middle(Node head) {
		if (isNull(head)) {
			return null;
		}
		Node slow = head;
		Node fast = head;
		while (isNotNull(fast) && isNotNull(fast.next)) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	public static Node middleNode(Node head) {
		if (isNull(head)) {
			return head;
		}
		Node slow = head;
		Node fast = head.next;
		while (isNotNull(fast)) {
			fast = fast.next;
			if (isNotNull(fast)) {
				slow = slow.next;
				fast = fast.next;
			}
		}
		return slow;
	}

	public static void main(String[] args) {
		Node head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
		print(head);
		System.out.println(middle(head));
	}
}
