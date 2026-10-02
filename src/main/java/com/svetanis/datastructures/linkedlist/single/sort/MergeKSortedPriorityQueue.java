package com.svetanis.datastructures.linkedlist.single.sort;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static java.util.Comparator.comparingInt;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 23. Merge k Sorted Lists
//
// Given an array of k sorted singly linked lists (any of them may be empty, and k may be 0),
// merge them into one sorted list made of their own nodes and return its head.
//
// The next node of the answer is always the smallest of the k front nodes. A priority queue
// (a heap: it hands back its smallest item first) holds the current front node of every list
// that still has nodes, so at most k nodes. Take the smallest out, attach it behind a fake
// node, and put the node after it into the queue in its place.

public final class MergeKSortedPriorityQueue {
	// Time Complexity: O(n * k * log k), n nodes in each of k lists, each one goes in and out
	// of a queue of at most k nodes
	// Space Complexity: O(k), the queue holds one front node per list; the nodes are reused

	public static ListNode mergeKSorted(ListNode[] nodes) {
		ListNode dummy = new ListNode();
		ListNode curr = dummy;
		Queue<ListNode> pq = init(nodes);
		while (!pq.isEmpty()) {
			ListNode top = pq.poll();
			if (top.next != null) {
				pq.offer(top.next);
			}
			curr.next = top;
			curr = curr.next;
		}
		return dummy.next;
	}

	private static PriorityQueue<ListNode> init(ListNode[] nodes) {
		PriorityQueue<ListNode> pq = new PriorityQueue<>(comparingInt(n -> n.val));
		for (ListNode node : nodes) {
			if (node != null) {
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

	private static ImmutableList<ListNode> createList() {
		int[] a1 = { 1, 2, 3, 5, 5, 6 };
		int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
		int[] a3 = { 3, 5, 7, 9, 11, 13, 15 };
		int[] a4 = { 2, 4, 6, 8, 10, 12, 14 };
		ListNode head1 = fromArray(a1);
		ListNode head2 = fromArray(a2);
		ListNode head3 = fromArray(a3);
		ListNode head4 = fromArray(a4);
		List<ListNode> heads = newArrayList();
		heads.add(head1);
		heads.add(head2);
		heads.add(head3);
		heads.add(head4);
		return copyOf(heads);
	}
}
