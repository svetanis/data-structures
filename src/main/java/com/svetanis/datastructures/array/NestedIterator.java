package com.svetanis.datastructures.array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// 341. Flatten Nested List Iterator
//
// Input: a list of NestedInteger. Each NestedInteger is either one integer or a list of
// NestedIntegers, nested to any depth. On LeetCode: 1 <= nestedList.length <= 500, and every
// integer is in [-10^6, 10^6]. The class is an Iterator<Integer> that hands the integers out in
// the order they appear when the nesting is read left to right.
//
// The one idea: do all the unwrapping once, in the constructor. flatten walks the list; an entry
// that is a plain integer is appended to a flat list, an entry that is itself a list is flattened
// in the same way, one level deeper. When flatten returns, the flat list holds every integer in
// reading order, and next and hasNext are just the iterator of that flat list.
//
// Known limit: flatten calls itself once per level of nesting, so a list nested tens of thousands
// of levels deep (about 20,000) overflows the default stack; NestedIteratorIterative does not.
//
// Time: O(n) to build, O(1) per next and hasNext -- n is the number of NestedIntegers in all.
// Space: O(n) -- the flat copy of every integer, plus one stack frame per level of nesting.
//
// Siblings: NestedIteratorIterative -- the same walk with a stack of list iterators in place of
// the recursion, so any depth fits. A lazy version would keep that stack between calls and
// flatten only as far as the next integer: O(depth) space, but each hasNext may do work.

public class NestedIterator implements Iterator<Integer> {

	private List<Integer> flattened;
	private Iterator<Integer> iterator;

	public NestedIterator(List<NestedInteger> nested) {
		this.flattened = new ArrayList<>();
		flatten(nested);
		this.iterator = flattened.iterator();
	}

	private void flatten(List<NestedInteger> list) {
		for (NestedInteger nested : list) {
			if (nested.isInteger()) {
				flattened.add(nested.getInteger()); // TAKE: a plain integer goes on the flat list
			} else {
				flatten(nested.getList()); // DESCEND: same walk, one level deeper
			}
		}
	}

	@Override
	public Integer next() {
		return iterator.next(); // RETURN: the next integer in reading order
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	public static void main(String[] args) {
		// [[1, 1], 2, [1, 1]] -> 1 1 2 1 1
		List<NestedInteger> nested = new ArrayList<>();
		nested.add(new Pair(1, 1));
		nested.add(new One(2));
		nested.add(new Pair(1, 1));
		NestedIterator it = new NestedIterator(nested);
		StringBuilder out = new StringBuilder();
		while (it.hasNext()) {
			out.append(it.next()).append(' ');
		}
		System.out.println(out.toString().trim()); // 1 1 2 1 1
	}

	public interface NestedInteger {

		public boolean isInteger();

		public Integer getInteger();

		public List<NestedInteger> getList();
	}

	// one integer, as LeetCode's NestedInteger holds it
	private static final class One implements NestedInteger {
		private final int value;

		One(int value) {
			this.value = value;
		}

		public boolean isInteger() {
			return true;
		}

		public Integer getInteger() {
			return value;
		}

		public List<NestedInteger> getList() {
			return null;
		}
	}

	// a list of two integers, as LeetCode's NestedInteger holds it
	private static final class Pair implements NestedInteger {
		private final List<NestedInteger> list = new ArrayList<>();

		Pair(int first, int second) {
			list.add(new One(first));
			list.add(new One(second));
		}

		public boolean isInteger() {
			return false;
		}

		public Integer getInteger() {
			return null;
		}

		public List<NestedInteger> getList() {
			return list;
		}
	}

}
