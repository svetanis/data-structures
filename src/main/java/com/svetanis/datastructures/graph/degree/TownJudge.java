package com.svetanis.datastructures.graph.degree;

// 997. Find the Town Judge
//
// Input: n people numbered 1..n, and trust, a list of pairs [a, b] meaning person a trusts person
// b. On LeetCode: 1 <= n <= 1000, 0 <= trust.length <= 10^4, a != b, and all pairs are different.
// Returns: the label of the town judge, or -1 if there is none. The judge trusts nobody, and
// everybody else trusts the judge; there can be at most one such person.
//
// The one idea: a judge is a person with outgoing trust 0 and incoming trust n - 1. Count both for
// everyone in one pass over the pairs, then look for that person. If two people both had n - 1
// people trusting them, each would have to trust the other, which breaks the "trusts nobody"
// rule, so the answer is never ambiguous. Person i's two counts are trustCount[i] (how many
// people i trusts) and trustedByCount[i] (how many people trust i).
//
// Time: O(n + m) -- one pass over the m pairs, one pass over the n people.
// Space: O(n) -- two arrays of n + 1 counts.
//
// Siblings: none.

public final class TownJudge {

	public static int townJudge(int n, int[][] trust) {
		int[] trustCount = new int[n + 1]; // CARDS: trustCount[i] -- how many people i trusts
		int[] trustedByCount = new int[n + 1]; // CARDS: trustedByCount[i] -- how many people trust i
		for (int[] relation : trust) {
			int trusts = relation[0];
			int trusted = relation[1];
			trustCount[trusts]++; // COUNT: one more person trusted by a
			trustedByCount[trusted]++; // COUNT: one more person trusting b
		}
		for (int i = 1; i <= n; i++) {
			if (trustCount[i] == 0 && trustedByCount[i] == n - 1) {
				return i; // RETURN: trusts nobody, trusted by everybody else
			}
		}
		return -1; // RETURN: no judge
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2 } };
		int[][] g2 = { { 1, 3 }, { 2, 3 } };
		int[][] g3 = { { 1, 3 }, { 2, 3 }, { 3, 1 } };
		System.out.println(townJudge(2, g1)); // 2
		System.out.println(townJudge(3, g2)); // 3
		System.out.println(townJudge(3, g3)); // -1
	}
}
