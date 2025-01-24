package com.svetanis.datastructures.linkedlist.single.palindrome;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 234. Palindrome Linked List

// given the head of a Singly LinkedList
// check if the SLL is a palindrom or not

public final class Palindrome {
	// Time Complexity: O(n)

	public static boolean isPalindrome(Node head) {
		if (head == null || head.next == null) {
			return true;
		}
		Node mid = middle(head);
		Node reversed = reverse(mid);
		Node curr1 = head;
		Node curr2 = reversed;
		while (curr2 != null) {
			if (curr1.data != curr2.data) {
				return false;
			}
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		return true;
	}

	public static Node middle(Node head) {
		if (head == null) {
			return null;
		}
		Node slow = head;
		Node fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	public static Node reverse(Node head) {
		Node prev = null;
		Node curr = head;
		while (curr != null) {
			Node next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}

	public static void main(String[] args) {
		// 0->1->2->1->0
		Node head = fromList(newArrayList(0, 1, 2, 1, 0));
		System.out.println(isPalindrome(head));

		Node head1 = fromList(newArrayList(0, 1, 3, 0));
		System.out.println(isPalindrome(head1));
	}
}
