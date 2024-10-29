package com.svetanis.datastructures.graph.unionfind;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

// compute the number of connected
// components in a graph.

// a connected component is a series
// of node such that there exists a
// path between any two nodes in the graph. 

public final class NumberOfConnectedComponents {
	// Time Complexity: O(n * log n)
	// Space Complexity: O(n)

	public static List<Integer> connectedComponents(int n, List<List<Integer>> connections) {
		int cc = n;
		UnionFind<Integer> dsu = new UnionFind<>();
		List<Integer> list = new ArrayList<>();
		for (List<Integer> connection : connections) {
			int x = connection.get(0);
			int y = connection.get(1);
			if (dsu.find(x) != dsu.find(y)) {
				dsu.union(x, y);
				cc--;
			}
			list.add(cc);
		}
		return list;
	}

	public static void main(String[] args) {
		List<List<Integer>> list = new ArrayList<>();
		list.add(asList(1, 2));
		list.add(asList(2, 3));
		list.add(asList(1, 3));
		list.add(asList(0, 4));
		list.add(asList(0, 4));
		System.out.println(connectedComponents(5, list));
	}
}
