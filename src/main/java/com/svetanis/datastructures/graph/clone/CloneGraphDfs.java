package com.svetanis.datastructures.graph.clone;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.graph.bfs.BfsTraversal.bfs;

import java.util.Map;

import com.svetanis.datastructures.graph.Node;

// 133. Clone Graph

// create a deep copy of a 
// connected undirected graph

public final class CloneGraphDfs {
  // Time Complexity: O(V + E)
	
	public static Node clone(Node src) {
		if (src == null) {
			return null;
		}
		return clone(src, newHashMap());
	}

	private static Node clone(Node src, Map<Integer, Node> map) {
		Node dst = new Node(src.getData());
		map.put(dst.getData(), dst);
		for (Node neighbor : src.getNeighbors()) {
			Node clone = map.get(neighbor.getData());
			if (clone == null) {
				clone = clone(neighbor, map);
			}
			dst.add(clone);
		}
		return dst;
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
