package com.svetanis.datastructures.tree.binary.bt.connect;

// The node of the next-pointer problems in this package: LC 116 and 117 (Populating Next Right
// Pointers in Each Node), ConnectSameLevelNodes, ConnectSameLevelNodesMemoryOptimized and
// ConnectAllLevelOrderSiblings. next starts out null and is set by those solutions.
//
// printTree follows next pointers from this node and prints the values on one line.
// printLevelOrder prints one line per level: it follows next along a level and takes the first
// child it meets as the start of the line below. It needs the next pointers of each level to be
// set, and prints the whole tree only when each level is linked.

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

	public void printTree() {
		Node curr = this;
		System.out.println("Traversal using next pointer:");
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
		System.out.println();
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