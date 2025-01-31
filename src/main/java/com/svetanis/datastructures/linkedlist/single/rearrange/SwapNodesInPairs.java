package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 24. Swap Nodes in Pairs

public final class SwapNodesInPairs {
	// Time Complexity: O(n)

	public static Node swapPairs(Node head) {
		Node dummy = new Node();
		dummy.next = head;
		Node prev = dummy;
		Node curr = head;
		while (curr != null && curr.next != null) {
			Node next = curr.next;
			// swap the pair
			curr.next = next.next;
			next.next = curr;
			prev.next = next;

			prev = curr;
			curr = curr.next;
		}
		return dummy.next;
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
