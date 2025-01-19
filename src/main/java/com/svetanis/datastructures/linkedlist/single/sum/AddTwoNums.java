package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 2. Add Two Numbers

public final class AddTwoNums {
	// Time Complexity: O(n + m)
	// Space Complexity: O(1)

	public static Node addTwoLists(Node first, Node second) {
		int carry = 0;
		Node dummy = new Node(0);
		Node curr = dummy;
		while (first != null || second != null || carry != 0) {
			int num1 = first == null ? 0 : first.data;
			int num2 = second == null ? 0 : second.data;
			int sum = num1 + num2 + carry;
			carry = sum / 10;
			// create a new node with digit value of the sum
			curr.next = new Node(sum % 10);
			// move to the next node in the result list
			curr = curr.next;
			// move first and second pointers to next node
			first = first == null ? null : first.next;
			second = second == null ? null : second.next;
		}
		return dummy.next;
	}

	public static Node addTwoNumbers(Node first, Node second) {
		int carry = 0;
		Node dummy = new Node(0);
		Node curr = dummy;
		while (first != null || second != null) {
			int x = first != null ? first.data : 0;
			int y = second != null ? second.data : 0;
			int sum = x + y + carry;
			carry = sum / 10;
			curr.next = new Node(sum % 10);
			curr = curr.next;
			first = first == null ? null : first.next;
			second = second == null ? null : second.next;
		}
		if (carry > 0) {
			curr.next = new Node(carry);
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		Node num1 = fromList(asList(2, 4, 3));
		Node num2 = fromList(asList(5, 6, 4));
		print(addTwoLists(num1, num2)); // 7 0 8

		Node num3 = fromList(asList(0));
		Node num4 = fromList(asList(0));
		print(addTwoLists(num3, num4)); // 0

		Node num5 = fromList(asList(9, 9, 9, 9, 9, 9, 9));
		Node num6 = fromList(asList(9, 9, 9, 9));
		print(addTwoLists(num5, num6)); // 8 9 9 9 0 0 0 1
	}
}
