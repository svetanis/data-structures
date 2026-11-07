package com.svetanis.datastructures.hashmap.cache.lfu;

import java.util.HashMap;
import java.util.Map;

// 460. LFU Cache
//
// Input: a capacity, then calls to get(key) and put(key, value). get returns the key's value, or -1
// when the key is absent. put inserts or updates; when the cache is full, it first evicts the least
// frequently used key, and among keys used equally often, the least recently used one. Both count
// as a use. On LeetCode: capacity up to 10^4, keys up to 10^5, values up to 10^9, at most 2 * 10^5
// calls, each in O(1) on average. A capacity of 0 stores nothing.
//
// The one idea: one entry of cache is a key and its node (key, value, use count). frequencies maps a
// use count to the list of nodes used exactly that many times, most recently used at the front.
// minFrequency is the smallest count in use, so the eviction victim is the last node of the list
// at minFrequency -- found without searching. A use moves a node from its count's list to the next
// count's list; if that empties the list at minFrequency, minFrequency goes up by one, because the
// moved node now sits exactly one above it. A new key always starts at count 1, so after an insert
// minFrequency is 1.
//
// Time: O(1) per get and put -- a few map lookups and list splices.
// Space: O(capacity) -- one node per key, and at most one list per distinct count.
//
// Siblings: LruCache (package hashmap.cache) evicts by recency only: one list, no counts.

public final class LfuCache {

	private int size;
	private int minFrequency; // the smallest use count any key has
	private Map<Integer, Node> cache; // key -> its node
	private Map<Integer, DoublyLinkedList> frequencies; // use count -> the nodes with that count

	public LfuCache(int size) {
		this.size = size;
		this.cache = new HashMap<>();
		this.frequencies = new HashMap<>();
	}

	public int get(int key) {
		if (size == 0) {
			return -1; // BASE: a cache that holds nothing
		}
		if (!cache.containsKey(key)) {
			return -1; // MISS
		}
		Node node = cache.get(key);
		increaseFrequency(node); // USE: a read counts
		return node.value;
	}

	public void put(int key, int value) {
		if (size == 0) {
			return; // BASE: a cache that holds nothing
		}
		if (cache.containsKey(key)) {
			Node node = cache.get(key);
			node.value = value; // UPDATE in place
			increaseFrequency(node); // USE: a write counts
			return;
		}
		if (cache.size() == size) {
			DoublyLinkedList dll = frequencies.get(minFrequency);
			Node remove = dll.removeLast(); // EVICT: least used, and least recent among those
			cache.remove(remove.key);
			if (dll.isEmpty()) {
				frequencies.remove(minFrequency); // DROP the emptied list; minFrequency is reset below
			}
		}
		Node node = new Node(key, value);
		addNodeToFrequency(node);
		cache.put(key, node);
		minFrequency = 1; // NEW: a new key is the least used key there is
	}

	private void increaseFrequency(Node node) {
		int freq = node.freq;
		DoublyLinkedList dll = frequencies.get(freq);
		dll.remove(node); // MOVE: out of its count's list
		if (dll.isEmpty()) {
			frequencies.remove(freq);
			if (freq == minFrequency) {
				minFrequency++; // RAISE: the node was the last one at the minimum, and moves to min + 1
			}
		}
		node.freq++;
		addNodeToFrequency(node); // ... and into the next count's list, at the front
	}

	private void addNodeToFrequency(Node node) {
		int freq = node.freq;
		DoublyLinkedList dll = frequencies.getOrDefault(freq, new DoublyLinkedList());
		dll.addFirst(node); // FRONT: the most recently used of its count
		frequencies.put(freq, dll);
	}

	public static void main(String[] args) {
		LfuCache cache = new LfuCache(2);
		cache.put(1, 1);
		cache.put(2, 2);
		System.out.println(cache.get(1)); // 1
		cache.put(3, 3); // evicts 2: used once, while 1 was used twice
		System.out.println(cache.get(2)); // -1
		System.out.println(cache.get(3)); // 3
		cache.put(4, 4); // 1 and 3 were both used twice; 1 less recently, so 1 goes
		System.out.println(cache.get(1)); // -1
		System.out.println(cache.get(3)); // 3
		System.out.println(cache.get(4)); // 4
	}
}
