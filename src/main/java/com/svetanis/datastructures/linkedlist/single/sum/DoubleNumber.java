package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 2816. Double a Number Represented as a Linked List
//
// Input: head -- the first node of a non-empty singly linked list; each node holds one digit 0 to
// 9 of a number, most significant digit first (1 8 9 is 189), with no leading zeros except the
// number 0 itself. On LeetCode: 1 to 10^4 nodes. Returns the head of a new list holding the
// number times two in the same order (3 7 8).
//
// The one idea: doubling starts at the last digit, because a carry moves toward the front, but a
// singly linked list can only be walked from the first. So reverse the list, then walk it and
// replace each digit by its double plus the carry: the tens part of the previous product,
// handed on to the next digit. The product's last digit becomes a new node of the answer, built
// last-first behind a fake node, and its tens part (0 or 1) is the next carry. A carry left after
// the last digit becomes one more digit. Reverse the answer to put it most significant first.
// The input list is left reversed, so the caller's head node is then its last node.
//
// Time: O(n) -- three passes: reverse the input, build the answer, reverse the answer
// Space: O(1) -- not counting the answer list
//
// Siblings: AddTwoNumsForward (LC 445) also works on most-significant-first lists but uses
// stacks and leaves its input unchanged.

public final class DoubleNumber {

	public static ListNode doubleNum(ListNode head) {
		head = reverse(head); // least significant digit first; this reverses the caller's list too
		ListNode dummy = new ListNode(); // FAKE: the answer's first node needs something in front of it
		ListNode curr = dummy; // the last node of the answer so far
		int multiplier = 2;
		int carry = 0; // the tens part of the previous product
		while (head != null) { // WALK from the least significant digit
			int product = head.val * multiplier + carry; // DOUBLE the digit and add the carry
			carry = product / 10; // CARRY for the next digit: 0 or 1
			curr.next = new ListNode(product % 10); // ATTACH the answer digit
			curr = curr.next;
			head = head.next;
		}
		if (carry > 0) { // LEFTOVER: one more digit at the front (9 9 9 becomes 1 9 9 8)
			curr.next = new ListNode(carry);
		}
		return reverse(dummy.next); // RETURN: most significant digit first again
	}

	private static ListNode reverse(ListNode head) {
		ListNode dummy = new ListNode(); // FAKE: the reversed list grows after it
		ListNode curr = head;
		while (curr != null) { // MOVE each node to the front of the reversed list
			ListNode next = curr.next; // SAVE the rest before the arrow changes
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
