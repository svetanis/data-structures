package com.svetanis.datastructures.array.shuffle;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

import java.util.Random;

public final class ShuffleArrayRecursive {

  private static final Random generator = new Random();

  public static int[] shuffle(int[] a, int i) {

    if (i == 0) {
      return a;
    }

    shuffle(a, i - 1); // shuffle earlier part

    // pick random index to swap with
    int rand = rand(0, i);

    // swap element randomIndex and i
    swap(a, rand, i);

    // return shuffled array
    return a;
  }

  public static int rand(int low, int high) {
    return generator.nextInt(high - low + 1) + low;
  }

  public static void main(String[] args) {
    int[] a = { 16, 27, 37, 13, 11, 5, 5, 9, 9, 3 };
    shuffle(a, a.length - 1);
    print(a);
  }
}