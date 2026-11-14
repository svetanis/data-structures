package com.svetanis.datastructures.graph.unionfind;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Reverse Disjoint Union Set (not a LeetCode problem)
//
// Input:
// A number 'n', representing the total number of cities, labelled 1..n.
// A list detailing the roads to be dismantled, in the order they are dismantled.
// Each road is shown as a pair of integers.
// For instance, [1,2] means there's a road between city 1 and city 2.

// Output:
// A list of integers showing the number of city
// clusters after each road is dismantled.
//
// The one idea: a union-find can join but cannot split, so run time BACKWARDS. After the last road
// is gone there are n clusters, one per city. Putting the roads back from the last to the first,
// each road that joins two different clusters lowers the count by one. The count recorded just
// before putting road i back is the count with only the roads after i standing -- that is the answer
// for "after road i is dismantled". Reverse the recorded counts to get them in the original order.
// One element is one city.
//
// The list `breaks` is reversed in place, so the caller's list comes back in the opposite order.
//
// Time: O(m * log n) -- m roads, each with two finds; UnionFind compresses paths but does not pick
// the smaller tree, so a find is O(log n) amortised.
// Space: O(n + m) -- the map inside UnionFind and the list of counts.

public final class ReverseDisjointUnionSet {

	public static List<Integer> umbristan(int n, List<List<Integer>> breaks) {
		int cc = n; // clusters now: n cities, no roads yet
		UnionFind<Integer> dsu = new UnionFind<>();
		List<Integer> list = new ArrayList<>();
		Collections.reverse(breaks); // WALK the roads from last dismantled to first
		for (List<Integer> edge : breaks) {
			list.add(cc); // RECORD: clusters with only the later roads standing
			int x = edge.get(0);
			int y = edge.get(1);
			if (!dsu.isSame(x, y)) {
				dsu.union(x, y); // JOIN: put the road back
				cc--; // COUNT: two clusters became one
			}
		}
		Collections.reverse(list); // back to the order the roads were dismantled in
		return list;
	}

	public static void main(String[] args) {
		List<List<Integer>> list = new ArrayList<>();
		list.add(asList(1, 2));
		list.add(asList(2, 3));
		list.add(asList(3, 4));
		list.add(asList(1, 4));
		list.add(asList(2, 4));
		System.out.println(umbristan(4, list)); // [1, 1, 2, 3, 4]
	}
}
