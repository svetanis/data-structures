package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 2225. Find Players With Zero or One Losses

public final class PlayersWithZeroOrOneLosses {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<List<Integer>> winners(int[][] matches) {
		int[] losses = new int[100005];
		for (int[] match : matches) {
			int win = match[0];
			int loss = match[1];
			if (losses[win] == 0) {
				losses[win] = -1;
			}
			if (losses[loss] == -1) {
				losses[loss] = 1;
			} else {
				losses[loss]++;
			}
		}
		List<Integer> zeroLoss = new ArrayList<>();
		List<Integer> oneLoss = new ArrayList<>();
		for (int i = 0; i < losses.length; i++) {
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
			wins[match[0]]++;
			losses[match[1]]++;
		}
		List<List<Integer>> list = new ArrayList<>();
		list.add(new ArrayList<>());
		list.add(new ArrayList<>());
		for (int i = 0; i < wins.length; i++) {
			if (wins[i] >= 1 && losses[i] == 0) {
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