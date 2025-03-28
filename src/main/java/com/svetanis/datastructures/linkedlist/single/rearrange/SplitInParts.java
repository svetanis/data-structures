package com.svetanis.datastructures.linkedlist.single.rearrange;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 725. Split Linked List in Parts

public final class SplitInParts {

	public static Node[] split(Node head, int k) {
		int size = size(head);
		int width = size / k;
		int extra = size % k;
		Node[] nodes = new Node[k];
		Node curr = head;
		for (int i = 0; i < k; i++) {
			Node phead = curr;
			int psize = width + (i < extra ? 1 : 0);
			for (int j = 0; j < psize - 1; j++) {
				if (curr != null) {
					curr = curr.next;
				}
			}
			if (curr != null) {
				Node next = curr.next;
				curr.next = null;
				curr = next;
			}
			nodes[i] = phead;
		}
		return nodes;
	}

	private static int size(Node head) {
		int count = 0;
		while (head != null) {
			head = head.next;
			count++;
		}
		return count;
	}

	public static void main(String[] args) {
		Node head = Nodes.fromList(Arrays.asList(1, 2, 3));
		Node[] nodes = split(head, 5);
		for (Node node : nodes) {
			Nodes.print(node); // [1],[2],[3],[],[]
		}

		Node head2 = Nodes.fromList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		Node[] nodes2 = split(head2, 3);
		for (Node node : nodes2) {
			Nodes.print(node); // [1,2,3,4],[5,6,7],[8,9,10]
		}
	}
}
