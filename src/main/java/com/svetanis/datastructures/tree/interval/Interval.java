package com.svetanis.datastructures.tree.interval;

// A closed interval [low, high] of an interval tree. Not a LeetCode problem.
//
// Input: low and high, the two endpoints; newInterval(low, high) and the constructor build one,
// and the no-argument constructor builds [0, 0]. Both endpoints belong to the interval, so
// [1, 5] and [5, 6] overlap at 5. Nothing checks that low <= high; the tree's overlap test
// assumes it. toString prints "[low, high]".
//
// The one idea: an interval is just its two endpoints, and two closed intervals overlap exactly
// when each one starts no later than the other ends (see IntervalTree.isOverlap).
//
// Time: every method is O(1).
// Space: O(1) -- two ints.
//
// Siblings: Node holds one of these in the tree, and IntervalTree and ConflictingAppointments
// use it.

public final class Interval {

  public int low;
  public int high;

  public static Interval newInterval(int low, int high) {
    return new Interval(low, high);
  }

  public Interval() {
    this.low = 0;
    this.high = 0;
  }

  public Interval(int low, int high) {
    this.low = low;
    this.high = high;
  }

  @Override
  public String toString() {
    return "[" + low + ", " + high + "]"; // RETURN: "[low, high]"
  }
}