package com.svetanis.datastructures.linkedlist.dll.random;

import java.util.Arrays;
import java.util.Random;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 382. Linked List Random Node
//
// Input: the constructor gets the head of a singly linked list. Each getRandom() call returns
// the value of one node, every node equally likely. On LeetCode: 1 <= number of nodes <= 10^4,
// -10^4 <= val <= 10^4, at most 10^4 calls to getRandom.
//
// The one idea: the length is not known, and walking the list twice is not needed. Keep one
// value, the "held" one. Visit the nodes in order; the i-th node visited replaces the held
// value with probability 1 / i. The first node is always held (1 / 1). Node i ends up as the
// answer when it is picked at its own turn (1 / i) and then not replaced at any later turn:
// (i / (i + 1)) * ((i + 1) / (i + 2)) * ... * ((n - 1) / n) = i / n. Multiply: (1 / i) * (i / n)
// = 1 / n, the same for every node. This is called reservoir sampling.
//
// Time: O(n) per getRandom call -- every call walks the whole list; the constructor is O(1).
// Space: O(1) -- the head, a random number generator and a few ints.
//
// The neighbouring CloneWithRandom* files are LC 138, a different problem.

public final class RandomNode {

	private final ListNode head;
	private final Random generator = new Random();

	public RandomNode(ListNode head) {
		this.head = head;
	}

	public int getRandom() {
		int random = 0;
		int index = 0;
		for (ListNode node = head; node != null; node = node.next) { // WALK every node
			index++; // COUNT: this is the index-th node
			int rand = 1 + generator.nextInt(index); // 1 .. index, each equally likely
			if (index == rand) { // TAKE: true with probability 1 / index
				random = node.val;
			}
		}
		return random; // RETURN: the held value
	}

	public static void main(String[] args) {
		ListNode head = Nodes.fromList(Arrays.asList(1, 2, 3));
		RandomNode rn = new RandomNode(head);
		System.out.println(rn.getRandom()); // 1, 2 or 3, each with probability 1 / 3
		System.out.println(rn.getRandom()); // 1, 2 or 3
		System.out.println(rn.getRandom()); // 1, 2 or 3
		System.out.println(rn.getRandom()); // 1, 2 or 3
		System.out.println(rn.getRandom()); // 1, 2 or 3
	}
}
