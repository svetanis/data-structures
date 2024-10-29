package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

public final class UnionByRank<T> {

	private Map<T, T> map;
	private Map<T, Integer> rank;

	public UnionByRank() {
		this.map = new HashMap<>();
		this.rank = new HashMap<>();
	}

	public void union(T x, T y) {
		if (!rank.containsKey(find(x))) {
			rank.put(find(x), 0);
		}
		if (!rank.containsKey(find(y))) {
			rank.put(find(y), 0);
		}
		if (rank.get(find(x)) < rank.get(find(y))) {
			map.put(find(x), find(y));
		} else {
			map.put(find(y), find(x));
			if (rank.get(find(x)) == rank.get(find(y))) {
				rank.put(find(x), rank.get(find(x)) + 1);
			}
		}
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
