package com.svetanis.datastructures.graph.bfs;

import static com.google.common.base.Verify.verify;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.Graph;
import com.svetanis.datastructures.graph.Node;

public final class BfsTraversalWithConnectedComponents {

	public static ImmutableList<ImmutableList<Integer>> bfs(Graph g) {
		int components = 0;
		Set<Node> set = newHashSet();
		List<ImmutableList<Integer>> lists = newArrayList();
		for (Node node : g.getNodes()) {
			if (!set.contains(node)) {
				components++;
				lists.add(bfs(node, set));
			}
		}
		verify(components == lists.size());
		return newList(lists);
	}

	private static ImmutableList<Integer> bfs(Node src, Set<Node> set) {
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
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);

		// add edges
		node1.add(node2);
		node1.add(node3);
		node2.add(node1);
		node2.add(node4);
		node3.add(node1);
		node3.add(node4);
		node4.add(node2);
		node4.add(node3);
		node5.add(node6);
		node6.add(node5);

		Graph g = new Graph();
		g.addNode(node1);
		g.addNode(node2);
		g.addNode(node3);
		g.addNode(node4);
		g.addNode(node5);
		g.addNode(node6);

		System.out.println(bfs(g));
	}
}
