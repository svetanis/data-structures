package com.svetanis.datastructures.hashmap.cache;

import java.util.HashMap;
import java.util.Map;

// 146. LRU Cache

// A cache of at most `capacity` entries. get(key) returns the key's value,
// or -1 if the key is not there. put(key, value) changes the value of a key
// already there, or adds the entry; if that makes one entry too many, it
// throws out the least recently used one. get and put both count as a use.
// Both must run in O(1).

// The idea: a map finds a key's own node in one step, and a doubly linked
// list keeps the use order, most recent at the front. Three changes are
// ever made to that order: MOVE a used key from wherever it is to the
// front, ADD a new key at the front, EVICT the key at the back. Each node
// holds its key (EVICT removes the map entry by key) and both neighbours
// (a node in the middle can unlink itself). Two dummy nodes, head and tail,
// sit at the ends, so every real node always has a neighbour on both sides
// and no arrow change needs a null check.

// Time Complexity: O(1) for get and put - each changes at most four arrows
// and makes one or two map calls, whatever the size.
// Space Complexity: O(capacity) - one node and one map entry per key.

// Three implementations of the same problem, in this package:
// | LruCacheDummy | map + doubly linked list, two dummy ends | O(1) |
// | LruCache      | map + doubly linked list, real head and tail starting
// |               | null, a null check before every arrow change | O(1) |
// | LruSimple     | map of values + a deque of keys; MOVE uses
// |               | removeFirstOccurrence, which scans the deque | O(capacity) per call |

public final class LruCacheDummy {

	private final int capacity;
	private final Map<Integer, Node> map; // key -> its own node
	private final Node head; // dummy: head.next is the most recent
	private final Node tail; // dummy: tail.prev is the least recent

	public LruCacheDummy(int capacity) {
		this.capacity = capacity;
		this.map = new HashMap<>();
		this.head = new Node(0, 0);
		this.tail = new Node(0, 0);
		head.next = tail; // LINK the empty list: head <-> tail
		tail.prev = head;
	}

	public int get(int key) {
		Node node = map.get(key); // FIND
		if (node == null) {
			return -1;
		}
		unlink(node); // MOVE out of its place first ...
		addFront(node); // ... then to the front
		return node.value;
	}

	public void put(int key, int value) {
		Node node = map.get(key); // FIND
		if (node != null) {
			node.value = value; // UPDATE the same node, so the map needs no change
			unlink(node); // MOVE
			addFront(node);
			return;
		}
		node = new Node(key, value); // ADD
		addFront(node);
		map.put(key, node);
		if (map.size() > capacity) { // EVICT the least recent
			Node last = tail.prev;
			unlink(last);
			map.remove(last.key); // by its key, not its value
		}
	}

	// the two neighbours skip node
	private void unlink(Node node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}

	// node goes between head and the current front
	private void addFront(Node node) {
		Node first = head.next; // SAVE before head.next changes
		head.next = node;
		node.prev = head;
		node.next = first;
		first.prev = node;
	}

	public static void main(String[] args) {
		LruCacheDummy cache = new LruCacheDummy(2);
		cache.put(1, 1);
		cache.put(2, 2);
		System.out.println(cache.get(1)); // 1
		cache.put(3, 3); // evicts 2
		System.out.println(cache.get(2)); // -1
		cache.put(4, 4); // evicts 1
		System.out.println(cache.get(1)); // -1
		System.out.println(cache.get(3)); // 3
		System.out.println(cache.get(4)); // 4

		LruCacheDummy update = new LruCacheDummy(2);
		update.put(1, 1);
		update.put(2, 2);
		update.put(1, 10); // MOVE on put: 1 becomes the most recent
		update.put(3, 3); // evicts 2, not 1
		System.out.println(update.get(1) + " " + update.get(2)); // 10 -1
	}
}
