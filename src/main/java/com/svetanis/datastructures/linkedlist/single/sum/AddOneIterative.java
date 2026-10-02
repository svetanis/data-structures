package com.svetanis.datastructures.linkedlist.single.sum;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.datastructures.linkedlist.single.reverse.ReverseIterative.reverse;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 369. Plus One Linked List
//
// Each node holds one digit of a number, most significant digit first (1 2 9 is 129).
// Add one to the number in place and return the head (1 3 0).
//
// Addition starts at the last digit, but a singly linked list can only be walked from the
// first. So reverse the list, add the way it is done on paper, then reverse it back. The
// carry is the 1 handed on to the next digit when a digit's sum reaches 10; here it starts
// at 1, because that is the one being added. A carry left over after the last digit becomes a new node, which is the
// new first digit once the list is reversed back (9 9 becomes 1 0 0).

public final class AddOneIterative {
	// Time Complexity: O(n), three passes
	// Space Complexity: O(1), the digits are changed in place

	public static ListNode addOne(ListNode head) {
		head = reverse(head); // least significant digit first
		head = addOneUtil(head);
		return reverse(head); // most significant digit first again
	}

	private static ListNode addOneUtil(ListNode head) {

		ListNode result = head;
		ListNode node = head; // the last node visited, where a leftover carry is attached
		int sum = 0;
		int carry = 1; // the one being added

		while (head != null) {
			sum = carry + head.val;
			carry = (sum >= 10) ? 1 : 0;
			sum = sum % 10;
			head.val = sum;
			node = head;
			head = head.next;
		}

		if (carry > 0) { // every digit was 9: one more digit is needed
			node.next = new ListNode(carry);
		}
		return result;
	}

	public static void main(String[] args) {
		ListNode head = fromList(newArrayList(1, 9, 9, 9));
		print(addOne(head)); // 2 0 0 0

		ListNode head2 = fromList(newArrayList(1, 2, 9, 4));
		print(addOne(head2)); // 1 2 9 5

		ListNode head3 = fromList(newArrayList(1, 2, 9));
		print(addOne(head3)); // 1 3 0
	}
}
