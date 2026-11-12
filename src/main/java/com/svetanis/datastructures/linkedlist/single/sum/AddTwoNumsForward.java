package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 445. Add Two Numbers II
//
// Input: first and second -- the first nodes of two non-empty singly linked lists; each node holds
// one digit 0 to 9 of a number, most significant digit first (7 2 4 3 is 7243). On LeetCode: 1 to
// 100 nodes in each, no leading zeros except the number 0 itself. Returns the head of a new list
// holding their sum in the same order (7243 + 564: 7 8 0 7). The input lists are not changed.
//
// The one idea: addition on paper starts at the last digit, but a singly linked list can only be
// walked from the first. So copy each list's digits into a stack (a pile where the last item put
// on is the first taken off): the last digit then comes off first. Pop one digit from each stack
// per turn and add them with the carry, the 1 handed on from the turn before when its sum reached
// 10. A stack that has run out counts as 0. The sum's digits come out last-first too, so each new
// node goes in at the front of the answer, right after a fake node. The loop keeps going while a
// carry is left.
//
// Time: O(n + m) -- each digit is pushed once and popped once
// Space: O(n + m) -- both lists are copied into stacks, which is the price of reading digits from
// the least significant end when the list is stored most significant first
//
// Siblings: AddTwoNums (LC 2) adds numbers stored least significant digit first, so it needs no
// stacks and builds the answer at the end.

public final class AddTwoNumsForward {

	public static ListNode addTwoLists(ListNode first, ListNode second) {
		Deque<Integer> dq1 = init(first); // STACK of first's digits, last digit on top
		Deque<Integer> dq2 = init(second);

		int carry = 0; // the 1 handed on to the next digit
		ListNode dummy = new ListNode(0); // FAKE: the answer grows after it, at the front
		while (!dq1.isEmpty() || !dq2.isEmpty() || carry != 0) { // STOP when both stacks are empty and no carry is left
			int num1 = dq1.isEmpty() ? 0 : dq1.pop(); // POP: a stack that ran out counts as 0
			int num2 = dq2.isEmpty() ? 0 : dq2.pop();
			int sum = num1 + num2 + carry; // ADD the two digits and the carry
			carry = sum / 10; // CARRY for the next digit: 0 or 1
			// create a new node with digit value of the sum
			ListNode node = new ListNode(sum % 10);
			node.next = dummy.next; // PREPEND: digits come out last-first, so each goes in front
			dummy.next = node;
		}
		return dummy.next; // RETURN: the most significant digit
	}

	private static Deque<Integer> init(ListNode head) {
		Deque<Integer> dq1 = new ArrayDeque<>();
		while (head != null) { // PUSH every digit, first to last
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
