package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 142. Linked List Cycle II

// given the head of a Singly LinkedList 
// that contains a cycle, find the 
// starting node of the cycle

public final class CycleStartPointSubmit {
	// Time Complexity: O(n)

	public static Node cycleStart(Node head) {
		if (head == null) {
			return null;
		}
		Node slow = head;
		Node fast = head;
		// find the meeting point
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				Node start = head;
				while (start != slow) {
					start = start.next;
					slow = slow.next;
				}
				return start;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Node head = fromList(asList(50, 20, 15, 4, 10));
		print(head);
		System.out.println(cycleStart(head));
		// create a loop for testing
		head.next.next.next.next.next = head.next.next;
		System.out.println(cycleStart(head));
	}
}
