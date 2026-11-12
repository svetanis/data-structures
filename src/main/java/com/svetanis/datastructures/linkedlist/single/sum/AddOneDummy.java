package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 369. Plus One Linked List
//
// Input: head -- the first node of a singly linked list; each node holds one digit 0 to 9 of a
// number, most significant digit first (1 2 9 is 129), with no leading zeros except the number
// 0 itself. Returns the head of the same list after adding one to the number (1 3 0); the digits
// are changed in place.
//
// The one idea: adding one changes only the last digit that is not 9, which goes up by one, and
// every 9 after it, which becomes 0 (1 9 9 9 becomes 2 0 0 0). The digits before it stay as they
// are. So walk the list once and remember node, the last node whose digit is not 9. The walk
// starts on a fake node holding 0 in front of the head: if every digit is 9, the remembered node
// is the fake node itself, it becomes 1, and it is returned as the new first digit (9 9 becomes
// 1 0 0).
//
// Time: O(n) -- one pass to find the last digit that is not 9, one pass over the 9s after it
// Space: O(1) -- the digits are changed in place; one fake node is added
//
// Siblings: AddOneIterative reverses the list, adds with a carry and reverses back. AddOneRecursive
// passes the carry back out of a recursion that is n calls deep.

public final class AddOneDummy {

	public static ListNode addOne(ListNode head) {
		ListNode dummy = new ListNode(0); // FAKE: the new first digit if every digit is 9
		dummy.next = head;
		ListNode node = dummy; // the last node seen whose digit is not 9
		while (head != null) { // FIND the last digit that is not 9
			if (head.val != 9) {
				node = head;
			}
			head = head.next;
		}
		node.val += 1; // ADD: never reaches 10, because node's digit is not 9
		ListNode curr = node.next; // every node after it holds a 9
		while (curr != null) { // ZERO the 9s that carried
			curr.val = 0;
			curr = curr.next;
		}
		// the fake node holds 1 only when every digit was 9: it is then the new first digit
		return dummy.val == 1 ? dummy : dummy.next; // RETURN
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
