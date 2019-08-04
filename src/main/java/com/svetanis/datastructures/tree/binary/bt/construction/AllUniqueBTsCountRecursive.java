package com.svetanis.datastructures.tree.binary.bt.construction;

public final class AllUniqueBTsCountRecursive {

  public static int count(int n) {
    if (n <= 1) {
      return 1;
    }
    int count = 0;
    for (int i = 1; i <= n; i++) {
      int left = count(i - 1);
      int right = count(n - i);
      count += (left * right);
    }
    return count;
  }

  public static void main(String[] args) {
    System.out.println(count(3));
  }
}
