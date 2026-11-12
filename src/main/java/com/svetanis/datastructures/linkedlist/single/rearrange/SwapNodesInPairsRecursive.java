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
// The one idea: the rest of the list after the first pair is the same problem, only shorter. Swap
// the first two nodes and let the recursive call swap everything after them. The call returns the
// new head of the rest, and the first node, now second, points at it. A list of zero or one node
// needs no swap and is returned as it is.
//
// Time: O(n) -- one call per pair.
// Space: O(n) -- recursion stack, one frame per pair (n / 2).
//
// Siblings: SwapNodesInPairs does the same with a loop and a fake head node, in O(1) space.

public final class SwapNodesInPairsRecursive {

	public static ListNode swapPairs(ListNode head) {
		if (head == null || head.next == null) { // BASE: zero or one node left, nothing to swap
			return head;
		}
		ListNode first = head;
		ListNode second = head.next;
		first.next = swapPairs(second.next); // the rest, already swapped, goes after first
		second.next = first; // FLIP: second comes before first
		return second; // second is the new head of this part
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
