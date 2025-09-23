package com.svetanis.datastructures.linkedlist.single.remove;

import java.util.HashMap;
import java.util.Map;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 1171. Remove Zero Sum Consecutive Nodes from Linked List

public final class RemoveZeroSumNodes {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static Node remove(Node head) {
		Node dummy = new Node(0);
		dummy.next = head;
		Map<Integer, Node> map = prefix(dummy);
		int sum = 0;
		Node curr = dummy;
		while (curr != null) {
			sum += curr.data;
			curr.next = map.get(sum).next;
			curr = curr.next;
		}
		return dummy.next;
	}

	private static Map<Integer, Node> prefix(Node head) {
		Map<Integer, Node> map = new HashMap<>();
		int sum = 0;
		Node curr = head;
		while (curr != null) {
			sum += curr.data;
			map.put(sum, curr);
			curr = curr.next;
		}
		return map;
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, -3, 3, 1 };
		Node head = Nodes.fromArray(a);
		Nodes.print(remove(head)); // 3,1

		int[] a1 = { 1, 2, 3, -3, 4 };
		Node head1 = Nodes.fromArray(a1);
		Nodes.print(remove(head1)); // 1,2,4

		int[] a2 = { 1, 2, 3, -3, -2 };
		Node head2 = Nodes.fromArray(a2);
		Nodes.print(remove(head2)); // 1
	}
}
