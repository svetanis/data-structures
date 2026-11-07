package com.svetanis.datastructures.hashmap.cache;

import java.util.HashMap;
import java.util.Map;

// 146. LRU Cache
//
// A cache of at most `size` entries. get(key) returns the key's value, or -1 if the key is
// not there. put(key, value) changes the value of a key already there, or adds the entry;
// if that makes one entry too many, it throws out the least recently used one. get and
// put both count as a use. Both must run in O(1).
//
// A map finds a key's own node in one step, and a doubly linked list keeps the use order,
// most recent at the front (head), least recent at the back (tail). get and put on a key
// already there MOVE its node to the front; put of a new key ADDs a node at the front and,
// if the map then holds one entry too many, EVICTs the tail. Each node holds its key, so
// EVICT can remove the map entry, and both neighbours, so a node in the middle can unlink
// itself. head and tail are real nodes, null while the list is empty, so every arrow
// change first checks for null: the front node has no prev, the back node no next.
//
// Siblings, same problem:
//   LruCacheDummy  the same map and list with two dummy ends, so no null checks
//   LruSimple      a map of values and a deque of keys; MOVE uses removeFirstOccurrence,
//                  which scans the deque, so O(capacity) per call
// Related, a different problem: lfu/LfuCache (460) evicts the least used key first, and by
// recency only among keys used equally often.

public final class LruCache {
	// Time complexity: get and put O(1), one or two map calls and a fixed number of arrow
	// changes, whatever the size
	// Auxiliary Space: O(size), one node and one map entry per key

	private int size;
	private Node head; // the most recent; null while empty
	private Node tail; // the least recent; null while empty
	private Map<Integer, Node> map; // key -> its own node

	public LruCache(int size) {
		this.size = size;
		this.head = null;
		this.tail = null;
		this.map = new HashMap<>();
	}

	public int get(int key) {
		Node node = map.get(key); // FIND
		if (node == null) {
			return -1;
		}
		moveToFront(node); // MOVE
		return node.value;
	}

	public void put(int key, int value) {
		Node node = map.get(key); // FIND
		if (node != null) {
			node.value = value; // UPDATE the same node, so the map needs no change
			moveToFront(node); // MOVE
		} else {
			node = new Node(key, value); // ADD
			addFirst(node);
			map.put(key, node);
			removeLast(); // EVICT, if one entry too many
		}
	}

	private void moveToFront(Node node) {
		if (node == head || node == null) {
			return; // FRONT already: nothing to move
		}
		remove(node);
		addFirst(node);
	}

	public void addFirst(Node node) {
		if (node == null) {
			return;
		}
		node.next = head; // LINK in front of the old head, null if the list is empty
		node.prev = null;

		if (head != null) {
			head.prev = node; // only when there is an old head to point back
		}
		head = node;
		if (tail == null) {
			tail = node; // EMPTY list: the one node is also the back
		}
	}

	private void removeLast() {
		if (map.size() > size) {
			map.remove(tail.key); // EVICT by its key, not its value
			remove(tail);
		}
	}

	// unlink node, fixing head or tail when node is one of them
	private void remove(Node node) {
		if (node == null) {
			return;
		}

		if (node == head) {
			head = node.next; // FRONT leaves: its next becomes the head
		}

		if (node == tail) {
			tail = node.prev; // BACK leaves: its prev becomes the tail
		}

		if (node.prev != null) {
			node.prev.next = node.next; // the left neighbour skips node, if there is one
		}

		if (node.next != null) {
			node.next.prev = node.prev; // the right neighbour skips node, if there is one
		}
		node.next = null;
		node.prev = null;
	}
}
