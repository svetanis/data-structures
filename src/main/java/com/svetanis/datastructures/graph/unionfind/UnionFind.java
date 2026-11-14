package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

// Union-find over any key type, kept in a map: a key missing from the map is its own root, so
// nothing is set up in advance and keys can arrive in any order.
//
// The one idea: map[x] is the key that x points at; following the pointers from a key leads to
// the root of its group, and two keys are in one group exactly when their roots are equal. union
// points one root at the other; find compresses the path it walked, so the next find is shorter.
//
// Time: O(log n) amortised per call -- find compresses paths, but union does not pick the smaller
// tree (UnionByRank does).
// Space: O(n) -- one map entry per key that has been joined to something.
//
// Siblings: UnionByRank is the same structure with union by rank, which also keeps the trees short.
// find walks up with a loop, not a recursion, so a very long chain of keys cannot overflow the
// stack; a second loop then points every key on the route at the root.
//
// T is an OBJECT type, so every comparison of two T values must be
// equals, never == / !=. With T = Integer the reference compare agrees
// with equality only inside Java's -128..127 box cache, which is why a
// test using small labels passes and real data does not.
public final class UnionFind<T> {

	private Map<T, T> map;

	public UnionFind() {
		this.map = new HashMap<>();
	}

	public void union(T x, T y) {
		map.put(find(x), find(y)); // JOIN: x's root now points at y's root
	}

	// prefer this to comparing two find() results yourself -- doing that
	// at the call site is where the == crept in seven times
	public boolean isSame(T x, T y) {
		return find(x).equals(find(y)); // RETURN: same root, same group
	}

	public T find(T x) {
		T root = x;
		T up = map.getOrDefault(root, root); // a missing key is a root
		while (!up.equals(root)) { // WALK up to the root
			root = up;
			up = map.getOrDefault(root, root);
		}
		T node = x;
		while (!node.equals(root)) { // COMPRESS: every key on the route points straight at the root
			T next = map.get(node);
			map.put(node, root);
			node = next;
		}
		return root;
	}
}
