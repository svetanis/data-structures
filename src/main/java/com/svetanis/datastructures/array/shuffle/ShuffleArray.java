package com.svetanis.datastructures.array.shuffle;

import static com.svetanis.java.base.utils.Print.print;

import java.util.Arrays;
import java.util.Random;

// 384. Shuffle an Array

public final class ShuffleArray {

	private int[] a; // array to store the current state
	private int[] original; // array to store the original state
	private final Random generator;

	public ShuffleArray(int[] a) {
		this.a = a;
		this.original = Arrays.copyOf(a, a.length);
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
		a = Arrays.copyOf(original, original.length);
		return a;
	}

	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
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