package com.svetanis.datastructures.tree.binary.bt.connect;

public class Node {

	public int data;
	public Node left;
	public Node right;
	public Node next;

	public static Node newNode(int data) {
		return new Node(data);
	}

	public Node(int data) {
		this.data = data;
		this.left = null;
		this.right = null;
		this.next = null;
	}

	public void printLevelOrder() {
		Node next = this;
		while (next != null) {
			Node curr = next;
			next = null;
			while (curr != null) {
				System.out.print(curr.data + " ");
				if (next == null) {
					if (curr.left != null) {
						next = curr.left;
					} else if (curr.right != null) {
						next = curr.right;
					}
				}
				curr = curr.next;
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public String toString() {
		return Integer.toString(data);
	}
}