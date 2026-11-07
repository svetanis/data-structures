package com.svetanis.datastructures.hashmap.cache;

// A node of the doubly linked list behind LruCache and LruCacheDummy (146. LRU Cache): the
// key, so that EVICT can remove the map entry of the node at the back; the value; and both
// neighbours, so that a node in the middle of the list can unlink itself in O(1). The
// fields are public on purpose: the caches change the arrows directly.

public final class Node {

  public int key;
  public int value;
  public Node next;
  public Node prev;

  public Node(int key, int value) {
    this(key, value, null, null);
  }

  public Node(int key, int value, Node next, Node prev) {
    this.key = key;
    this.value = value;
    this.next = next;
    this.prev = prev;
  }

  @Override
  public String toString() {
    return key + ":" + value;
  }
}
