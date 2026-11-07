package com.svetanis.datastructures.hashmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// 846. Hand of Straights
//
// straightHand takes the cards of a hand and a group size, and returns true if the cards
// can be split into groups of exactly that size, each group a run of consecutive values
// (3, 4, 5). On LeetCode: 1 <= hand.length <= 10^4, 0 <= hand[i] <= 10^9,
// 1 <= groupSize <= hand.length.
//
// The smallest card left has nothing smaller to join, so it must start a group, and that
// group can only be card, card + 1, ..., card + size - 1. Walking the cards in sorted
// order, every card still in the counts map is the smallest one left, so it starts a group
// and takes one copy of each card the run needs; a missing card means no split exists. A
// card whose copies were all taken by earlier groups is skipped. The sort changes the
// caller's array.
//
// HandOfStraightsTreeMap keeps the counts in a sorted map instead, so firstKey() is the
// smallest card left and nothing is sorted.

public final class HandOfStraights {
	// Time complexity: O(n log n) for the sort; the inner loop takes one card per step, so
	// all groups together take at most n steps
	// Auxiliary Space: O(n) for the counts map
	
	public static boolean straightHand(int[] hand, int size) {
		Map<Integer, Integer> map = counts(hand);
		Arrays.sort(hand); // SORT: so each card reached that is still in the map is the smallest left
		for (int card : hand) {
			if (map.containsKey(card)) { // START a group: nothing smaller is left to join
				for (int curr = card; curr < card + size; curr++) {
					if (!map.containsKey(curr)) {
						return false; // GAP: the run this card must start cannot be finished
					}
					map.merge(curr, -1, Integer::sum); // TAKE one copy of curr
					if (map.get(curr) == 0) {
						map.remove(curr); // DROP a card with no copies left
					}
				}
			}
		}
		return true;
	}

	private static Map<Integer, Integer> counts(int[] hand) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int card : hand) {
			map.merge(card, 1, Integer::sum); // COUNT copies of each card
		}
		return map;
	}

	public static void main(String[] args) {
		int[] hand1 = { 1, 2, 3, 6, 2, 3, 4, 7, 8 };
		System.out.println(straightHand(hand1, 3)); // true
		int[] hand2 = { 1, 2, 3, 4, 5 };
		System.out.println(straightHand(hand2, 4)); // false
	}
}