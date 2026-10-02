package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 2. Add Two Numbers
//
// Each list holds the digits of a number, least significant digit first (2 4 3 is 342).
// Return their sum as a new list in the same order (342 + 465: 7 0 8).
//
// Digits stored last-first is the order addition on paper works in, so walk both lists
// together, one digit from each per turn, and keep a carry: the 1 handed on to the next
// digit when a sum reaches 10. A list that has run out counts as 0. Each sum digit is a new
// node attached behind a fake node. addTwoLists keeps going while a carry is left, so a
// final carry gets its own turn; addTwoNumbers attaches it after the loop instead.

public final class AddTwoNums {
	// Time Complexity: O(n + m), one turn per digit of the longer list, plus one for a carry
	// Space Complexity: O(1), not counting the answer list

	public static ListNode addTwoLists(ListNode first, ListNode second) {
		int carry = 0;
		ListNode dummy = new ListNode(0);
		ListNode curr = dummy;
		while (first != null || second != null || carry != 0) {
			int num1 = first == null ? 0 : first.val;
			int num2 = second == null ? 0 : second.val;
			int sum = num1 + num2 + carry;
			carry = sum / 10;
			// create a new node with digit value of the sum
			curr.next = new ListNode(sum % 10);
			// move to the next node in the result list
			curr = curr.next;
			// move first and second pointers to next node
			first = first == null ? null : first.next;
			second = second == null ? null : second.next;
		}
		return dummy.next;
	}

	public static ListNode addTwoNumbers(ListNode first, ListNode second) {
		int carry = 0;
		ListNode dummy = new ListNode(0);
		ListNode curr = dummy;
		while (first != null || second != null) {
			int x = first != null ? first.val : 0;
			int y = second != null ? second.val : 0;
			int sum = x + y + carry;
			carry = sum / 10;
			curr.next = new ListNode(sum % 10);
			curr = curr.next;
			first = first == null ? null : first.next;
			second = second == null ? null : second.next;
		}
		if (carry > 0) { // both lists ran out with a carry left: one more digit (5 + 5 = 0 1)
			curr.next = new ListNode(carry);
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		ListNode num1 = fromList(asList(2, 4, 3));
		ListNode num2 = fromList(asList(5, 6, 4));
		print(addTwoLists(num1, num2)); // 7 0 8

		ListNode num3 = fromList(asList(0));
		ListNode num4 = fromList(asList(0));
		print(addTwoLists(num3, num4)); // 0

		ListNode num5 = fromList(asList(9, 9, 9, 9, 9, 9, 9));
		ListNode num6 = fromList(asList(9, 9, 9, 9));
		print(addTwoLists(num5, num6)); // 8 9 9 9 0 0 0 1
	}
}
