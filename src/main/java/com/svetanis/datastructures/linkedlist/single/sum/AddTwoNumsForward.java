package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.linkedlist.single.Node;

// 445. Add Two Numbers II

public final class AddTwoNumsForward {
	// Time Complexity: O(n + m)
	// Space Complexity: O(1)

	public static Node addTwoLists(Node first, Node second) {
		Deque<Integer> dq1 = init(first);
		Deque<Integer> dq2 = init(second);

		int carry = 0;
		Node dummy = new Node(0);
		while (!dq1.isEmpty() || !dq2.isEmpty() || carry != 0) {
			int num1 = dq1.isEmpty() ? 0 : dq1.pop();
			int num2 = dq2.isEmpty() ? 0 : dq2.pop();
			int sum = num1 + num2 + carry;
			carry = sum / 10;
			// create a new node with digit value of the sum
			Node node = new Node(sum % 10);
			node.next = dummy.next;
			dummy.next = node;
		}
		return dummy.next;
	}

	private static Deque<Integer> init(Node head) {
		Deque<Integer> dq1 = new ArrayDeque<>();
		while (head != null) {
			dq1.push(head.data);
			head = head.next;
		}
		return dq1;
	}

	public static void main(String[] args) {
		Node num1 = fromList(asList(7, 2, 4, 3));
		Node num2 = fromList(asList(5, 6, 4));
		print(addTwoLists(num1, num2)); // 7 8 0 7

		Node num3 = fromList(asList(2, 4, 3));
		Node num4 = fromList(asList(5, 6, 4));
		print(addTwoLists(num3, num4)); // 8 0 7

		Node num5 = fromList(asList(0));
		Node num6 = fromList(asList(0));
		print(addTwoLists(num5, num6)); // 0
	}
}
