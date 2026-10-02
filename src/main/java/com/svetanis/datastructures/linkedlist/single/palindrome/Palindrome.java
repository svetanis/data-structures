package com.svetanis.datastructures.linkedlist.single.palindrome;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 234. Palindrome Linked List
//
// Given the head of a singly linked list, check whether it is a palindrome.
// Return true when the values read the same from the back as from the front (0 1 2 1 0).
// The list is the same when the method returns as when it was called.
//
// The back half can only be walked forward, so find the middle node with two pointers
// (fast moves two nodes for each one that slow moves; slow stops on the middle node, or on
// the first node of the back half when the length is even), reverse the list from there on,
// and walk the front and the reversed back half together comparing values. The node before
// the middle still points at the middle, so reversing the back half a second time puts the
// list back exactly as it was.

public final class Palindrome {
	// Time Complexity: O(n), a few passes over half the list each
	// Space Complexity: O(1), the back half is reversed in place and then restored

	public static boolean isPalindrome(ListNode head) {
		if (head == null || head.next == null) {
			return true;
		}
		ListNode mid = middle(head);
		ListNode reversed = reverse(mid);
		boolean result = compare(head, reversed);
		// put the second half back the way it was found. the signature
		// promises a boolean and says nothing about mutating the input --
		// "your solution modified my list" is LC 234's standard follow-up
		reverse(reversed);
		return result;
	}

	private static boolean compare(ListNode head, ListNode reversed) {
		ListNode curr1 = head;
		ListNode curr2 = reversed;
		while (curr2 != null) {
			if (curr1.val != curr2.val) {
				return false;
			}
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		return true;
	}

	public static ListNode middle(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode slow = head;
		ListNode fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	public static ListNode reverse(ListNode head) {
		ListNode prev = null;
		ListNode curr = head;
		while (curr != null) {
			ListNode next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}

	public static void main(String[] args) {
		// 0->1->2->1->0
		ListNode head = fromList(newArrayList(0, 1, 2, 1, 0));
		System.out.println(isPalindrome(head)); // true

		ListNode head1 = fromList(newArrayList(0, 1, 3, 0));
		System.out.println(isPalindrome(head1)); // false
	}
}
