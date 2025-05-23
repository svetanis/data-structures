package com.svetanis.datastructures.linkedlist.single.sum;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 369. Plus One Linked List

public final class AddOneRecursive {
	// Time Complexity: O(n)

	public static Node addOne(Node head) {
		int carry = addWithCarry(head);
		if (carry > 0) {
			Node node = new Node(carry);
			node.next = head;
			return node;
		}
		return head;
	}

	private static int addWithCarry(Node head) {
		if (head == null) {
			return 1;
		}
		int res = head.data + addWithCarry(head.next);
		head.data = res % 10;
		return res / 10;
	}

	public static void main(String[] args) {
		Node head = fromList(newArrayList(1, 9, 9, 9));
		print(addOne(head));

		Node head2 = fromList(newArrayList(1, 2, 9, 4));
		print(addOne(head2));

		Node head3 = fromList(newArrayList(1, 2, 9));
		print(addOne(head3));
	}
}
