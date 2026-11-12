package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 2. Add Two Numbers
//
// Input: first and second -- the first nodes of two non-empty singly linked lists; each node holds
// one digit 0 to 9 of a number, least significant digit first (2 4 3 is 342). On LeetCode: 1 to
// 100 nodes in each, no leading zeros except the number 0 itself. Returns the head of a new list
// holding their sum in the same order (342 + 465: 7 0 8). The input lists are not changed.
//
// The one idea: digits stored last-first are in the order addition on paper works in, so walk
// both lists together, one digit from each per turn. One turn adds three things: first's digit,
// second's digit and the carry, the 1 handed on from the turn before when its sum reached 10. A
// list that has run out counts as 0. The turn's sum splits in two: sum % 10 is the digit of the
// answer, sum / 10 (0 or 1) is the carry for the next turn. Each answer digit is a new node
// attached behind a fake node, so the first digit needs no special case. addTwoLists keeps going
// while a carry is left, so a final carry gets a turn of its own; addTwoNumbers attaches it
// after the loop instead (5 + 5 gives 0 1).
//
// Time: O(n + m) -- one turn per digit of the longer list, plus one for a final carry
// Space: O(1) -- not counting the answer list
//
// Siblings: AddTwoNumsForward (LC 445) adds numbers stored most significant digit first, so it
// reads the digits from stacks. addTwoNumbers below is the same method as addTwoLists, written
// with the final carry attached after the loop.

public final class AddTwoNums {

	public static ListNode addTwoLists(ListNode first, ListNode second) {
		int carry = 0; // the 1 handed on to the next digit
		ListNode dummy = new ListNode(0); // FAKE: the answer's first node needs something in front of it
		ListNode curr = dummy; // the last node of the answer so far
		while (first != null || second != null || carry != 0) { // STOP only when both are used up and no carry is left
			int num1 = first == null ? 0 : first.val; // a list that ran out counts as 0
			int num2 = second == null ? 0 : second.val;
			int sum = num1 + num2 + carry; // ADD the two digits and the carry
			carry = sum / 10; // CARRY for the next digit: 0 or 1
			// create a new node with digit value of the sum
			curr.next = new ListNode(sum % 10); // ATTACH the answer digit
			// move to the next node in the result list
			curr = curr.next;
			// move first and second pointers to next node
			first = first == null ? null : first.next; // ADVANCE both lists; a used-up list stays null
			second = second == null ? null : second.next;
		}
		return dummy.next; // RETURN: the first digit
	}

	public static ListNode addTwoNumbers(ListNode first, ListNode second) {
		int carry = 0; // the 1 handed on to the next digit
		ListNode dummy = new ListNode(0); // FAKE
		ListNode curr = dummy; // the last node of the answer so far
		while (first != null || second != null) { // STOP when both lists are used up
			int x = first != null ? first.val : 0; // a list that ran out counts as 0
			int y = second != null ? second.val : 0;
			int sum = x + y + carry; // ADD the two digits and the carry
			carry = sum / 10; // CARRY for the next digit: 0 or 1
			curr.next = new ListNode(sum % 10); // ATTACH the answer digit
			curr = curr.next;
			first = first == null ? null : first.next; // ADVANCE both lists
			second = second == null ? null : second.next;
		}
		if (carry > 0) { // LEFTOVER: both lists ran out with a carry left: one more digit (5 + 5 = 0 1)
			curr.next = new ListNode(carry);
		}
		return dummy.next; // RETURN: the first digit
	}

	public static void main(String[] args) {
		ListNode num1 = fromList(asList(2, 4, 3));
		ListNode num2 = fromList(asList(5, 6, 4));
		print(addTwoLists(num1, num2)); // 7 0 8
		print(addTwoNumbers(num1, num2)); // 7 0 8

		ListNode num3 = fromList(asList(0));
		ListNode num4 = fromList(asList(0));
		print(addTwoLists(num3, num4)); // 0
		print(addTwoNumbers(num3, num4)); // 0

		ListNode num5 = fromList(asList(9, 9, 9, 9, 9, 9, 9));
		ListNode num6 = fromList(asList(9, 9, 9, 9));
		print(addTwoLists(num5, num6)); // 8 9 9 9 0 0 0 1
		print(addTwoNumbers(num5, num6)); // 8 9 9 9 0 0 0 1

		ListNode num7 = fromList(asList(5));
		print(addTwoLists(num7, num7)); // 0 1
		print(addTwoNumbers(num7, num7)); // 0 1
	}
}
