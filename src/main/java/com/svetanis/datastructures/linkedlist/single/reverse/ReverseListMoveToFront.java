package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 206. Reverse Linked List
//
// Input: the head of a singly linked list. LeetCode: 0 <= n <= 5000 nodes, values
// -5000..5000. Returns: the head of the reversed list (null for an empty list).
//
// The one idea: one node holds a value and an arrow to the next node. Take the nodes off the
// old list one at a time and insert each at the front of a new list that starts behind a fake
// node. The first node taken ends up last and the last node taken ends up first. dummy.next
// plays the part that prev plays in ReverseList.
//
// Time: O(n) -- each node is moved once.
// Space: O(1) -- the fake node and two markers.
//
// Siblings: ReverseList (the same four lines with prev instead of a fake node),
// ReverseListRecursive (arrows turned on the way back from the recursion), ReverseListTailRecursive
// (the loop as a recursion) and ReverseListStack (nodes pushed and popped).

public final class ReverseListMoveToFront {

	public static ListNode reverse(ListNode head) {
		ListNode curr = head;
		ListNode dummy = new ListNode(); // FAKE: a node in front of the reversed list
		while (curr != null) { // WALK the old list, one node per turn
			ListNode next = curr.next; // SAVE: the way forward first
			curr.next = dummy.next; // FRONT: insert curr at the front of the reversed list
			dummy.next = curr;
			curr = next; // MOVE: by the saved name
		}
		return dummy.next; // RETURN: the node inserted last
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(85, 15, 4, 20));
		print(reverse(head)); // 20 4 15 85

		ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head1)); // 5 4 3 2 1

		ListNode head2 = fromList(asList(1, 2));
		print(reverse(head2)); // 2 1
	}
}
