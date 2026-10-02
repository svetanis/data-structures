package com.svetanis.datastructures.linkedlist.single.sort;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 23. Merge k Sorted Lists
//
// Given a list of k sorted singly linked lists (any of them may be empty, and k may be 0),
// merge them into one sorted list made of their own nodes and return its head.
//
// Merges the lists in pairs: each half of the lists is merged recursively, then the two
// results are merged. Every node takes part in about log2(k) merges, one per level of
// halving. Folding the lists into one running result instead walks the early nodes again
// for every list after them, which grows with k times the total length.
// A range holding a single list needs no merging: that list is already sorted, and an
// empty list arrives as null and is returned as null.

public final class MergeKSortedRecursive {
	// Time Complexity: O(N log k), N nodes in all, k lists
	// Space Complexity: O(N), the two-list merge recurses once per node it places

	public static ListNode mergeKSorted(List<ListNode> nodes) {
		if (nodes.isEmpty()) { // k = 0: no range to split
			return null;
		}
		return mergeRange(nodes, 0, nodes.size() - 1);
	}

	// merges nodes[left..right], both ends inclusive
	private static ListNode mergeRange(List<ListNode> nodes, int left, int right) {
		if (left == right) { // one list: already sorted
			return nodes.get(left);
		}
		int mid = left + (right - left) / 2;
		ListNode first = mergeRange(nodes, left, mid);
		ListNode second = mergeRange(nodes, mid + 1, right);
		return merge(first, second);
	}

	// merges two sorted lists, reusing their nodes
	private static ListNode merge(ListNode node1, ListNode node2) {
		ListNode merged = null;
		if (node1 == null) {
			return node2;
		}
		if (node2 == null) {
			return node1;
		}
		if (node1.val <= node2.val) {
			merged = node1;
			merged.next = merge(node1.next, node2);
		} else {
			merged = node2;
			merged.next = merge(node1, node2.next);
		}
		return merged;
	}

	public static void main(String[] args) {
		List<ListNode> nodes = createList();
		ListNode merged = mergeKSorted(nodes);
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
