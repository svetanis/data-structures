package com.svetanis.datastructures.tree.interval;

public final class Node {

  public int max;
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
    this.interval = new Interval(interval.low, interval.high);
    this.max = interval.high;
    this.left = null;
    this.right = null;
  }

  @Override
  public String toString() {
    return interval.toString() + " " + max;
  }
}