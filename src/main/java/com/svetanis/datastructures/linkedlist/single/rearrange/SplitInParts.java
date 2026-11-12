package com.svetanis.datastructures.linkedlist.single.rearrange;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 725. Split Linked List in Parts
//
// Input: the head of a list and k. On LeetCode: 0 <= number of nodes <= 1000, 0 <= val <= 1000,
// 1 <= k <= 50. Cut the list into k consecutive parts whose lengths differ by at most one,
// longer parts first, and return the k heads as an array. When k is larger than the length, the
// last parts are empty and their array slots hold null.
//
// The one idea: with size nodes and k parts, every part gets size / k nodes, and the first
// size % k parts get one more, because that is what is left over. Count the nodes first. Then walk
// the list once: for each part, remember its first node, step to its last node, save the node
// after it, cut, and start the next part from the saved node.
//
// Time: O(n + k) -- one pass to count, one to cut, and k array slots to fill.
// Space: O(1) -- besides the k-slot array that is returned; the parts reuse the existing nodes.

public final class SplitInParts {

	public static ListNode[] split(ListNode head, int k) {
		int size = size(head);
		int width = size / k; // every part has at least this many nodes
		int extra = size % k; // the first extra parts have one more
		ListNode[] nodes = new ListNode[k];
		ListNode curr = head;
		for (int i = 0; i < k; i++) {
			ListNode phead = curr; // the first node of this part
			int psize = width + (i < extra ? 1 : 0);
			for (int j = 0; j < psize - 1; j++) { // step to the last node of this part
				if (curr != null) {
					curr = curr.next;
				}
			}
			if (curr != null) { // null once the list has run out: this part is empty
				ListNode next = curr.next; // save before the cut
				curr.next = null; // CUT: this part ends here
				curr = next; // the next part starts at the saved node
			}
			nodes[i] = phead; // RECORD the part's head
		}
		return nodes;
	}

	private static int size(ListNode head) {
		int count = 0;
		while (head != null) {
			head = head.next;
			count++; // COUNT one node
		}
		return count;
	}

	public static void main(String[] args) {
		ListNode head = Nodes.fromList(Arrays.asList(1, 2, 3));
		ListNode[] nodes = split(head, 5);
		for (ListNode node : nodes) {
			Nodes.print(node); // 1, then 2, then 3, then two empty lines
		}

		ListNode head2 = Nodes.fromList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		ListNode[] nodes2 = split(head2, 3);
		for (ListNode node : nodes2) {
			Nodes.print(node); // 1 2 3 4, then 5 6 7, then 8 9 10
		}
	}
}
