package com.svetanis.datastructures.graph.clone;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.graph.bfs.BfsTraversal.bfs;
import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.collect.Maps.checkedPut;

import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.graph.Node;

// create a deep copy of a 
// connected undirected graph

public final class CloneGraphBfs {
	// Time Complexity: O(V + E)

	public static Node clone(Node src) {
		Map<Node, Node> map = newHashMap();
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
		node1.add(node2);
		node1.add(node3);
		System.out.println(bfs(node1));
		Node clone = clone(node1);
		System.out.println(bfs(clone));
	}
}
