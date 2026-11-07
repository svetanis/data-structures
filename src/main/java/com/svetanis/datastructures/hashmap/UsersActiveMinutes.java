package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 1817. Finding the Users Active Minutes
//
// uam(logs, k) takes logs[i] = [userId, minute], one action each. A user's active minutes
// (UAM) are the number of DISTINCT minutes in which they acted. It returns an array of k
// ints where place j - 1 holds how many users have a UAM of exactly j. On LeetCode:
// 1 <= logs.length <= 10^4, 0 <= userId <= 10^9, 1 <= minute <= 10^5, k is at least the
// largest UAM and at most 10^5.
//
// A map from each user to the set of minutes they acted in: the set drops a minute logged
// twice, so its size is the UAM. Then each user adds one to the place for their UAM.
//
// Siblings, same problem: none in this package.

public final class UsersActiveMinutes {
	// Time complexity: O(n + k), one pass over the logs and the k-place result
	// Auxiliary Space: O(n), the users' minute sets; the result is k ints

	public static int[] uam(int[][] logs, int k) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for (int[] log : logs) {
			map.computeIfAbsent(log[0], u -> new HashSet<>()).add(log[1]); // DISTINCT: a repeated minute adds nothing
		}
		int[] result = new int[k];
		for (Set<Integer> set : map.values()) {
			result[set.size() - 1]++; // COUNT: a UAM of j goes to place j - 1
		}
		return result;
	}

	public static void main(String[] args) {
		int[][] logs1 = { { 0, 5 }, { 1, 2 }, { 0, 2 }, { 0, 5 }, { 1, 3 } };
		print(uam(logs1, 5)); // 0 2 0 0 0

		int[][] logs2 = { { 1, 1 }, { 2, 2 }, { 2, 3 } };
		print(uam(logs2, 4)); // 1 1 0 0
	}

	private static void print(int[] a) {
		for (int value : a) {
			System.out.print(value);
			System.out.print(' ');
		}
		System.out.println();
	}
}