package com.svetanis.datastructures.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// A graph as a list of Node objects; each Node carries its own neighbours.
//
// Input: nodes added one at a time with addNode, or all at once through the constructor.
// Returns: getNodes gives a read-only copy of the list, in the order the nodes were added.
//
// The one idea: this class holds only the roster of nodes. The edges live inside the nodes
// (Node.add), so the roster is what lets a traversal reach every piece of a graph that is not
// connected: start from each listed node that has not been visited yet.
//
// Time: O(1) per addNode; O(n) for getNodes and for the Iterable constructor, which copy n nodes.
// Space: O(n) -- one reference per node.
//
// Siblings: none. Node is the vertex type this roster holds.

public final class Graph {

	private List<Node> nodes;

	public Graph() {
		this.nodes = new ArrayList<>();
	}

	public Graph(Iterable<Node> nodes) {
		this();
		for (Node node : nodes) {
			this.nodes.add(node); // TAKE: copy each given node into the roster
		}
	}

	public void addNode(Node node) {
		this.nodes.add(node); // TAKE: append to the roster
	}

	public List<Node> getNodes() {
		return Collections.unmodifiableList(new ArrayList<>(nodes)); // RETURN: a read-only copy
	}
}
