package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 2816. Double a Number Represented as a Linked List

public final class DoubleNumber {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node doubleNum(Node head) {
		head = reverse(head);
		Node dummy = new Node();
		Node curr = dummy;
		int multiplier = 2;
		int carry = 0;
		while (head != null) {
			int product = head.data * multiplier + carry;
			carry = product / 10;
			curr.next = new Node(product % 10);
			curr = curr.next;
			head = head.next;
		}
		if (carry > 0) {
			curr.next = new Node(carry);
		}
		return reverse(dummy.next);
	}

	private static Node reverse(Node head) {
		Node dummy = new Node();
		Node curr = head;
		while (curr != null) {
			Node next = curr.next;
			curr.next = dummy.next;
			dummy.next = curr;
			curr = next;
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		Node num1 = fromList(asList(1, 8, 9));
		Nodes.print(doubleNum(num1)); // 3 7 8

		Node num2 = fromList(asList(9, 9, 9));
		Nodes.print(doubleNum(num2)); // 1 9 9 8
	}
}
