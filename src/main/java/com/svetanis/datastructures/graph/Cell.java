package com.svetanis.datastructures.graph;

import static java.util.Objects.hash;

// A position in a grid, and nothing else.
//
// Input: a row-like number x and a column-like number y; either name can be used for either axis,
// each caller says which it means (getRow and getCol read the same two numbers as getX and getY).
// The no-argument constructor gives (-1, -1), a position outside every grid, for "not set".
// Nothing is checked against a grid size.
// Returns: a value that is equal to another Cell exactly when both numbers match, and that can be
// used as a key in a HashSet or HashMap.
//
// The one idea: a Cell is the address of one square, so its identity is that address and nothing
// more. Distance is not part of it. If a breadth-first search stored its depth inside the Cell,
// Cell(3, 7) reached at depth 0 and Cell(3, 7) reached at depth 4 would be two different keys, a
// visited set would hold one entry per (square, depth) pair instead of one per square, and a
// search for an unreachable target would never stop. Distance belongs to the traversal: a loop
// variable when the queue is walked one level at a time, or a grid of its own.
//
// Time: O(1) per call -- the hash is computed once, in the constructor.
// Space: O(1) -- two ints and the stored hash.
//
// Siblings: none.

public final class Cell {
	protected int x;
	protected int y;
	private final int hash; // the hash of (x, y), computed once

	public Cell() {
		this(-1, -1); // DEFAULT: a position outside every grid
	}

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.hash = hash(x, y); // HASH: fixed at construction
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true; // SAME: the very same object
		} else if (object == null || getClass() != object.getClass()) {
			return false; // SKIP: not a Cell
		} else {
			Cell other = (Cell) object;
			return x == other.x && y == other.y; // RETURN: equal when both numbers match
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRow() {
		return x;
	}

	public int getCol() {
		return y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
