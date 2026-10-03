package com.svetanis.datastructures.stack;

import java.util.ArrayList;
import java.util.List;

// The NestedInteger interface that LeetCode supplies for 385. Mini Parser, implemented here so
// that MiniParser can build and print its result.
//
// One NestedInteger holds either a single integer or a list of NestedIntegers.
//
// 'value' is set exactly when the object holds a single integer; otherwise it is null and
// 'list' is the nested list, empty for []. setInteger clears the list and add clears the
// value, so the two states never mix.

public class NestedInteger {
	// Time Complexity: isInteger, getInteger, getList O(1); add O(1) amortized;
	//   setInteger O(size of the list it clears); toString O(length of the text it prints)
	// Space Complexity: O(1) per object, plus its list

	private Integer value;
	private List<NestedInteger> list;

	public NestedInteger() {
		this.list = new ArrayList<>();
	}

	// Constructor initializes a single integer.
	public NestedInteger(int value) {
		this.value = value;
		this.list = new ArrayList<>();
	}

	// @return true if this NestedInteger holds a single integer, rather than a nested list.
	public boolean isInteger() {
		return value != null;
	}

	// @return the single integer that this NestedInteger holds, if it holds a single integer
	// Return null if this NestedInteger holds a nested list
	public Integer getInteger() {
		return value;
	}

	// Set this NestedInteger to hold a single integer.
	public void setInteger(int value) {
		this.value = value;
		this.list.clear();
	}

	// Set this NestedInteger to hold a nested list and adds a nested integer to it.
	public void add(NestedInteger ni) {
		this.value = null;
		this.list.add(ni);
	}

	// @return the nested list that this NestedInteger holds, if it holds a nested list
	// Return empty list if this NestedInteger holds a single integer
	public List<NestedInteger> getList() {
		return list;
	}

	// the same text LC 385 parses: 324, or [123,[456,[789]]]
	@Override
	public String toString() {
		return isInteger() ? value.toString() : list.toString().replace(" ", "");
	}
}
