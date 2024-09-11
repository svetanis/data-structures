package com.svetanis.datastructures.graph;

import static com.google.common.base.Objects.equal;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.Objects.notEqual;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.util.Objects.hash;

import java.util.List;

import com.google.common.collect.ImmutableList;

public final class Node {

	private int data;
	private List<Node> neighbors;
	private int hash;

	public Node() {
		this(-1);
	}

	public Node(int data) {
		this.data = data;
		this.neighbors = newArrayList();
		this.hash = hash(data, neighbors);
	}

	public void add(Node node) {
		this.neighbors.add(node);
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (notEqual(this, object)) {
			return false;
		} else {
			Node other = (Node) object;
			boolean one = hash == other.hash;
			boolean two = equal(data, other.data);
			boolean three = equal(neighbors, other.neighbors);
			return one && two && three;
		}

	}

	public int getData() {
		return data;
	}

	public ImmutableList<Node> getNeighbors() {
		return newList(neighbors);
	}

}