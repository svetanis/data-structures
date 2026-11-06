package com.svetanis.datastructures.array;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.svetanis.datastructures.array.NestedIterator.NestedInteger;

// 341. Flatten Nested List Iterator
//
// Input: a list of NestedInteger. Each NestedInteger is either one integer or a list of
// NestedIntegers, nested to any depth. On LeetCode: 1 <= nestedList.length <= 500, and every
// integer is in [-10^6, 10^6]; the depth of nesting has no stated limit. The class is an
// Iterator<Integer> that hands the integers out in the order they appear when the nesting is read
// left to right.
//
// The one idea: the same unwrapping as NestedIterator, done once in the constructor, with the
// recursion's call stack replaced by a stack of our own. One stack entry is the iterator of one
// list that is partly read: the top entry is the list being read now, and each entry below it is
// the list that holds it, paused just after the entry we went down into. Read the next entry of
// the top list: a plain integer goes on the flat list; a list is pushed, so all of it is read
// before the rest of its parent. When the top list runs out it is popped, and its parent carries
// on where it paused. That is the order the recursion reads in, so the flat list is the same.
//
// Time: O(n) to build, O(1) per next and hasNext -- n is the number of NestedIntegers in all.
// Space: O(n) -- the flat copy of every integer, plus one stack entry per level of nesting, kept
// on the heap, so any depth fits.
//
// Siblings: NestedIterator -- the same walk written recursively; it overflows the call stack at
// about 20,000 levels of nesting.

public class NestedIteratorIterative implements Iterator<Integer> {

	private List<Integer> flattened;
	private Iterator<Integer> iterator;

	public NestedIteratorIterative(List<NestedInteger> nested) {
		this.flattened = new ArrayList<>();
		flatten(nested);
		this.iterator = flattened.iterator();
	}

	private void flatten(List<NestedInteger> list) {
		Deque<Iterator<NestedInteger>> stack = new ArrayDeque<>(); // one partly read list per level
		stack.push(list.iterator());
		while (!stack.isEmpty()) {
			Iterator<NestedInteger> top = stack.peek(); // the list being read now
			if (!top.hasNext()) {
				stack.pop(); // DONE: this list is read; the list that holds it carries on
			} else {
				NestedInteger nested = top.next();
				if (nested.isInteger()) {
					flattened.add(nested.getInteger()); // TAKE: a plain integer goes on the flat list
				} else {
					stack.push(nested.getList().iterator()); // DESCEND: read this list before the rest
				}
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
		nested.add(new Many(new One(1), new One(1)));
		nested.add(new One(2));
		nested.add(new Many(new One(1), new One(1)));
		System.out.println(read(new NestedIteratorIterative(nested))); // 1 1 2 1 1

		// [1, [4, [6]]] -> 1 4 6
		List<NestedInteger> nested2 = new ArrayList<>();
		nested2.add(new One(1));
		nested2.add(new Many(new One(4), new Many(new One(6))));
		System.out.println(read(new NestedIteratorIterative(nested2))); // 1 4 6

		// 7 inside 100,000 levels of lists: too deep for NestedIterator
		NestedInteger deep = new One(7);
		for (int level = 0; level < 100_000; level++) {
			deep = new Many(deep);
		}
		List<NestedInteger> nested3 = new ArrayList<>();
		nested3.add(deep);
		System.out.println(read(new NestedIteratorIterative(nested3))); // 7
	}

	private static String read(NestedIteratorIterative it) {
		StringBuilder out = new StringBuilder();
		while (it.hasNext()) {
			out.append(it.next()).append(' ');
		}
		return out.toString().trim();
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

	// a list of NestedIntegers, as LeetCode's NestedInteger holds it
	private static final class Many implements NestedInteger {
		private final List<NestedInteger> list = new ArrayList<>();

		Many(NestedInteger... items) {
			for (NestedInteger item : items) {
				list.add(item);
			}
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
