package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 160. Intersection of Two Linked Lists

public final class IntersectionNode {
	// Time Complexity: O(n + m)
	// Auxiliary Space: O(1)

	public static Node intersection(Node head1, Node head2) {
		Node curr1 = head1;
		Node curr2 = head2;
		while (curr1 != curr2) {
			curr1 = curr1 == null ? head2 : curr1.next;
			curr2 = curr2 == null ? head1 : curr2.next;
		}
		return curr1;
	}

	public static void main(String[] args) {
		Node head1 = new Node(10);
		Node head2 = new Node(3);
		head2.next = new Node(6);
		head2.next.next = new Node(9);
		Node node3 = new Node(15);
		head1.next = node3;
		head2.next.next.next = node3;
		head1.next.next = new Node(30);
		print(head1);
		print(head2);
		System.out.println(intersection(head1, head2));
	}
}
