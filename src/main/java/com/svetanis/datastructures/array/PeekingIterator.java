package com.svetanis.datastructures.array;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// 284. Peeking Iterator

public final class PeekingIterator implements Iterator<Integer> {

	private Iterator<Integer> iterator;
	private boolean hasPeeked;
	private Integer peeked;

	public PeekingIterator(Iterator<Integer> iterator) {
		this.iterator = iterator;
	}

	public Integer peek() {
		if (!hasPeeked) {
			if (iterator.hasNext()) {
				peeked = iterator.next();
			} else {
				throw new NoSuchElementException();
			}
			hasPeeked = true;
		}
		return peeked;
	}

	@Override
	public Integer next() {
		if (!hasPeeked) {
			return iterator.next();
		}
		Integer result = peeked;
		hasPeeked = false;
		peeked = null;
		return result;
	}

	@Override
	public boolean hasNext() {
		return hasPeeked || iterator.hasNext();
	}

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3);
		PeekingIterator pi = new PeekingIterator(list.iterator());
		System.out.println(pi.next()); // 1
		System.out.println(pi.peek()); // 2
		System.out.println(pi.next()); // 2
		System.out.println(pi.next()); // 3
		System.out.println(pi.hasNext()); // false
	}
}
