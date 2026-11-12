package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 237. Delete Node in a Linked List
//
// Input: one node of a singly linked list, never the last one. LeetCode: 2 <= n <= 1000 nodes,
// values -1000..1000 and all different. There is no access to the head. Returns: nothing; the
// list, read from its head, must look as if that node had been removed.
//
// The one idea: a node can only be unlinked by changing the next of the node before it, and
// that node is out of reach. One node holds a value and an arrow to the next node. So the given
// node takes over the value of the node after it, and the arrow is moved past that next node,
// which unlinks it instead. Read from the head, the list then looks as if the given node had
// been removed. LC 237 promises the node is not the last one, so a next node always exists.
//
// Time: O(1) -- two assignments.
// Space: O(1) -- nothing is allocated.

public final class DeleteNode {

	public void deleteNode(ListNode node) {
		node.val = node.next.val; // COPY: take over the next node's value
		node.next = node.next.next; // SKIP: unlink the next node, whose value now lives here
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(4, 5, 1, 9));
		ListNode five = head.next;
		new DeleteNode().deleteNode(five); // CALL: remove the node holding 5
		print(head); // 4 1 9

		ListNode head1 = fromList(asList(4, 5, 1, 9));
		ListNode one = head1.next.next;
		new DeleteNode().deleteNode(one); // CALL: remove the node holding 1
		print(head1); // 4 5 9
	}
}
