package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.datastructures.linkedlist.single.reverse.ReverseList.reverse;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 369. Plus One Linked List
//
// Input: head -- the first node of a singly linked list; each node holds one digit 0 to 9 of a
// number, most significant digit first (1 2 9 is 129), with no leading zeros except the number
// 0 itself. Returns the head of the list after adding one to the number (1 3 0); the digits are
// changed in place.
//
// The one idea: addition on paper starts at the last digit, but a singly linked list can only be
// walked from the first. So reverse the list, add the way it is done on paper, then reverse it
// back. The carry is the 1 handed on to the next digit when a digit's sum reaches 10; here it
// starts at 1, because that is the one being added. A carry left over after the last digit
// becomes a new node, which is the new first digit once the list is reversed back (9 9 becomes
// 1 0 0).
//
// Time: O(n) -- three passes: reverse, add, reverse
// Space: O(1) -- the digits are changed in place; one node is added for a final carry
//
// Siblings: AddOneDummy finds the last digit that is not 9 in one forward walk, with no
// reversing. AddOneRecursive passes the carry back out of a recursion that is n calls deep.

public final class AddOneIterative {

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

		while (head != null) { // WALK from the least significant digit
			sum = carry + head.val; // ADD the carry to this digit
			carry = (sum >= 10) ? 1 : 0; // CARRY: 1 when the digit overflowed
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
