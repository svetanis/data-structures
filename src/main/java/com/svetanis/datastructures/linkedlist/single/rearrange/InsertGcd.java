package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 2807. Insert Greatest Common Divisors in Linked List

public final class InsertGcd {
	// Time Complexity: O(n * log(min(a,b)))
	// Space Complexity: O(n)

	public static Node insert(Node head) {
		Node prev = head;
		Node curr = head.next;
		while (curr != null) {
			int gcd = gcd(prev.data, curr.data);
			Node node = new Node(gcd);
			node.next = curr;
			prev.next = node;
			prev = curr;
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
		Node head = Nodes.fromList(Arrays.asList(18, 6, 10, 3));
		print(insert(head)); // 18,6,6,2,10,1,3

		Node head2 = Nodes.fromList(Arrays.asList(7));
		print(insert(head2)); // 7
	}
}
