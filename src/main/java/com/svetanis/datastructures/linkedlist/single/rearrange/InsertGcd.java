package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 2807. Insert Greatest Common Divisors in Linked List
//
// Between every two neighbouring nodes, insert a new node holding the greatest common divisor
// of their two values (the largest number that divides both), and return the head. The list
// has at least one node.
//
// The first node never moves, so the head never changes and no fake node is needed. Two
// pointers walk side by side: prev on one original node and curr on the original node after
// it. Each step makes a new node, points it at curr, points prev at it, then moves both one
// original node forward, stepping over the node just inserted.

public final class InsertGcd {
	// Time Complexity: O(n * log(min(a,b))), one pass; each gcd takes O(log(min(a,b))) steps
	// Space Complexity: O(n), the n - 1 new nodes that are part of the answer

	public static ListNode insert(ListNode head) {
		ListNode prev = head;
		ListNode curr = head.next; // LC 2807 promises at least one node, so head is not null
		while (curr != null) {
			int gcd = gcd(prev.val, curr.val);
			ListNode node = new ListNode(gcd);
			node.next = curr; // the new node points at curr first, while prev.next still leads to curr
			prev.next = node; // then prev points at the new node
			prev = curr; // the next pair starts at curr, past the node just inserted
			curr = curr.next;
		}
		return head;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	public static void main(String[] args) {
		ListNode head = Nodes.fromList(Arrays.asList(18, 6, 10, 3));
		print(insert(head)); // 18,6,6,2,10,1,3

		ListNode head2 = Nodes.fromList(Arrays.asList(7));
		print(insert(head2)); // 7
	}
}
