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
		T rootX = find(x);
		T rootY = find(y);
		if (rootX.equals(rootY)) {
			return;
		}
		int rankX = rank.getOrDefault(rootX, 0);
		int rankY = rank.getOrDefault(rootY, 0);
		if (rankX < rankY) {
			map.put(rootX, rootY);
		} else {
			map.put(rootY, rootX);
			// int, not Integer: rank.get(a) == rank.get(b) compares two boxes
			if (rankX == rankY) {
				rank.put(rootX, rankX + 1);
			}
		}
	}

	public boolean isSame(T x, T y) {
		return find(x).equals(find(y));
	}

	public T find(T x) {
		T y = map.getOrDefault(x, x);
		// equals, not != -- see the note on UnionFind
		if (!y.equals(x)) {
			y = find(y);
			map.put(x, y);
		}
		return y;
	}
}
