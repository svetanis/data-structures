package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

public final class UnionFind<T> {

	private Map<T, T> map;

	public UnionFind() {
		this.map = new HashMap<>();
	}

	public void union(T x, T y) {
		map.put(find(x), find(y));
	}

	public T find(T x) {
		T y = map.getOrDefault(x, x);
		if (y != x) {
			y = find(y);
			map.put(x, y);
		}
		return y;
	}
}
