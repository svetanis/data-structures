package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 24. Swap Nodes in Pairs
//
// Input: the head of a list. On LeetCode: 0 <= number of nodes <= 100, 0 <= val <= 100. Swap
// every two neighbouring nodes (1st with 2nd, 3rd with 4th, ...) by changing links, not values,
// and return the new head. A last node with no partner stays where it is.
//
// The one idea: swapping two neighbours changes three arrows and no more. prev is the node just
// before the pair, curr the first node of the pair, next the second. curr points past the pair,
// next points at curr, prev points at next. Then curr, which is now second in its pair, is the
// prev of the following pair. A fake node in front of the head gives the first pair a prev too,
// so a new head is not a special case.
//
// Time: O(n) -- one pass.
// Space: O(1) -- a few pointers.
//
// Siblings: SwapNodesInPairsRecursive swaps the first two and lets a recursive call swap the rest.

public final class SwapNodesInPairs {

	public static ListNode swapPairs(ListNode head) {
		ListNode dummy = new ListNode();
		dummy.next = head;
		ListNode prev = dummy;
		ListNode curr = head;
		while (curr != null && curr.next != null) { // stop when fewer than two nodes are left
			ListNode next = curr.next;
			// swap the pair
			curr.next = next.next; // curr keeps the rest of the list before next.next is overwritten
			next.next = curr; // FLIP: next comes before curr
			prev.next = next; // the node before the pair now leads to its new first node

			prev = curr; // curr is now the second node of its pair
			curr = curr.next; // the first node of the following pair
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		ListNode head1 = fromList(asList(1, 2, 3, 4));
		ListNode swapped1 = swapPairs(head1);
		print(swapped1); // 2 1 4 3
		System.out.println(); // (empty line)

		ListNode head2 = fromList(asList(1, 2, 3));
		ListNode swapped2 = swapPairs(head2);
		print(swapped2); // 2 1 3
		System.out.println(); // (empty line)
	}
}
