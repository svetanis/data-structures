package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 2225. Find Players With Zero or One Losses
//
// winners(matches) takes matches[i] = [winner, loser] and returns two lists: the players who
// never lost, and the players who lost exactly once, each in increasing order. Only players
// who played at least one match count. On LeetCode: 1 <= matches.length <= 10^5,
// 1 <= winner, loser <= 10^5, winner != loser, no match appears twice.
//
// Player numbers are at most 10^5, so an array with one slot per number replaces a map, and
// reading the slots from 0 up returns both lists already sorted. The only care is telling
// "never played" from "played and never lost", since both have zero losses.
//
// Two methods, the same scan. They differ in how that difference is kept:
//   winners   one array: 0 never played, -1 played and never lost, k >= 1 lost k times
//   winners2  two arrays, wins and losses; never lost means at least one win and no loss

public final class PlayersWithZeroOrOneLosses {
	// Time complexity: O(m + P), m matches, P = 10^5 + 5 slots read once
	// Auxiliary Space: O(P), one or two arrays of P ints; the result lists are extra

	public static List<List<Integer>> winners(int[][] matches) {
		int[] losses = new int[100005];
		for (int[] match : matches) {
			int win = match[0];
			int loss = match[1];
			if (losses[win] == 0) { // PLAYED: a first sighting as a winner
				losses[win] = -1;
			}
			if (losses[loss] == -1) { // LOSS: the first loss of a player marked -1
				losses[loss] = 1;
			} else {
				losses[loss]++; // LOSS: from 0, or one more
			}
		}
		List<Integer> zeroLoss = new ArrayList<>();
		List<Integer> oneLoss = new ArrayList<>();
		for (int i = 0; i < losses.length; i++) { // READ: in increasing order, so no sort
			if (losses[i] == -1) {
				zeroLoss.add(i);
			} else if (losses[i] == 1) {
				oneLoss.add(i);
			}
		}
		List<List<Integer>> list = new ArrayList<>();
		list.add(zeroLoss);
		list.add(oneLoss);
		return list;
	}

	public static List<List<Integer>> winners2(int[][] matches) {
		int[] wins = new int[100005];
		int[] losses = new int[100005];
		for (int[] match : matches) {
			wins[match[0]]++; // COUNT
			losses[match[1]]++; // COUNT
		}
		List<List<Integer>> list = new ArrayList<>();
		list.add(new ArrayList<>());
		list.add(new ArrayList<>());
		for (int i = 0; i < wins.length; i++) { // READ: in increasing order
			if (wins[i] >= 1 && losses[i] == 0) { // PLAYED, never lost
				list.get(0).add(i);
			}
			if (losses[i] == 1) {
				list.get(1).add(i);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		int[][] m1 = { { 1, 3 }, { 2, 3 }, { 3, 6 }, //
				{ 5, 6 }, { 5, 7 }, { 4, 5 }, //
				{ 4, 8 }, { 4, 9 }, { 10, 4 }, { 10, 9 } };//
		System.out.println(winners(m1)); // [1,2,10],[4,5,7,8]

		int[][] m2 = { { 2, 3 }, { 1, 3 }, //
				{ 5, 4 }, { 6, 4 } };//
		System.out.println(winners(m2)); // [1,2,5,6],[]
	}
}