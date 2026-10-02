package com.svetanis.datastructures.linkedlist.single.sum;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 369. Plus One Linked List
//
// Each node holds one digit of a number, most significant digit first (1 2 9 is 129).
// Add one to the number in place and return the head (1 3 0).
//
// Addition starts at the last digit. A recursive call goes down to the end of the list
// first and does its work on the way back, so the digits are handled last to first without
// reversing anything. Each call returns its carry: the 1 handed on to the digit before it
// when its sum reaches 10. Past the last node the call returns 1, which is the one being
// added. A carry left over at the head becomes a new first digit (9 9 becomes 1 0 0).

public final class AddOneRecursive {
	// Time Complexity: O(n), one call per node
	// Space Complexity: O(n), one stack frame per node

	public static ListNode addOne(ListNode head) {
		int carry = addWithCarry(head);
		if (carry > 0) { // every digit was 9: one more digit is needed in front
			ListNode node = new ListNode(carry);
			node.next = head;
			return node;
		}
		return head;
	}

	// adds the carry from the rest of the list to this digit, returns the carry for the digit before
	private static int addWithCarry(ListNode head) {
		if (head == null) {
			return 1; // past the last digit: the one being added
		}
		int res = head.val + addWithCarry(head.next);
		head.val = res % 10;
		return res / 10;
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
