package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 23. Merge k Sorted Lists
//
// Input: nodes -- an array of k sorted singly linked lists (any of them may be empty, and k may
// be 0). On LeetCode: 0 <= k <= 10^4, each list sorted in increasing order, at most 10^4 nodes in
// all, each value -10^4 to 10^4. Returns the head of one sorted list made of their own nodes.
//
// The one idea: the next node of the answer is always the smallest of the k front nodes, because
// each list is sorted, so nothing behind a front node can be smaller than it. A priority queue
// (a heap: it hands back its smallest item first) holds the current front node of every list
// that still has nodes, so at most k nodes. Take the smallest out, attach it behind a fake node,
// and put the node after it into the queue in its place, which is that list's new front node.
//
// Time: O(N log k) -- N nodes in all; each goes into and out of a queue of at most k nodes
// Space: O(k) -- the queue holds one front node per list; the nodes are reused
//
// Siblings: MergeKSortedRecursive merges the lists two at a time instead of using a heap, also
// in O(N log k), but it recurses once per node placed in a two-list merge.

public final class MergeKSortedPriorityQueue {

	public static ListNode mergeKSorted(ListNode[] nodes) {
		ListNode dummy = new ListNode(); // fake node in front of the answer
		ListNode curr = dummy; // the last node of the answer so far
		Queue<ListNode> pq = init(nodes);
		while (!pq.isEmpty()) { // TAKE the smallest front node until every list is used up
			ListNode top = pq.poll();
			if (top.next != null) {
				pq.offer(top.next); // REFILL: that list's next node is its new front
			}
			curr.next = top; // ATTACH to the end of the answer
			curr = curr.next;
		}
		return dummy.next; // RETURN: the first attached node
	}

	private static PriorityQueue<ListNode> init(ListNode[] nodes) {
		PriorityQueue<ListNode> pq = new PriorityQueue<>(comparingInt(n -> n.val)); // SMALLEST val first
		for (ListNode node : nodes) {
			if (node != null) { // SKIP an empty list
				pq.offer(node);
			}
		}
		return pq;
	}

	public static void main(String[] args) {
		List<ListNode> nodes = createList();
		ListNode[] a = new ListNode[nodes.size()];
		a = nodes.toArray(a);
		ListNode merged = mergeKSorted(nodes.toArray(a));
		Nodes.print(merged); // 1 2 2 3 3 4 4 5 5 5 5 6 6 6 7 7 8 8 9 9 10 10 11 11 12 13 14 15
	}

	private static List<ListNode> createList() {
		int[] a1 = { 1, 2, 3, 5, 5, 6 };
		int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
		int[] a3 = { 3, 5, 7, 9, 11, 13, 15 };
		int[] a4 = { 2, 4, 6, 8, 10, 12, 14 };
		ListNode head1 = fromArray(a1);
		ListNode head2 = fromArray(a2);
		ListNode head3 = fromArray(a3);
		ListNode head4 = fromArray(a4);
		List<ListNode> heads = new ArrayList<>();
		heads.add(head1);
		heads.add(head2);
		heads.add(head3);
		heads.add(head4);
		return heads;
	}
}
