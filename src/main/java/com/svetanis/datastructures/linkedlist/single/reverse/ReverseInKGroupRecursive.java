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
// row. First count k nodes from head; if the list runs out first, the group is short and is
// returned as it is. Otherwise curr is the first node after the group, and the recursive call
// finishes everything from curr on. Then the group's k nodes are taken off the front one at a
// time, and each is put in front of curr, the finished part, so they end up in front of it in
// reverse order. The last node put in front is the new head.
//
// Time: O(n) -- each node is counted once and moved once.
// Space: O(n / k) -- recursion stack, one call per group. Assumption: the deepest legal case
// is n = 5000 with k = 2, 2,500 nested calls, which runs on a default JVM stack; k = 1 returns
// at once.
//
// Siblings: ReverseInKGroup is the version to carry: a look-ahead before each group, then LC 92's
// counted loop. ReverseInKGroupCutAndReverse cuts each group off and reverses it as a list of its
// own, and ReverseInKGroupMoveToFront moves nodes to the front of their group; all three are loops
// with O(1) space. ReverseInKGroupLeftoverTooRecursive reverses a short last group too, which LC 25
// does not want.

public final class ReverseInKGroupRecursive {

	public static ListNode reverse(ListNode head, int k) {
		if (k <= 1 || head == null) { // BASE: k = 1 moves nothing
			return head;
		}
		int count = 0;
		ListNode curr = head;
		while (curr != null && count != k) { // COUNT: walk k nodes; curr ends on the first node after the group
			curr = curr.next;
			count++;
		}
		if (count == k) { // a full group; a short one falls through and is returned as it is
			curr = reverse(curr, k); // RECURSE: curr is the rest of the list, already finished
			while (count-- > 0) { // MOVE each of the k nodes, one at a time
				ListNode next = head.next; // SAVE: the group's next node before head.next changes
				head.next = curr; // FRONT: put head in front of the finished part
				curr = head; // the finished part now starts at head
				head = next;
			}
			head = curr; // TAKE: the group's old last node, now the first node
		}
		return head; // RETURN: a short group comes back as it is
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
