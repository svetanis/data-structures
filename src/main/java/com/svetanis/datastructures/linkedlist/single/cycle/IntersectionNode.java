package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 160. Intersection of Two Linked Lists
//
// Input: the heads of two singly linked lists that have no cycles. They may join at some node
// and share every node after it. On LeetCode: 1 <= list lengths <= 3 * 10^4, 1 <= val <= 10^5.
// Returns the first shared node, or null if the lists never join. Both lists are left as they were.
//
// The one idea: let a and b be the nodes only list 1 and only list 2 have, and c the shared
// nodes at the end. Each pointer walks its own list, then switches to the head of the other one.
// Pointer 1 walks a + c + b nodes, pointer 2 walks b + c + a, the same total, so they reach the
// joining node on the same turn, even when the two lists have different lengths. Lists that
// never join (c = 0) make both pointers reach null together, which ends the loop too.
//
// Time: O(n + m) -- each pointer walks both lists once, n and m the two lengths.
// Space: O(1) -- two pointers.

public final class IntersectionNode {

	public static ListNode intersection(ListNode head1, ListNode head2) {
		ListNode curr1 = head1;
		ListNode curr2 = head2;
		while (curr1 != curr2) { // MEET: the shared node, or null and null when none is shared
			curr1 = curr1 == null ? head2 : curr1.next; // STEP, or SWITCH lists once on null
			curr2 = curr2 == null ? head1 : curr2.next; // test curr, not curr.next: null is a step
		}
		return curr1; // RETURN: each walked a + c + b, so they arrive together
	}

	public static void main(String[] args) {
		ListNode head1 = new ListNode(10);
		ListNode head2 = new ListNode(3);
		head2.next = new ListNode(6);
		head2.next.next = new ListNode(9);
		ListNode node3 = new ListNode(15);
		head1.next = node3;
		head2.next.next.next = node3;
		head1.next.next = new ListNode(30);
		print(head1); // 10 15 30
		print(head2); // 3 6 9 15 30
		System.out.println(intersection(head1, head2)); // 15
	}
}
