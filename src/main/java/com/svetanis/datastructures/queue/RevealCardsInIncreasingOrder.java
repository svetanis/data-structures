package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// 950. Reveal Cards In Increasing Order
//
// Input: deck -- the card values. Repeat until the deck is gone: reveal the top card, then if
// cards remain move the next top card to the bottom. Returns an order of the deck in which the
// cards are revealed in increasing order. deck itself is sorted in place, so the caller's array
// is changed. On LeetCode: 1 <= deck.length <= 1000, 1 <= deck[i] <= 10^6, all values unique.
//
// The one idea: run the same process on positions instead of cards. One queue entry is one
// position of the answer array, 0 to n - 1, with the next card to be revealed at the front. Each
// poll is the position revealed next, so it gets the next smallest card; the position after it
// moves to the back, as the next top card would. When every card has been placed, the array is
// the answer.
//
// Time: O(n log n) -- the sort; the queue work is O(n)
// Space: O(n) -- the queue and the answer array

public final class RevealCardsInIncreasingOrder {
	public static int[] deckRevealedIncreasing(int[] deck) {
		Arrays.sort(deck); // SORT: the caller's array, in place
		int n = deck.length;
		Deque<Integer> dq = init(n);
		int[] a = new int[n];
		for (int card : deck) {
			a[dq.poll()] = card; // PLACE: the position revealed next gets the next smallest card
			if (!dq.isEmpty()) {
				dq.offer(dq.poll()); // SKIP: the next top card moves to the bottom
			}
		}
		return a; // RETURN: the deck order
	}

	private static Deque<Integer> init(int n) {
		Deque<Integer> dq = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			dq.offer(i); // RECORD: position i, top first
		}
		return dq;
	}

	public static void main(String[] args) {
		int[] a1 = { 17, 13, 11, 2, 3, 5, 7 };
		System.out.println(Arrays.toString(deckRevealedIncreasing(a1))); // [2, 13, 3, 11, 5, 17, 7]
		int[] a2 = { 1, 1000 };
		System.out.println(Arrays.toString(deckRevealedIncreasing(a2))); // [1, 1000]
	}
}