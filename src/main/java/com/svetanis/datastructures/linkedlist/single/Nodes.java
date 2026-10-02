package com.svetanis.datastructures.linkedlist.single;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.java.base.Exceptions.illegalArgument;

import java.util.List;

import com.google.common.base.Optional;

// Static helpers for lists of ListNode, used by the main methods across this repo: build a
// list from a List or an array (both refuse an empty input), print, count, insert, search,
// and find the last node. LeetCode builds its input lists itself, so a method meant to be
// pasted into LeetCode must not call these. sum is the one helper that is not static.

public final class Nodes {

	public static boolean isNotNull(ListNode node) {
		return !isNull(node);
	}

	public static boolean isNull(ListNode node) {
		return node == null;
	}

	public static ListNode insertAtHead(ListNode head, int data) {
		ListNode newNode = new ListNode(data);
		newNode.next = head;
		head = newNode;
		return head;
	}

	public static ListNode insertAtHead(ListNode head, ListNode node) {
		ListNode newNode = node;
		newNode.next = head;
		head = newNode;
		return head;
	}

	public static ListNode fromList(List<Integer> list) {
		if (list == null || list.size() == 0) {
			throw illegalArgument("invalid input");
		}
		ListNode head = new ListNode(list.get(0));
		ListNode pointer = head;
		for (int i = 1; i < list.size(); i++) {
			pointer.next = new ListNode(list.get(i));
			pointer = pointer.next;
		}
		return head;
	}

	public static ListNode fromArray(int[] numbers) {
		if (numbers == null || numbers.length == 0) {
			throw illegalArgument("invalid input");
		}
		ListNode head = new ListNode(numbers[0]);
		ListNode pointer = head;
		for (int i = 1; i < numbers.length; i++) {
			pointer.next = new ListNode(numbers[i]);
			pointer = pointer.next;
		}
		return head;
	}

	public static int size(ListNode node) {
		int count = 0;
		while (node != null) {
			count++;
			node = node.next;
		}
		return count;
	}

	public int sum(ListNode head) {
		int sum = 0;
		ListNode curr = head;
		while (curr != null) {
			sum += curr.val;
			curr = curr.next;
		}
		return sum;
	}

	// walks until null, so on a list with a cycle it never stops
	public static void print(ListNode current) {
		while (current != null) {
			System.out.print(current + " ");
			current = current.next;
		}
		System.out.println();
	}

	public static void printCircular(ListNode start) {
		if (start != null) {
			ListNode curr = start;
			do {
				System.out.print(curr + " ");
				curr = curr.next;
			} while (curr != start);
		}
		System.out.println();
	}

	public static ListNode insertSorted(ListNode head, int data) {
		ListNode node = new ListNode(data);
		return insertSorted(head, node);
	}

	public static ListNode insertSorted(ListNode head, ListNode node) {
		if (head == null || head.val >= node.val) {
			node.next = head;
			head = node;
		} else {
			// locate the node before the point of insertion
			ListNode current = head;
			while (current.next != null && current.next.val < node.val) {
				current = current.next;
			}
			node.next = current.next;
			current.next = node;
		}
		return head;
	}

	public static boolean contains(ListNode head, int item) {
		return search(head, item).isPresent();
	}

	// the first node holding target, or absent if no node holds it
	public static Optional<ListNode> search(ListNode head, int target) {
		ListNode current = head;
		while (current != null) { // every node, the last one included
			if (current.val == target) {
				return of(current);
			}
			current = current.next;
		}
		return absent();
	}

	public static ListNode appendToTail(ListNode head, int data) {
		ListNode end = new ListNode(data);
		ListNode current;
		if (head == null) {
			current = new ListNode(data);
			head = current;
		} else {
			current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = end;
		}
		return head;
	}

	public static ListNode swap(ListNode curr, ListNode next) {
		int temp = curr.val;
		curr.val = next.val;
		next.val = temp;
		return curr;
	}

	public static ListNode getTail(ListNode head) {
		while (head != null && head.next != null) {
			head = head.next;
		}
		return head;
	}

}
