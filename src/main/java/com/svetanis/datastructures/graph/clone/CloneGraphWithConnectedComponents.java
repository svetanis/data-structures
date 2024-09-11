package com.svetanis.datastructures.graph.clone;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.graph.bfs.BfsTraversalWithConnectedComponents.bfs;
import static com.svetanis.datastructures.graph.dfs.DfsTraversalWithConnectedComponents.dfs;
import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.collect.Maps.checkedPut;

import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.graph.Graph;
import com.svetanis.datastructures.graph.Node;

// given an undirected graph with
// multiple connected components
// clone the graph

public final class CloneGraphWithConnectedComponents {
	// Time Complexity: O(V + E)

	public static Graph clone(Graph g) {
		Graph cloned = new Graph();
		Map<Node, Node> map = newHashMap();
		for (Node node : g.getNodes()) {
			if (map.get(node) == null) {
				Node clone = clone(node, map);
				cloned.addNode(clone);
			}
		}
		return cloned;
	}

	private static Node clone(Node src, Map<Node, Node> map) {
		Queue<Node> queue = newLinkedList();
		queue.offer(src);
		checkedPut(map, src, new Node(src.getData()));
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			Node copy = map.get(node);
			if (copy == null) {
				copy = new Node(node.getData());
				checkedPut(map, node, copy);
			}
			for (Node neighbor : node.getNeighbors()) {
				Node clone = map.get(neighbor);
				if (clone == null) {
					queue.offer(neighbor);
					clone = new Node(neighbor.getData());
					checkedPut(map, neighbor, clone);
				}
				copy.add(clone);
			}
		}
		return checkedGet(map, src);
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

		Graph clone = clone(g);

		System.out.println(dfs(g));
		System.out.println(dfs(clone));
		System.out.println();
		System.out.println(bfs(g));
		System.out.println(bfs(clone));
	}
}
