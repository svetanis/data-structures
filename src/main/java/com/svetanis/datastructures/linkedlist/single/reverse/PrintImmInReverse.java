package com.svetanis.datastructures.linkedlist.single.reverse;

// 1265. Print Immutable Linked List in Reverse
//
// The list can only be read: each node gives its value through printValue() and the next node
// through getNext(), and no pointer can be changed. Print the values from last to first.
//
// Recursion does the reversing. The call for one node first handles every node after it, and
// only then prints its own value, so the last node is printed first. The calls still waiting to
// finish hold the nodes, in place of a stack.
//
// The nested ImmutableNode stands in for the judge's interface. It has no way to build a list,
// and its printValue() hands the value back instead of printing it, so main runs nothing.

public final class PrintImmInReverse {
	// Time Complexity: O(n), one call per node
	// Space Complexity: O(n) recursion stack, one waiting call per node

	public void printInReverse(ImmutableNode head) {
		if (head != null) {
			printInReverse(head.getNext());
			head.printValue();
		}
	}

	public static void main(String[] args) {}

	private static class ImmutableNode {

		private int val;
		private ImmutableNode next;

		public ImmutableNode getNext() {
			return next;
		}

		public int printValue() {
			return val;
		}
	}
}
