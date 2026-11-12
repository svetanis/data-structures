package com.svetanis.datastructures.linkedlist.single.remove;

import java.util.HashMap;
import java.util.Map;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 1171. Remove Zero Sum Consecutive Nodes from Linked List
//
// Input: the head of a singly linked list. LeetCode: 1 <= n <= 1000 nodes, values
// -1000..1000. Returns: the head after runs of consecutive nodes whose values add up to 0 are
// removed again and again until no such run is left. When several final lists are possible,
// any one of them is accepted.
//
// The one idea: the running total at a node is the sum of every value from the head up to that
// node. If two nodes have the same running total, the nodes after the first, up to and
// including the second, add up to 0. The first pass records, for each running total, the LAST
// node that reaches it. The second pass walks again and, at each node, jumps straight past that
// last node, cutting out the longest zero-sum run that starts just after it. Cutting a run
// that adds up to 0 leaves the running totals of the later nodes unchanged, so the recorded map
// is still right while it is being used. A fake node with value 0 sits before the head, so a
// run that starts at the head is cut too.
//
// Time: O(n) -- two passes; each map operation is O(1) on average.
// Space: O(n) -- the map holds one node per different running total.

public final class RemoveZeroSumNodes {

	public static ListNode remove(ListNode head) {
		ListNode dummy = new ListNode(0); // FAKE: running total 0, before the head
		dummy.next = head;
		Map<Integer, ListNode> map = prefix(dummy); // RECORD: last node for each running total
		int sum = 0;
		ListNode curr = dummy;
		while (curr != null) { // WALK: the second pass
			sum += curr.val;
			curr.next = map.get(sum).next; // CUT: jump past the last node with this total (curr itself if none later)
			curr = curr.next;
		}
		return dummy.next; // RETURN: dummy.next, since the head itself may have been removed
	}

	private static Map<Integer, ListNode> prefix(ListNode head) {
		Map<Integer, ListNode> map = new HashMap<>();
		int sum = 0;
		ListNode curr = head;
		while (curr != null) { // WALK: the first pass
			sum += curr.val;
			map.put(sum, curr); // LAST: a later node with the same total replaces an earlier one
			curr = curr.next;
		}
		return map; // RETURN: running total -> last node reaching it
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, -3, 3, 1 };
		ListNode head = Nodes.fromArray(a);
		Nodes.print(remove(head)); // 3 1

		int[] a1 = { 1, 2, 3, -3, 4 };
		ListNode head1 = Nodes.fromArray(a1);
		Nodes.print(remove(head1)); // 1 2 4

		int[] a2 = { 1, 2, 3, -3, -2 };
		ListNode head2 = Nodes.fromArray(a2);
		Nodes.print(remove(head2)); // 1
	}
}
