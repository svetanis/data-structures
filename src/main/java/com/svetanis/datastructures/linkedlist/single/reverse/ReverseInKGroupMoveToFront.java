package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 25. Reverse Nodes in k-Group
//
// Input: the head of a singly linked list and a number k. LeetCode: 1 <= k <= n <= 5000 nodes,
// values 0..1000. Returns: the head after every group of k nodes, starting from the head, is
// reversed. If the number of nodes is not a multiple of k, the left-out nodes at the end stay
// as they are. Only the nodes move, never their values.
//
// The one idea: one node holds a value and an arrow to the next node; a group is k nodes in a
// row. Count the nodes first, so the loop knows how many full groups are left. For each group,
// before is the node before it and curr is the group's first node. curr itself is never moved:
// instead the node right after curr, called next, is unlinked and inserted just after before,
// which makes it the group's first node. After k - 1 such moves the group is reversed, curr is
// its last node, and curr becomes the node before the next group. The loop stops when fewer
// than k nodes are left, so a short last group stays as it is.
//
// Time: O(n) -- one pass to count, then k - 1 moves per group, fewer than n in all.
// Space: O(1) -- a few markers; there is no recursion, so n = 5000 needs no deep stack.
//
// Siblings: ReverseInKGroup is the version to carry: a look-ahead before each group, then LC 92's
// counted loop. ReverseInKGroupCutAndReverse cuts each group off and reverses it as a list of its
// own. ReverseInKGroupRecursive reverses the rest of the list first and builds each group in front
// of it.

public final class ReverseInKGroupMoveToFront {

	public static ListNode reverse(ListNode head, int k) {
		if (k <= 1 || head == null) { // BASE: k = 1 moves nothing
			return head;
		}

		ListNode dummy = new ListNode(0); // FAKE: the first group gets a node before it
		dummy.next = head;
		ListNode before = dummy; // the node before the current group
		ListNode curr = dummy;
		ListNode next = dummy;
		int count = 0;
		while (curr.next != null) { // COUNT: the nodes in the list
			curr = curr.next;
			count++;
		}
		while (count >= k) { // stop when fewer than k nodes are left: they stay as they are
			curr = before.next; // the group's first node, its last after the reversal
			next = curr.next; // the node to move to the front
			for (int i = 1; i < k; i++) { // MOVE k - 1 nodes to the front of the group
				curr.next = next.next; // unlink next: curr now skips over it
				next.next = before.next; // next points at the group's current first node
				before.next = next; // next is the group's first node now
				next = curr.next; // the following node to move
			}
			before = curr; // NEXT: curr is the group's last node, the node before the next group
			count -= k; // TAKE: one full group done
		}
		return dummy.next; // RETURN: dummy.next, since the first group changes the head
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
		print(reverse(head, 3)); // 3 2 1 6 5 4 7 8

		ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head1, 2)); // 2 1 4 3 5

		ListNode head2 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head2, 3)); // 3 2 1 4 5
	}
}
