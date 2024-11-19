package com.svetanis.datastructures.linkedlist.single.sort;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 23. Merge k Sorted Lists

public final class MergeKSortedRecursive {

	public static Node mergeKSorted(List<Node> nodes) {
		Node head = null;
		for (Node node : nodes) {
			head = merge(head, node);
		}
		return head;
	}

	private static Node merge(Node node1, Node node2) {
		Node merged = null;
		if (node1 == null) {
			return node2;
		}
		if (node2 == null) {
			return node1;
		}
		if (node1.data <= node2.data) {
			merged = node1;
			merged.next = merge(node1.next, node2);
		} else {
			merged = node2;
			merged.next = merge(node1, node2.next);
		}
		return merged;
	}

	public static void main(String[] args) {
		List<Node> nodes = createList();
		Node merged = mergeKSorted(nodes);
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
