package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

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
		map.put(find(x), find(y));
	}

	// prefer this to comparing two find() results yourself -- doing that
	// at the call site is where the == crept in seven times
	public boolean isSame(T x, T y) {
		return find(x).equals(find(y));
	}

	public T find(T x) {
		T y = map.getOrDefault(x, x);
		if (!y.equals(x)) {
			y = find(y);
			map.put(x, y);
		}
		return y;
	}
}
