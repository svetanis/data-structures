package com.svetanis.datastructures.hashmap;

import java.util.TreeMap;

// 846. Hand of Straights
//
// straightHand takes the cards of a hand and a group size, and returns true if the cards
// can be split into groups of exactly that size, each group a run of consecutive values
// (3, 4, 5). On LeetCode: 1 <= hand.length <= 10^4, 0 <= hand[i] <= 10^9,
// 1 <= groupSize <= hand.length.
//
// The smallest card left has nothing smaller left to join, so it must start a group, and
// that group can only be first, first + 1, ..., first + size - 1. The counts are kept in a
// sorted map, so firstKey() is always the smallest card left: each pass takes one group
// from it, a key whose copies are all taken is removed, and a missing card means no split
// exists. The map empty means every card went into a group. The caller's array is not
// changed.
//
// HandOfStraights gets the same order by sorting the array once and counting in a
// HashMap instead.

public final class HandOfStraightsTreeMap {
	// Time complexity: O(n log n); each card is taken exactly once, and each take is a few
	// map calls of O(log n)
	// Auxiliary Space: O(n) for the counts map

	public static boolean straightHand(int[] hand, int size) {
		TreeMap<Integer, Integer> count = new TreeMap<>(); // COUNT: card -> copies left, sorted
		for (int card : hand) {
			count.merge(card, 1, Integer::sum);
		}
		while (!count.isEmpty()) { // GROUP: one per pass, until no card is left
			int first = count.firstKey(); // START: the smallest card left
			int last = first + size - 1;
			for (int card = first; card <= last; card++) { // TAKE: each card of the group
				if (count.get(card) == null) {
					return false; // MISSING: the group cannot be finished
				}
				count.merge(card, -1, Integer::sum); // ONE LESS
				if (count.get(card) == 0) {
					count.remove(card); // at 0 the key goes, or firstKey() returns a card that is gone
				}
			}
		}
		return true; // EMPTY: every card went into a group
	}

	public static void main(String[] args) {
		int[] hand1 = { 1, 2, 3, 6, 2, 3, 4, 7, 8 };
		System.out.println(straightHand(hand1, 3)); // true
		int[] hand2 = { 1, 2, 3, 4, 5 };
		System.out.println(straightHand(hand2, 4)); // false
	}
}
