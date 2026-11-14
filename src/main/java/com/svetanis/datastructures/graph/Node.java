package com.svetanis.datastructures.graph;

import static java.util.Objects.hash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// A graph vertex that holds an integer and the list of nodes it has an edge to.
//
// Input: an integer for the constructor (the no-argument one uses -1); neighbours are added one
// at a time with add. An edge from a to b is a.add(b); for a two-way edge the caller also calls
// b.add(a).
// Returns: getData gives the integer; getNeighbors gives a read-only copy of the neighbour list,
// in the order the neighbours were added.
//
// The one idea: the edges are stored inside the vertex, as the list of nodes it points at, so a
// traversal needs nothing but the node it stands on. The hash is computed in the constructor,
// when the neighbour list is still empty, so it depends on the integer alone; this is what lets a
// node stay a valid HashSet or HashMap key while neighbours are still being added to it.
//
// Known limit: equals compares the integer first and, when it matches, the neighbour lists --
// and those compare Node objects, which compare their neighbour lists in turn. Two DIFFERENT
// Node objects with the same integer whose neighbours form a cycle never finish comparing
// (StackOverflowError). A node compared with itself returns at once, and nodes with different
// integers are told apart before any neighbour list is looked at. So a graph that is walked with a
// HashSet or HashMap of its own nodes is safe as long as no two of its nodes share an integer.
//
// Time: O(1) for add, hashCode and getData; O(k) for getNeighbors (copies k neighbours).
// Space: O(k) -- one reference per neighbour.
//
// Siblings: Graph holds a list of these nodes; Cell is the grid-position key.

public final class Node {

	private int data;
	private List<Node> neighbors;
	private int hash; // the hash of (data, empty list), computed once

	public Node() {
		this(-1);
	}

	public Node(int data) {
		this.data = data;
		this.neighbors = new ArrayList<>();
		this.hash = hash(data, neighbors); // HASH: the list is empty now, so this depends on data alone
	}

	public void add(Node node) {
		this.neighbors.add(node); // EDGE: this node now points at the given one
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true; // SAME: the very same object
		} else if (object == null || getClass() != object.getClass()) {
			return false; // SKIP: not a Node
		} else {
			Node other = (Node) object;
			// && stops at the first mismatch, so the neighbour lists are compared only for nodes
			// that already agree on the integer
			return hash == other.hash && data == other.data && neighbors.equals(other.neighbors);
		}

	}

	public int getData() {
		return data;
	}

	public List<Node> getNeighbors() {
		return Collections.unmodifiableList(new ArrayList<>(neighbors)); // RETURN: a read-only copy
	}

}