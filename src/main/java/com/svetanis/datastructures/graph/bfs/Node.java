package com.svetanis.datastructures.graph.bfs;

import static com.google.common.base.Objects.equal;
import static com.svetanis.java.base.Objects.notEqual;

public final class Node {
	protected int x;
	protected int y;
	protected int dist;
	private int hash;

	public Node(int x, int y) {
		this(x, y, 0);
	}

	public Node(int x, int y, int dist) {
		this.x = x;
		this.y = y;
		this.dist = dist;
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (notEqual(this, object)) {
			return false;
		} else {
			Node other = (Node) object;
			boolean one = hash == other.hash;
			boolean two = equal(x, other.x);
			boolean three = equal(y, other.y);
			boolean four = equal(dist, other.dist);
			return one && two && three && four;
		}
	}

}