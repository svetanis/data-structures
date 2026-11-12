package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 148. Sort List
//
// Input: head -- the first node of a singly linked list. On LeetCode: 0 to 5 * 10^4 nodes, each
// value -10^5 to 10^5. Returns the head of the same nodes rearranged into increasing order.
//
// The one idea: merging two lists that are already sorted is easy -- compare the two front nodes
// and take the smaller one (LC 21) -- so cut the list in half, sort each half the same way, and
// merge. A list of zero or one node is already sorted, which stops the cutting; the null test
// comes first, so an empty list never reads head.next. A linked list has no index to jump to its
// middle, so slow and fast walk from head, fast two nodes for each one of slow's, and the loop
// runs only while fast has two more nodes ahead. That leaves slow on the FIRST middle: for two
// nodes the halves are one and one, so every cut makes both halves strictly shorter and the
// calls always end. The other test, while (fast != null && fast.next != null), finds the second
// middle, cuts [4,2] into [4,2] and nothing, and calls itself on the same list forever. The
// second half's first node is saved before the arrow after slow is cut. The merge hangs the
// smaller front node behind a fake node each turn; a tie takes the first half's node, so the
// sort is stable.
//
// Time: O(n log n) -- counted: about 0.91 n log2 n comparisons from n = 12,500 to 50,000.
// Space: O(log n) -- the waiting calls, one per halving: 17 at n = 50,000, 21 at n = 1,000,000.
// The follow-up's O(1) memory needs a bottom-up merge sort, with no recursion.
//
// Siblings: InsertionSortList (LC 147) sorts the same kind of list by inserting one node at a
// time, in O(n^2). MergeTwoSortedDummy (LC 21) is the same merge loop as a method of its own.

public final class MergeSort {

	public static ListNode sort(ListNode head) {
		if (head == null || head.next == null) { // STOP: empty or one node; the null test first
			return head;
		}
		ListNode slow = head; // MIDDLE: the FIRST middle --
		ListNode fast = head; // a two-node list must cut one and one
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		ListNode secondHalf = slow.next; // SAVE: the cut destroys the only way there
		slow.next = null; // CUT: the arrow, not the name
		ListNode firstSorted = sort(head); // SORT each half -- both strictly shorter
		ListNode secondSorted = sort(secondHalf);
		return mergeTwoLists(firstSorted, secondSorted); // MERGE: LC 21, unchanged
	}

	private static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		ListNode dummy = new ListNode(0); // FAKE: the answer has no first node yet
		ListNode tail = dummy; // TAIL: the answer's last node
		while (list1 != null && list2 != null) { // STOP: every COMPARE needs both fronts
			if (list1.val <= list2.val) { // COMPARE the fronts; a tie takes list1
				tail.next = list1; // ATTACH
				list1 = list1.next; // ADVANCE
			} else {
				tail.next = list2; // ATTACH
				list2 = list2.next; // ADVANCE
			}
			tail = tail.next; // TAIL moves onto the node just attached
		}
		tail.next = (list1 != null) ? list1 : list2; // LEFTOVER: sorted, ends in null: one arrow
		return dummy.next; // RETURN: the answer's real first node
	}

	public static void main(String[] args) {
		int[] a = { 4, 2, 1, 3 };
		ListNode head = fromArray(a);
		print(sort(head)); // 1 2 3 4

		int[] a1 = { -1, 5, 3, 4, 0 };
		ListNode head1 = fromArray(a1);
		print(sort(head1)); // -1 0 3 4 5
	}
}
