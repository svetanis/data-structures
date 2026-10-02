package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 24. Swap Nodes in Pairs
//
// Swap every two neighbouring nodes (1st with 2nd, 3rd with 4th, ...) by changing links, not
// values, and return the new head. A last node with no partner stays where it is.
//
// prev is the node just before the pair, curr the first node of the pair, next the second.
// Three links change: curr points past the pair, next points at curr, prev points at next.
// Then curr, which is now second in its pair, is the prev of the following pair. A fake node
// in front of the head gives the first pair a prev too, so a new head is not a special case.

public final class SwapNodesInPairs {
	// Time Complexity: O(n), one pass
	// Space Complexity: O(1)

	public static ListNode swapPairs(ListNode head) {
		ListNode dummy = new ListNode();
		dummy.next = head;
		ListNode prev = dummy;
		ListNode curr = head;
		while (curr != null && curr.next != null) { // stop when fewer than two nodes are left
			ListNode next = curr.next;
			// swap the pair
			curr.next = next.next; // curr keeps the rest of the list before next.next is overwritten
			next.next = curr;
			prev.next = next; // the node before the pair now leads to its new first node

			prev = curr; // curr is now the second node of its pair
			curr = curr.next; // the first node of the following pair
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		ListNode head1 = fromList(newArrayList(1, 2, 3, 4));
		ListNode swapped1 = swapPairs(head1);
		print(swapped1); // 2,1,4,3
		System.out.println();

		ListNode head2 = fromList(newArrayList(1, 2, 3));
		ListNode swapped2 = swapPairs(head2);
		print(swapped2); // 2, 1, 3
		System.out.println();
	}
}
