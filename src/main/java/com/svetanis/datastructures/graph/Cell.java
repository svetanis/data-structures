package com.svetanis.datastructures.graph;

import static com.google.common.base.Objects.equal;
import static com.svetanis.java.base.Objects.notEqual;

public final class Cell {
	protected int x;
	protected int y;
	protected int dist;
	private int hash;

	public Cell(int x, int y) {
		this(x, y, 0);
	}

	public Cell(int x, int y, int dist) {
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
			Cell other = (Cell) object;
			boolean one = hash == other.hash;
			boolean two = equal(x, other.x);
			boolean three = equal(y, other.y);
			boolean four = equal(dist, other.dist);
			return one && two && three && four;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDist() {
		return dist;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + dist + "]";
	}
}