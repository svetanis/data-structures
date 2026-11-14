package com.svetanis.datastructures.tree.interval;

// One node of an interval tree: a stored interval, its two children, and max. Not a LeetCode
// problem.
//
// Input: new Node(interval) copies the interval into a leaf; the no-argument constructor
// builds a node holding [0, 0]. max is the largest high among all the intervals in this node's
// subtree, the node's own interval included, so a new node starts with max equal to its own
// high. The tree's search reads it to rule out a whole subtree at once.
//
// The one idea: one node holds one interval, and max summarises everything below it, so that a
// search can decide from a single number whether a whole subtree could hold an overlap.
//
// Time: every method is O(1).
// Space: O(1) per node -- one interval copy and four fields.
//
// Siblings: IntervalTree builds and searches the nodes; its insert keeps max up to date.

public final class Node {

  public int max; // largest high anywhere in this subtree
  public Interval interval;
  public Node left;
  public Node right;

  public Node() {
    this.max = 0;
    this.interval = new Interval();
    this.left = null;
    this.right = null;
  }

  public Node(Interval interval) {
    this.interval = new Interval(interval.low, interval.high); // a copy the caller cannot change
    this.max = interval.high; // a subtree of one node
    this.left = null;
    this.right = null;
  }

  @Override
  public String toString() {
    return interval.toString() + " " + max; // RETURN: "[low, high] max"
  }
}