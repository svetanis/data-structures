package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 445. Add Two Numbers II
//
// Each list holds the digits of a number, most significant digit first (7 2 4 3 is 7243).
// Return their sum as a new list in the same order (7243 + 564: 7 8 0 7).
//
// Addition starts at the last digit, but a singly linked list can only be walked from the
// first. So copy each list's digits into a stack (a pile where the last item put on is the
// first taken off): the last digit then comes off first. Pop one digit from each stack per
// turn and keep a carry, the 1 handed on to the next digit when a sum reaches 10. The sum's
// digits come out last-first too, so each new node goes in at the front of the answer,
// right after a fake node. The loop keeps going while a carry is left.

public final class AddTwoNumsForward {
	// Time Complexity: O(n + m), each digit is pushed and popped once
	// Space Complexity: O(n + m) -- both lists are copied into deques,
	// which is the price of reading digits from the least significant
	// end when the list is stored most significant first

	public static ListNode addTwoLists(ListNode first, ListNode second) {
		Deque<Integer> dq1 = init(first);
		Deque<Integer> dq2 = init(second);

		int carry = 0;
		ListNode dummy = new ListNode(0);
		while (!dq1.isEmpty() || !dq2.isEmpty() || carry != 0) {
			int num1 = dq1.isEmpty() ? 0 : dq1.pop();
			int num2 = dq2.isEmpty() ? 0 : dq2.pop();
			int sum = num1 + num2 + carry;
			carry = sum / 10;
			// create a new node with digit value of the sum
			ListNode node = new ListNode(sum % 10);
			node.next = dummy.next;
			dummy.next = node;
		}
		return dummy.next;
	}

	private static Deque<Integer> init(ListNode head) {
		Deque<Integer> dq1 = new ArrayDeque<>();
		while (head != null) {
			dq1.push(head.val);
			head = head.next;
		}
		return dq1;
	}

	public static void main(String[] args) {
		ListNode num1 = fromList(asList(7, 2, 4, 3));
		ListNode num2 = fromList(asList(5, 6, 4));
		print(addTwoLists(num1, num2)); // 7 8 0 7

		ListNode num3 = fromList(asList(2, 4, 3));
		ListNode num4 = fromList(asList(5, 6, 4));
		print(addTwoLists(num3, num4)); // 8 0 7

		ListNode num5 = fromList(asList(0));
		ListNode num6 = fromList(asList(0));
		print(addTwoLists(num5, num6)); // 0
	}
}
