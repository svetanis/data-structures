package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 24. Swap Nodes in Pairs

public final class SwapNodesInPairsRecursive {
	// Time Complexity: O(n)

	public static Node swapPairs(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node first = head;
		Node second = head.next;
		first.next = swapPairs(second.next);
		second.next = first;
		return second;
	}

	public static void main(String[] args) {
		Node head1 = fromList(newArrayList(1, 2, 3, 4));
		Node swapped1 = swapPairs(head1);
		print(swapped1); // 2,1,4,3
		System.out.println();

		Node head2 = fromList(newArrayList(1, 2, 3));
		Node swapped2 = swapPairs(head2);
		print(swapped2); // 2, 1, 3
		System.out.println();
	}
}
