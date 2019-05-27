package com.svetanis.datastructures.tree.interval;

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
    return "[" + low + ", " + high + "]";
  }
}