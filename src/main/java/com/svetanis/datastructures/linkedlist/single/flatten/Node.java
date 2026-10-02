package com.svetanis.datastructures.linkedlist.single.flatten;

// One node of a list that runs in two directions: next to the node on the right, and down
// to the head of another list, or null. FlattenList and FlattenMultiLevelList use it. The
// LeetCode problem of this family (LC 430) has its own node type with next, prev and child,
// which those files declare for themselves.

public final class Node {

  public int val;
  public Node next;
  public Node down;

  public Node() {
    this(0);
  }

  public Node(int val) {
    this.val = val;
    this.next = null;
    this.down = null;
  }

  @Override
  public String toString() {
    return Integer.toString(val);
  }
}