package com.svetanis.datastructures.linkedlist.single.reverse;

// 1265. Print Immutable Linked List in Reverse
//
// Input: the head of a list that can only be read. LeetCode gives each node two methods:
// printValue() prints that node's value, and getNext() returns the next node. No pointer can be
// changed. 1 <= n <= 1000 nodes, values -1000..1000. Returns: nothing; the values are printed
// from last to first.
//
// The one idea: one call is one node. It first handles every node after its node, and only then
// prints its own value, so the last node is printed first. The calls still waiting to finish
// hold the nodes, in place of a stack.
//
// The nested ImmutableNode stands in for the judge's interface; its printValue() prints the
// value, as the judge's does. main builds a list by hand and runs it. Assumption: the largest
// legal list, n = 1000, runs on a default JVM stack.
//
// Time: O(n) -- one call per node.
// Space: O(n) -- recursion stack, one waiting call per node.
//
// Siblings: PrintInReverse is the same three lines on this repo's ListNode.

public final class PrintImmutableInReverse {

	public void printInReverse(ImmutableNode head) {
		if (head != null) { // BASE: past the end
			printInReverse(head.getNext()); // RECURSE: everything after head is printed first
			head.printValue(); // PRINT: on the way back
		}
	}

	public static void main(String[] args) {
		PrintImmutableInReverse printer = new PrintImmutableInReverse();
		ImmutableNode head = new ImmutableNode(1, new ImmutableNode(2, new ImmutableNode(3, null)));
		printer.printInReverse(head); // 3 2 1
		System.out.println();

		ImmutableNode single = new ImmutableNode(7, null);
		printer.printInReverse(single); // 7
		System.out.println();

		ImmutableNode head2 = new ImmutableNode(0, new ImmutableNode(-5, new ImmutableNode(9, new ImmutableNode(9, null))));
		printer.printInReverse(head2); // 9 9 -5 0
		System.out.println();
	}

	private static class ImmutableNode {

		private final int val;
		private final ImmutableNode next;

		private ImmutableNode(int val, ImmutableNode next) {
			this.val = val;
			this.next = next;
		}

		public ImmutableNode getNext() {
			return next;
		}

		public void printValue() {
			System.out.print(val + " ");
		}
	}
}
