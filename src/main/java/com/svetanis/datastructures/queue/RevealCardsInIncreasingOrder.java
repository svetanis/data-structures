package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import com.svetanis.java.base.utils.Print;

// 950. Reveal Cards In Increasing Order
//
// deck holds the card values. Returns the deck in an order such that repeating "reveal the top
// card, then move the next top card to the bottom" reveals the cards in increasing order.
// deck itself is sorted in place, so the caller's array is changed.
//
// Run that same process on positions instead of cards. The queue holds the positions 0..n-1 of
// the answer, top first. Each poll is the position revealed next, so it gets the next smallest
// card; the position after it moves to the back, as the next top card would.

public final class RevealCardsInIncreasingOrder {
	// Time Complexity: O(n log n), the sort; the queue work is O(n)
	// Space Complexity: O(n), the queue and the answer array

	public static int[] deckRevealedIncreasing(int[] deck) {
		Arrays.sort(deck); // sorts the caller's array in place
		int n = deck.length;
		Deque<Integer> dq = init(n);
		int[] a = new int[n];
		for (int card : deck) {
			a[dq.poll()] = card; // the position revealed next gets the next smallest card
			if (!dq.isEmpty()) {
				dq.offer(dq.poll()); // the next top card moves to the bottom
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