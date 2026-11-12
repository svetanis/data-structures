package com.svetanis.datastructures.linkedlist.single;

import java.util.List;
import java.util.Optional;

// Static helpers for lists of ListNode, used by the main methods across this repo: build a
// list from a List or an array (both refuse an empty input), print, count, insert, search,
// and find the last node. LeetCode builds its input lists itself, so a method meant to be
// pasted into LeetCode must not call these. sum is the one helper that is not static.
//
// Every helper walks the list from the node it is given, one node per step, so each costs
// O(n) time and O(1) extra space, except fromList and fromArray, which build n new nodes.

public final class Nodes {

	public static boolean isNotNull(ListNode node) {
		return !isNull(node);
	}

	public static boolean isNull(ListNode node) {
		return node == null;
	}

	public static ListNode insertAtHead(ListNode head, int data) {
		ListNode newNode = new ListNode(data);
		newNode.next = head; // LINK the new node in front of the old head
		head = newNode;
		return head; // RETURN the new head
	}

	public static ListNode insertAtHead(ListNode head, ListNode node) {
		ListNode newNode = node;
		newNode.next = head; // LINK the given node in front of the old head
		head = newNode;
		return head; // RETURN the new head
	}

	public static ListNode fromList(List<Integer> list) {
		if (list == null || list.size() == 0) {
			throw new IllegalArgumentException("invalid input");
		}
		ListNode head = new ListNode(list.get(0));
		ListNode pointer = head;
		for (int i = 1; i < list.size(); i++) {
			pointer.next = new ListNode(list.get(i)); // APPEND one node per value
			pointer = pointer.next;
		}
		return head;
	}

	public static ListNode fromArray(int[] numbers) {
		if (numbers == null || numbers.length == 0) {
			throw new IllegalArgumentException("invalid input");
		}
		ListNode head = new ListNode(numbers[0]);
		ListNode pointer = head;
		for (int i = 1; i < numbers.length; i++) {
			pointer.next = new ListNode(numbers[i]); // APPEND one node per value
			pointer = pointer.next;
		}
		return head;
	}

	public static int size(ListNode node) {
		int count = 0;
		while (node != null) {
			count++; // COUNT one node
			node = node.next; // WALK
		}
		return count;
	}

	public int sum(ListNode head) {
		int sum = 0;
		ListNode curr = head;
		while (curr != null) {
			sum += curr.val; // ADD this node's value
			curr = curr.next; // WALK
		}
		return sum;
	}

	// walks until null, so on a list with a cycle it never stops
	public static void print(ListNode current) {
		while (current != null) {
			System.out.print(current + " "); // PRINT the value
			current = current.next; // WALK
		}
		System.out.println();
	}

	// the list must be circular: it stops when it is back on the node it started from
	public static void printCircular(ListNode start) {
		if (start != null) {
			System.out.print(start + " "); // PRINT the start first, so the loop can test for it
			ListNode curr = start.next;
			while (curr != start) { // WALK once round the ring
				System.out.print(curr + " ");
				curr = curr.next;
			}
		}
		System.out.println();
	}

	public static ListNode insertSorted(ListNode head, int data) {
		ListNode node = new ListNode(data);
		return insertSorted(head, node);
	}

	public static ListNode insertSorted(ListNode head, ListNode node) {
		if (head == null || head.val >= node.val) {
			node.next = head; // the new node goes first
			head = node;
		} else {
			// locate the node before the point of insertion
			ListNode current = head;
			while (current.next != null && current.next.val < node.val) { // WALK past smaller values
				current = current.next;
			}
			node.next = current.next; // LINK the new node to what follows
			current.next = node; // LINK the node before to the new node
		}
		return head;
	}

	public static boolean contains(ListNode head, int item) {
		return search(head, item).isPresent();
	}

	// the first node holding target, or empty if no node holds it
	public static Optional<ListNode> search(ListNode head, int target) {
		ListNode current = head;
		while (current != null) { // every node, the last one included
			if (current.val == target) {
				return Optional.of(current); // FOUND
			}
			current = current.next;
		}
		return Optional.empty(); // NOT FOUND
	}

	public static ListNode appendToTail(ListNode head, int data) {
		ListNode end = new ListNode(data);
		ListNode current;
		if (head == null) {
			current = new ListNode(data);
			head = current;
		} else {
			current = head;
			while (current.next != null) { // WALK to the last node
				current = current.next;
			}
			current.next = end; // LINK the new node after the last one
		}
		return head;
	}

	public static ListNode swap(ListNode curr, ListNode next) {
		int temp = curr.val;
		curr.val = next.val; // SWAP the two values, not the nodes
		next.val = temp;
		return curr;
	}

	public static ListNode getTail(ListNode head) {
		while (head != null && head.next != null) { // WALK until the node with no next
			head = head.next;
		}
		return head;
	}

}
