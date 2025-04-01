package com.svetanis.datastructures.linkedlist.single.sum;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 369. Plus One Linked List

public final class AddOneDummy {
	// Time Complexity: O(n)

	public static Node addOne(Node head) {
		Node dummy = new Node(0);
		dummy.next = head;
		Node node = dummy;
		while (head != null) {
			if (head.data != 9) {
				node = head;
			}
			head = head.next;
		}
		node.data += 1;
		Node curr = node.next;
		while (curr != null) {
			curr.data = 0;
			curr = curr.next;
		}
		return dummy.data == 1 ? dummy : dummy.next;
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
