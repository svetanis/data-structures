package com.svetanis.datastructures.graph;

import static com.google.common.base.Objects.equal;
import static com.svetanis.java.base.Objects.notEqual;
import static java.util.Objects.hash;

// A position in a grid, and nothing else.

// It used to carry a `dist` field as well, which put a BFS's depth inside
// the identity of a square: Cell(3, 7, 0) and Cell(3, 7, 4) were unequal,
// so a visited set held one entry per (square, depth) pair rather than one
// per square. On an 8x8 knight board that is 354 entries for 64 squares and
// five times the work, and where the destination is unreachable the depth
// keeps climbing and the search never ends.

// Distance belongs to the traversal, not to the square. Every caller that
// needs it either walks the queue one LEVEL at a time -- so the depth is a
// loop variable -- or already records it in a grid of its own.

public final class Cell {
	protected int x;
	protected int y;
	private final int hash;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.hash = hash(x, y);
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
			return equal(x, other.x) && equal(y, other.y);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
