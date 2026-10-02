package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 2816. Double a Number Represented as a Linked List
//
// The list holds the digits of a number with no leading zeros, most significant digit first
// (1 8 9 is 189). Return the number times two as a list in the same order (3 7 8).
//
// Doubling starts at the last digit, but a singly linked list can only be walked from the
// first. So reverse the list, then multiply each digit by 2 and add the carry: the tens part
// of the previous product, handed on to the next digit. Each product's last digit becomes a
// new node of the answer, built last-first behind a fake node. A carry left after the last
// digit becomes one more digit. Reverse the answer to put it most significant first.
// The input list is left reversed, so the caller's head node is then its last node.

public final class DoubleNumber {
	// Time Complexity: O(n), three passes
	// Space Complexity: O(1), not counting the answer list

	public static ListNode doubleNum(ListNode head) {
		head = reverse(head); // least significant digit first; this reverses the caller's list too
		ListNode dummy = new ListNode();
		ListNode curr = dummy;
		int multiplier = 2;
		int carry = 0;
		while (head != null) {
			int product = head.val * multiplier + carry;
			carry = product / 10;
			curr.next = new ListNode(product % 10);
			curr = curr.next;
			head = head.next;
		}
		if (carry > 0) { // one more digit at the front (9 9 9 becomes 1 9 9 8)
			curr.next = new ListNode(carry);
		}
		return reverse(dummy.next);
	}

	private static ListNode reverse(ListNode head) {
		ListNode dummy = new ListNode();
		ListNode curr = head;
		while (curr != null) {
			ListNode next = curr.next;
			curr.next = dummy.next;
			dummy.next = curr;
			curr = next;
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		ListNode num1 = fromList(asList(1, 8, 9));
		Nodes.print(doubleNum(num1)); // 3 7 8

		ListNode num2 = fromList(asList(9, 9, 9));
		Nodes.print(doubleNum(num2)); // 1 9 9 8
	}
}
