package com.svetanis.datastructures.graph.dfs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.Node;

public final class DfsTraversal {

	public static ImmutableList<Integer> dfs(Node src) {
		Set<Node> set = newHashSet();
		List<Integer> list = newArrayList();
		set.add(src);
		list.add(src.getData());
		for (Node node : src.getNeighbors()) {
			explore(node, set, list);
		}
		return newList(list);
	}

	private static void explore(Node node, Set<Node> set, List<Integer> list) {
		set.add(node);
		list.add(node.getData());
		for (Node neighbor : node.getNeighbors()) {
			if (!set.contains(neighbor)) {
				explore(neighbor, set, list);
			}
		}
	}

	public static void main(String[] args) {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		node1.add(node2);
		node1.add(node3);
		System.out.println(dfs(node1));
	}
}
