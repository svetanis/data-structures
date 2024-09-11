package com.svetanis.datastructures.graph;

import static com.svetanis.java.base.collect.Lists.newList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public final class Graph {

	private List<Node> nodes;

	public Graph() {
		this.nodes = new ArrayList<>();
	}

	public Graph(Iterable<Node> nodes) {
		this();
		this.nodes.addAll(newList(nodes));
	}

	public void addNode(Node node) {
		this.nodes.add(node);
	}

	public ImmutableList<Node> getNodes() {
		return newList(nodes);
	}
}
