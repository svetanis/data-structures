package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.Node;

public final class BfsTraversal {

	public static ImmutableList<Integer> bfs(Node src) {
		Set<Node> set = newHashSet();
		List<Integer> list = newArrayList();
		Queue<Node> queue = newLinkedList();
		queue.add(src);
		set.add(src);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			list.add(node.getData());
			for (Node neighbour : node.getNeighbors()) {
				if (!set.contains(neighbour)) {
					queue.add(neighbour);
					set.add(neighbour);
				}
			}
		}
		return newList(list);
	}

	public static void main(String[] args) {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		node1.add(node2);
		node1.add(node3);
		System.out.println(bfs(node1));
	}
}
