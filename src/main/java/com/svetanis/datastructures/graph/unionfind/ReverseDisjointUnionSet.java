package com.svetanis.datastructures.graph.unionfind;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Input:
// A number 'n', representing the total number of cities. 
// A list detailing the roads to be dismantled. 
// Each road is shown as a pair of integers. 
// For instance, [1,2] means there's a road between city 1 and city 2. 

// Output:
// A list of integers showing the number of city 
// clusters after each road is dismantled.

public final class ReverseDisjointUnionSet {
	// Time Complexity: O(n * log n)
	// Space Complexity: O(n)

	public static List<Integer> umbristan(int n, List<List<Integer>> breaks) {
		int cc = n;
		UnionFind<Integer> dsu = new UnionFind<>();
		List<Integer> list = new ArrayList<>();
		Collections.reverse(breaks);
		for (List<Integer> edge : breaks) {
			list.add(cc);
			int x = edge.get(0);
			int y = edge.get(1);
			if (dsu.find(x) != dsu.find(y)) {
				dsu.union(x, y);
				cc--;
			}
		}
		Collections.reverse(list);
		return list;
	}

	public static void main(String[] args) {
		List<List<Integer>> list = new ArrayList<>();
		list.add(asList(1, 2));
		list.add(asList(2, 3));
		list.add(asList(3, 4));
		list.add(asList(1, 4));
		list.add(asList(2, 4));
		System.out.println(umbristan(4, list));
	}
}
