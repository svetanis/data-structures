package com.svetanis.datastructures.linkedlist.single;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.java.base.Exceptions.illegalArgument;

import java.util.List;

import com.google.common.base.Optional;

public final class Nodes {

	public static boolean isNotNull(Node node) {
		return !isNull(node);
	}

	public static boolean isNull(Node node) {
		return node == null;
	}

	public static Node insertAtHead(Node head, int data) {
		Node newNode = new Node(data);
		newNode.next = head;
		head = newNode;
		return head;
	}

	public static Node insertAtHead(Node head, Node node) {
		Node newNode = node;
		newNode.next = head;
		head = newNode;
		return head;
	}

	public static Node fromList(List<Integer> list) {
		if (list == null || list.size() == 0) {
			throw illegalArgument("invalid input");
		}
		Node head = new Node(list.get(0));
		Node pointer = head;
		for (int i = 1; i < list.size(); i++) {
			pointer.next = new Node(list.get(i));
			pointer = pointer.next;
		}
		return head;
	}

	public static Node fromArray(int[] numbers) {
		if (numbers == null || numbers.length == 0) {
			throw illegalArgument("invalid input");
		}
		Node head = new Node(numbers[0]);
		Node pointer = head;
		for (int i = 1; i < numbers.length; i++) {
			pointer.next = new Node(numbers[i]);
			pointer = pointer.next;
		}
		return head;
	}

	public static int size(Node node) {
		int count = 0;
		while (node != null) {
			count++;
			node = node.next;
		}
		return count;
	}

	public int sum(Node head) {
		int sum = 0;
		Node curr = head;
		while (curr != null) {
			sum += curr.data;
			curr = curr.next;
		}
		return sum;
	}

	public static void print(Node current) {
		while (current != null) {
			System.out.print(current + " ");
			current = current.next;
		}
		System.out.println();
	}

	public static void printCircular(Node start) {
		if (start != null) {
			Node curr = start;
			do {
				System.out.print(curr + " ");
				curr = curr.next;
			} while (curr != start);
		}
		System.out.println();
	}

	public static Node insertSorted(Node head, int data) {
		Node node = new Node(data);
		return insertSorted(head, node);
	}

	public static Node insertSorted(Node head, Node node) {
		if (head == null || head.data >= node.data) {
			node.next = head;
			head = node;
		} else {
			// locate the node before the point of insertion
			Node current = head;
			while (current.next != null && current.next.data < node.data) {
				current = current.next;
			}
			node.next = current.next;
			current.next = node;
		}
		return head;
	}

	public static boolean contains(Node head, int item) {
		return search(head, item).isPresent();
	}

	public static Optional<Node> search(Node head, int target) {
		Node current = head;
		while (current != null && current.next != null) {
			if (current.data == target) {
				return of(current);
			}
			current = current.next;
		}
		return absent();
	}

	public static Node appendToTail(Node head, int data) {
		Node end = new Node(data);
		Node current;
		if (head == null) {
			current = new Node(data);
			head = current;
		} else {
			current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = end;
		}
		return head;
	}

	public static Node swap(Node curr, Node next) {
		int temp = curr.data;
		curr.data = next.data;
		next.data = temp;
		return curr;
	}

	public static Node getTail(Node head) {
		while (head != null && head.next != null) {
			head = head.next;
		}
		return head;
	}

}
