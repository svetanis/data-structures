package com.svetanis.datastructures.linkedlist.single.palindrome;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 234. Palindrome Linked List
//
// Input: the head of a singly linked list. On LeetCode: 1 <= number of nodes <= 10^5,
// 0 <= val <= 9. Returns true when the values read the same from the back as from the front
// (0 1 2 1 0). The list is the same when the method returns as when it was called.
//
// The one idea: a singly linked list can only be walked forward, so the back half cannot be
// read from the end. Turn the back half around instead. Find the middle node with two pointers
// (fast moves two nodes for each one that slow moves; slow stops on the middle node, or on the
// first node of the back half when the length is even), reverse the list from there on, and walk
// the front and the reversed back half together comparing values; the front read forward is
// then compared with the back read backward. The node before the middle still points at the
// middle, so reversing the back half a second time puts the list back exactly as it was.
//
// Time: O(n) -- a few passes over half the list each.
// Space: O(1) -- the back half is reversed in place and then restored.

public final class Palindrome {

	public static boolean isPalindrome(ListNode head) {
		if (head == null || head.next == null) {
			return true; // BASE: no node or one node reads the same both ways
		}
		ListNode mid = middle(head); // MIDDLE
		ListNode reversed = reverse(mid); // REVERSE the back half
		boolean result = compare(head, reversed); // COMPARE
		// put the second half back the way it was found. the signature
		// promises a boolean and says nothing about mutating the input --
		// "your solution modified my list" is LC 234's standard follow-up
		reverse(reversed); // RESTORE the back half
		return result;
	}

	private static boolean compare(ListNode head, ListNode reversed) {
		ListNode curr1 = head;
		ListNode curr2 = reversed;
		while (curr2 != null) { // the reversed half is never longer than the front part
			if (curr1.val != curr2.val) {
				return false; // MISMATCH
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
			slow = slow.next; // STEP: one node
			fast = fast.next.next; // STEP: two nodes
		}
		return slow;
	}

	public static ListNode reverse(ListNode head) {
		ListNode prev = null;
		ListNode curr = head;
		while (curr != null) {
			ListNode next = curr.next; // SAVE
			curr.next = prev; // FLIP
			prev = curr; // KEEP
			curr = next; // MOVE
		}
		return prev;
	}

	public static void main(String[] args) {
		// 0->1->2->1->0
		ListNode head = fromList(asList(0, 1, 2, 1, 0));
		System.out.println(isPalindrome(head)); // true

		ListNode head1 = fromList(asList(0, 1, 3, 0));
		System.out.println(isPalindrome(head1)); // false
	}
}
