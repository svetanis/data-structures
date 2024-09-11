package com.svetanis.datastructures.graph.clone;

import static com.svetanis.datastructures.graph.bfs.BfsTraversal.bfs;

import com.svetanis.datastructures.graph.Node;

// create a deep copy of a 
// connected undirected graph

public final class CloneGraphRecursive {

	public static Node clone(Node src) {
		Node copy = new Node(src.getData());
		clone(src, copy);
		return copy;
	}

	private static void clone(Node src, Node dst) {
		for (Node neighbor : src.getNeighbors()) {
			Node clone = new Node(neighbor.getData());
			dst.add(clone);
			clone(neighbor, clone);
		}
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
