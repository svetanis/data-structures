package com.svetanis.datastructures.tree.implicit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// A nested list element: either a single integer or a list of further elements. Not a solution
// to anything by itself; it stands in for LeetCode's NestedInteger, which the judge supplies for
// 339. Nested List Weight Sum.
//
// Input: new NestedInteger(value) is an integer; new NestedInteger() is an empty list, filled
// with add(element). isInteger() says which kind it is, getInteger() holds the integer (empty
// for a list) and getList() the elements (empty for an integer). LeetCode's own getInteger()
// returns the int itself; this one returns an Optional.
//
// The one idea: a nested list is a tree. An integer is a leaf, a list is a node whose children
// are its elements, and the "depth" of 339 is the number of lists above an integer, counted
// from 1 for the outermost.
//
// Time: every method is O(1).
// Space: O(1) per element, plus its children.

public final class NestedInteger {

	public NestedInteger() {
		this(Optional.empty()); // a list: no integer
	}

	public NestedInteger(int value) {
		this(Optional.of(value)); // an integer: no elements
	}

	private NestedInteger(Optional<Integer> value) {
		this.value = value;
		this.nested = new ArrayList<>();
	}

	private Optional<Integer> value;
	private List<NestedInteger> nested;

	public boolean isInteger() {
		return value.isPresent();
	}

	public void add(NestedInteger nested) {
		this.nested.add(nested);
	}

	public Optional<Integer> getInteger() {
		return value;
	}

	public List<NestedInteger> getList() {
		return nested;
	}
}
