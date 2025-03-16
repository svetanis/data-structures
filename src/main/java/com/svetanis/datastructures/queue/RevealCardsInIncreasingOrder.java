package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import com.svetanis.java.base.utils.Print;

// 950. Reveal Cards In Increasing Order

public final class RevealCardsInIncreasingOrder {
	// Time complexity: O(n log n)

	public static int[] deckRevealedIncreasing(int[] deck) {
		Arrays.sort(deck);
		int n = deck.length;
		Deque<Integer> dq = init(n);
		int[] a = new int[n];
		for (int card : deck) {
			a[dq.poll()] = card;
			if (!dq.isEmpty()) {
				dq.offer(dq.poll());
			}
		}
		return a;
	}

	private static Deque<Integer> init(int n) {
		Deque<Integer> dq = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			dq.offer(i);
		}
		return dq;
	}

	public static void main(String[] args) {
		int[] a1 = { 17, 13, 11, 2, 3, 5, 7 };
		Print.print(deckRevealedIncreasing(a1)); // [2,13,3,11,5,17,7]
		int[] a2 = { 1, 1000 };
		Print.print(deckRevealedIncreasing(a2)); // [1,1000]
	}
}