package com.svetanis.datastructures.array;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// 284. Peeking Iterator
//
// Input: an Iterator<Integer>. The class wraps it and adds peek(), which returns the next element
// without moving past it. On LeetCode: 1 <= nums.length <= 1000, 1 <= nums[i] <= 1000, at most
// 1000 calls to next, peek and hasNext, and next and peek are only called when an element is left.
//
// The one idea: an Iterator can only move forward, so peeking means really taking the element and
// keeping it. The wrapper holds one spare slot: peeked is that element and hasPeeked says the
// slot is full. peek fills the slot (if it is empty) and returns it; next hands out the slot and
// empties it, or, when the slot is empty, just asks the wrapped iterator; hasNext is true when
// the slot is full or the wrapped iterator has more.
//
// Time: O(1) -- every call does a constant amount of work.
// Space: O(1) -- one spare element and one flag.

public final class PeekingIterator implements Iterator<Integer> {

	private Iterator<Integer> iterator;
	private boolean hasPeeked; // SLOT: true when peeked holds an element taken from the iterator
	private Integer peeked;

	public PeekingIterator(Iterator<Integer> iterator) {
		this.iterator = iterator;
	}

	public Integer peek() {
		if (!hasPeeked) {
			if (iterator.hasNext()) {
				peeked = iterator.next(); // FILL: take the element and keep it in the slot
			} else {
				throw new NoSuchElementException();
			}
			hasPeeked = true;
		}
		return peeked; // RETURN: the slot, not consumed
	}

	@Override
	public Integer next() {
		if (!hasPeeked) {
			return iterator.next(); // NO SLOT: straight from the wrapped iterator
		}
		Integer result = peeked; // EMPTY THE SLOT: hand out what peek took
		hasPeeked = false;
		peeked = null;
		return result;
	}

	@Override
	public boolean hasNext() {
		return hasPeeked || iterator.hasNext(); // MORE: the slot is full or the iterator has more
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
