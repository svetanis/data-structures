package com.svetanis.datastructures.linkedlist.single.reverse;

// 1265. Print Immutable Linked List in Reverse

public final class PrintImmInReverse {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

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
