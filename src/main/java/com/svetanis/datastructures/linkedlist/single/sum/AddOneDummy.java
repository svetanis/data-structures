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
// Adding one changes only the last digit that is not 9, which goes up by one, and every 9
// after it, which becomes 0. So walk the list once and remember the last node whose digit is
// not 9. The walk starts on a fake node holding 0 in front of the head: if every digit is 9,
// the remembered node is the fake node itself, it becomes 1, and it is returned as the new
// first digit (9 9 becomes 1 0 0).

public final class AddOneDummy {
	// Time Complexity: O(n), two passes at most
	// Space Complexity: O(1), the digits are changed in place

	public static ListNode addOne(ListNode head) {
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode node = dummy; // the last node seen whose digit is not 9
		while (head != null) {
			if (head.val != 9) {
				node = head;
			}
			head = head.next;
		}
		node.val += 1; // never reaches 10: node's digit is not 9
		ListNode curr = node.next; // every node after it holds a 9
		while (curr != null) {
			curr.val = 0;
			curr = curr.next;
		}
		// the fake node holds 1 only when every digit was 9: it is then the new first digit
		return dummy.val == 1 ? dummy : dummy.next;
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
