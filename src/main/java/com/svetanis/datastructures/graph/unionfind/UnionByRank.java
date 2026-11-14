package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

// Union-find over any key type, kept in maps, with UNION BY RANK.
//
// The one idea: rank[root] is an upper bound on the height of the tree under that root -- not a
// count of keys. When two roots are joined, the root with the smaller rank goes under the other, so
// a tree only grows taller when two trees of EQUAL rank meet, and then the winner's rank goes up by
// one. A tree of rank r holds at least 2^r keys, so no tree is taller than log2(n).
//
// Time: O(alpha(n)) amortised per call -- union by rank together with path compression in find.
// Space: O(n) -- the two maps.
//
// Siblings: UnionFind is the same structure without rank.
// find is recursive, but with union by rank the depth stays at most log2(n).

public final class UnionByRank<T> {

	private Map<T, T> map;
	private Map<T, Integer> rank;

	public UnionByRank() {
		this.map = new HashMap<>();
		this.rank = new HashMap<>();
	}

	public void union(T x, T y) {
		T rootX = find(x);
		T rootY = find(y);
		if (rootX.equals(rootY)) {
			return; // SKIP: already one group
		}
		int rankX = rank.getOrDefault(rootX, 0);
		int rankY = rank.getOrDefault(rootY, 0);
		if (rankX < rankY) {
			map.put(rootX, rootY); // JOIN: the lower tree goes under the higher
		} else {
			map.put(rootY, rootX);
			// int, not Integer: rank.get(a) == rank.get(b) compares two boxes
			if (rankX == rankY) {
				rank.put(rootX, rankX + 1); // RAISE: only when equal trees meet
			}
		}
	}

	public boolean isSame(T x, T y) {
		return find(x).equals(find(y)); // RETURN: same root, same group
	}

	public T find(T x) {
		T y = map.getOrDefault(x, x);
		// equals, not != -- see the note on UnionFind
		if (!y.equals(x)) {
			y = find(y); // WALK up to the root
			map.put(x, y); // COMPRESS: point straight at it
		}
		return y;
	}
}
