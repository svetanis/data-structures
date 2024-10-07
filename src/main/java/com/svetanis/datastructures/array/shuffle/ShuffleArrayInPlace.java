package com.svetanis.datastructures.array.shuffle;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

import java.util.Random;

public final class ShuffleArrayInPlace {
  
  private static final Random generator = new Random();

  public static void shuffle(int[] a) {
    int n = a.length;
    int start = 0; 
    int end = n - 1;

    for (int i = end; i > start; --i) {
      // pick a random index from 0 to i
      int rand = generator.nextInt(i + 1);
      // swap array[i] with the element at random index
      swap(a, i, rand);
    }
  }

  public static void main(String[] args) {
    int[] a = { 16, 27, 37, 13, 11, 5, 5, 9, 9, 3 };
    shuffle(a);
    print(a);
  }
}