package com.svetanis.datastructures.array;

// 1535. Find the Winner of an Array Game
//
// Input: an array a of distinct integers and k. The game: compare a[0] and a[1]; the larger stays
// at the front, the smaller goes to the end of the array. Repeat. The game ends when one number
// wins k rounds in a row. Returns the winner. On LeetCode: 2 <= a.length <= 10^5, 1 <= a[i] <=
// 10^6, all distinct, 1 <= k <= 10^9.
//
// The one idea: the "go to the end" step never needs doing. The number at the front just meets the
// numbers a[1], a[2], ... in order, so walk along the array keeping max, the current front, and
// count, how many rounds it has won in a row. A new number that beats max takes over, with one
// win (the round it just won). A smaller one adds a win to max. If count reaches k, max wins. If
// the walk ends first, max has beaten everything once and, being the largest number, would win
// every round after that, so it wins whatever k is; this is why k up to 10^9 is no cost.
//
// Time: O(n) -- one pass; the rounds after it are never played.
// Space: O(1) -- two variables.

public final class WinnerOfArrayGame {

	public static int winner(int[] a, int k) {
		int max = a[0]; // FRONT: the current champion
		for (int i = 1, count = 0; i < a.length; i++) { // WALK: the challengers in order
			if (max < a[i]) {
				max = a[i]; // REPLACE: the challenger wins this round
				count = 1;
			} else {
				count++; // WIN: the champion beats the challenger
			}
			if (count == k) { // DONE: k wins in a row
				break;
			}
		}
		return max; // RETURN: the champion, or the largest number if nobody reached k
	}

	public static void main(String[] args) {
		int[] a1 = { 2, 1, 3, 5, 4, 6, 7 };
		System.out.println(winner(a1, 2)); // 5

		int[] a2 = { 3, 2, 1 };
		System.out.println(winner(a2, 10)); // 3
	}
}
