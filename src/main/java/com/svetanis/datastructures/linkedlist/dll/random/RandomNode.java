package com.svetanis.datastructures.linkedlist.dll.random;

import java.util.Arrays;
import java.util.Random;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 382. Linked List Random Node
//
// The constructor gets the head of a singly linked list with at least one node. Each
// getRandom() call returns the value of one node, every node equally likely.
//
// return a uniformly random node's value. reservoir sampling: the ith
// node seen replaces the held value with probability 1 / i, which leaves
// every node equally likely without knowing the length in advance.
// the neighbouring CloneWithRandom* files are 138, a different problem.
// Node i is the one returned when it is picked at its own turn (1 / i) and then not
// replaced at any later turn ((i / (i + 1)) * ... * ((n - 1) / n)); the product is 1 / n.

public final class RandomNode {
	// Time Complexity: O(n) per call, every call walks the whole list
	// Space Complexity: O(1)

	private final ListNode head;
	private final Random generator = new Random();

	public RandomNode(ListNode head) {
		this.head = head;
	}

	public int getRandom() {
		int random = 0;
		int index = 0;
		for (ListNode node = head; node != null; node = node.next) {
			index++;
			int rand = 1 + generator.nextInt(index); // 1 .. index, each equally likely
			if (index == rand) { // true with probability 1 / index
				random = node.val;
			}
		}
		return random;
	}

	public static void main(String[] args) {
		ListNode head = Nodes.fromList(Arrays.asList(1, 2, 3));
		RandomNode rn = new RandomNode(head);
		System.out.println(rn.getRandom());
		System.out.println(rn.getRandom());
		System.out.println(rn.getRandom());
		System.out.println(rn.getRandom());
		System.out.println(rn.getRandom());
	}
}
