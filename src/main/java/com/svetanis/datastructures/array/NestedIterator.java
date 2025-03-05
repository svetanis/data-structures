package com.svetanis.datastructures.array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// 341. Flatten Nested List Iterator

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
				flattened.add(nested.getInteger());
			} else {
				flatten(nested.getList());
			}
		}
	}

	@Override
	public Integer next() {
		return iterator.next();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	public static void main(String[] args) {}

	public interface NestedInteger {

		public boolean isInteger();

		public Integer getInteger();

		public List<NestedInteger> getList();
	}

}
