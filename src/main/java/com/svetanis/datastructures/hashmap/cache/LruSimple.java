package com.svetanis.datastructures.hashmap.cache;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// 146. LRU Cache
//
// A cache of at most `maxSize` entries. get(key) returns the key's value, or -1 if the key
// is not there. put(key, value) changes the value of a key already there, or adds the
// entry; if the cache is full, it first throws out the least recently used one. get and
// put both count as a use. LeetCode asks for O(1) per call, which this version does not
// meet.
//
// The map holds the values, and a deque holds the keys in use order, most recent at the
// front, least recent at the back. A use MOVEs its key to the front: removeFirstOccurrence
// finds it by walking the deque from the front, comparing key after key, then addFirst
// puts it back at the front. The key used longest ago sits at the back, so finding it
// walks past every key in the cache: O(capacity) per get or put, not O(1). EVICT is cheap,
// the least recent key is the deque's last.
//
// Siblings, same problem:
//   LruCache       map + doubly linked list, real head and tail starting null, a null
//                  check before every arrow change: O(1)
//   LruCacheDummy  map + doubly linked list with two dummy ends: O(1)

public final class LruSimple {
	// Time complexity: get and put O(capacity), removeFirstOccurrence walks the deque until
	// it meets the key, all of it for the least recent key
	// Auxiliary Space: O(capacity), one map entry and one deque entry per key

	private int maxSize;
	private Deque<Integer> queue; // KEYS: most recent first
	private Map<Integer, Integer> map; // key -> value

	public LruSimple(int maxSize) {
		this.maxSize = maxSize;
		this.map = new HashMap<>();
		this.queue = new LinkedList<>();
	}

	private boolean isFull() {
		return queue.size() >= maxSize;
	}

	public int get(int key) {
		if (map.get(key) == null) {
			return -1;
		}
		queue.removeFirstOccurrence(key); // MOVE: a walk from the front to the key ...
		queue.addFirst(key); // ... then back in at the front
		return map.get(key);
	}

	public void put(int key, int value) {
		if (map.containsKey(key)) {
			queue.removeFirstOccurrence(key); // MOVE: out of its place, a walk to the key
		}

		while (isFull()) {
			int last = queue.removeLast(); // EVICT the least recent; runs at most once
			map.remove(last);
		}

		queue.addFirst(key); // ADD at the front
		map.put(key, value); // UPDATE or ADD the value
	}
}
