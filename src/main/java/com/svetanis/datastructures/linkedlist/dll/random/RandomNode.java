package com.svetanis.datastructures.linkedlist.dll.random;

import java.util.Arrays;
import java.util.Random;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 138. Copy List with Random Pointer

public final class RandomNode {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	private Node head;
	private Random generator = new Random();

	public RandomNode(Node head) {
		this.head = head;
	}

	public int getRandom() {
		int random = 0;
		int index = 0;
		for (Node node = head; node != null; node = node.next) {
			index++;
			int rand = 1 + generator.nextInt(index);
			if (index == rand) {
				random = node.data;
			}
		}
		return random;
	}

	public static void main(String[] args) {
		Node head = Nodes.fromList(Arrays.asList(1, 2, 3));
		RandomNode rn = new RandomNode(head);
		System.out.println(rn.getRandom());
		System.out.println(rn.getRandom());
		System.out.println(rn.getRandom());
		System.out.println(rn.getRandom());
		System.out.println(rn.getRandom());
	}
}
