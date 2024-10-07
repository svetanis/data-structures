package com.svetanis.datastructures.array.shuffle;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;
import static java.util.Arrays.copyOf;

import java.util.Random;

public final class ShuffleArray {

	private int[] a; // array to store the current state
	private int[] original; // array to store the original state
	private final Random generator;

	public ShuffleArray(int[] a) {
		this.a = a;
		this.original = copyOf(a, a.length);
		this.generator = new Random();
	}

	public int[] shuffle() {
		int n = a.length;
		int start = 0;
		int end = n - 1;

		for (int i = end; i > start; --i) {
			// pick a random index from 0 to i
			int rand = generator.nextInt(i + 1);
			// swap array[i] with the element at random index
			swap(a, i, rand);
		}
		return a;
	}

	public int[] reset() {
		a = copyOf(original, original.length);
		return a;
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 3 };
		ShuffleArray sa = new ShuffleArray(a);
		print(a);
		print(sa.shuffle());
		print(sa.reset());
		print(sa.shuffle());
	}
}