package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 369. Plus One Linked List
//
// Input: head -- the first node of a singly linked list; each node holds one digit 0 to 9 of a
// number, most significant digit first (1 2 9 is 129), with no leading zeros except the number
// 0 itself. Returns the head of the list after adding one to the number (1 3 0); the digits are
// changed in place.
//
// The one idea: addition on paper starts at the last digit. A recursive call goes down to the end
// of the list first and does its work on the way back, so the digits are handled last to first
// without reversing anything. Each call returns its carry: the 1 handed on to the digit before
// it when its sum reaches 10. Past the last node the call returns 1, which is the one being
// added. A carry left over at the head becomes a new first digit (9 9 becomes 1 0 0).
//
// Time: O(n) -- one call per node
// Space: O(n) -- one stack frame per node
//
// Siblings: AddOneDummy finds the last digit that is not 9 in one forward walk, with O(1) space.
// AddOneIterative reverses the list, adds with a carry and reverses back.

public final class AddOneRecursive {

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
		if (head == null) { // BASE
			return 1; // past the last digit: the one being added
		}
		int res = head.val + addWithCarry(head.next); // DOWN first: add on the way back
		head.val = res % 10; // KEEP the last digit of the sum
		return res / 10; // CARRY: 1 when the digit overflowed
	}

	public static void main(String[] args) {
		ListNode head = fromList(Arrays.asList(1, 9, 9, 9));
		print(addOne(head)); // 2 0 0 0

		ListNode head2 = fromList(Arrays.asList(1, 2, 9, 4));
		print(addOne(head2)); // 1 2 9 5

		ListNode head3 = fromList(Arrays.asList(1, 2, 9));
		print(addOne(head3)); // 1 3 0

		ListNode head4 = fromList(Arrays.asList(9, 9));
		print(addOne(head4)); // 1 0 0
	}
}
