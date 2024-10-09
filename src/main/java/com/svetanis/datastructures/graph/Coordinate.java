package com.svetanis.datastructures.graph;

import static com.google.common.base.Objects.equal;
import static com.svetanis.java.base.Objects.notEqual;
import static java.util.Objects.hash;

public final class Coordinate {

	private final int row;
	private final int col;
	private int hash;

	public Coordinate() {
		this(-1, -1);
	}

	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
		this.hash = hash(row, col);
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
			Coordinate other = (Coordinate) object;
			boolean one = hash == other.hash;
			boolean two = equal(row, other.row);
			boolean three = equal(col, other.col);
			return one && two && three;
		}
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}