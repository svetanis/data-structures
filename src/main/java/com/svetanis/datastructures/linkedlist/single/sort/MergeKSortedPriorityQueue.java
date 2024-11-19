package com.svetanis.datastructures.linkedlist.single.sort;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 23. Merge k Sorted Lists

public final class MergeKSortedPriorityQueue {
	// Time Complexity: O(n * k * log k)
	// Space Complexity: O(k)

	public static Node mergeKSorted(Node[] nodes) {
		Node head = new Node();
		Node curr = head;
		Queue<Node> pq = init(nodes);
		while (!pq.isEmpty()) {
			Node top = pq.poll();
			if (top.next != null) {
				pq.offer(top.next);
			}
			curr.next = top;
			curr = curr.next;
		}
		return head.next;
	}

	private static PriorityQueue<Node> init(Node[] nodes) {
		PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> n1.data - n2.data);
		for (Node node : nodes) {
			if (node != null) {
				pq.offer(node);
			}
		}
		return pq;
	}

	public static void main(String[] args) {
		List<Node> nodes = createList();
		Node[] a = new Node[nodes.size()];
		a = nodes.toArray(a);
		Node merged = mergeKSorted(nodes.toArray(a));
		Nodes.print(merged);
	}

	private static ImmutableList<Node> createList() {
		int[] a1 = { 1, 2, 3, 5, 5, 6 };
		int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
		int[] a3 = { 3, 5, 7, 9, 11, 13, 15 };
		int[] a4 = { 2, 4, 6, 8, 10, 12, 14 };
		Node head1 = fromArray(a1);
		Node head2 = fromArray(a2);
		Node head3 = fromArray(a3);
		Node head4 = fromArray(a4);
		List<Node> heads = newArrayList();
		heads.add(head1);
		heads.add(head2);
		heads.add(head3);
		heads.add(head4);
		return copyOf(heads);
	}
}
