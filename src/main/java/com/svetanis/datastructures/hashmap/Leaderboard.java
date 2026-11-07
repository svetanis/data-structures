package com.svetanis.datastructures.hashmap;

import static java.util.Comparator.reverseOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// 1244. Design A Leaderboard
//
// addScore(playerId, score) adds score to the player's total, putting a new player on the
// board. top(K) returns the sum of the K highest totals. reset(playerId) takes the player
// off the board (their total becomes 0); the player is guaranteed to be on it. On
// LeetCode: 1 <= playerId, K <= 10^4, K is at most the number of players on the board,
// 1 <= score <= 100, at most 1000 calls to each method.
//
// scores maps a player to their total. sorted maps a total to how many players hold it,
// highest total first, so top(K) walks down from the best total and takes up to K players
// from each until K are taken. A total held by nobody must leave sorted: a key left with a
// count of 0 adds nothing to the sum, but every later top() still has to walk over it.

public final class Leaderboard {
	// Time complexity: addScore and reset O(log s), s = distinct totals on the board; top(K)
	// O(K log s), every key it visits holds at least one player, and each is read with get
	// Auxiliary Space: O(p), p = players on the board, one scores entry each and at most one
	// sorted key each

	private Map<Integer, Integer> scores; // player -> their total
	private TreeMap<Integer, Integer> sorted; // total -> players holding it, highest first

	public Leaderboard() {
		this.scores = new HashMap<>();
		this.sorted = new TreeMap<>(reverseOrder());
	}

	public void addScore(int pid, int score) {
		boolean onBoard = scores.containsKey(pid);
		int prev = scores.getOrDefault(pid, 0);
		scores.put(pid, prev + score);
		int curr = scores.get(pid);
		// reduce frequency of prev score
		if (onBoard) {
			drop(prev); // DROP: one player fewer at the old total; a new player has none
		}
		// update frequency of curr score
		sorted.merge(curr, 1, Integer::sum); // ADD: one player more at the new total
	}

	public int top(int k) {
		int sum = 0;
		for (int score : sorted.keySet()) { // WALK totals from the highest down
			int count = sorted.get(score);
			count = Math.min(count, k); // TAKE every player at this total, or the k still needed
			sum += count * score;
			k -= count;
			if (k == 0) {
				break; // STOP: K players taken
			}
		}
		return sum;
	}

	public void reset(int pid) {
		int score = scores.remove(pid);
		drop(score); // DROP: one player fewer at their total
	}

	// decrement the frequency, and remove the total once nobody holds it
	private void drop(int score) {
		sorted.merge(score, -1, Integer::sum);
		if (sorted.get(score) == 0) {
			sorted.remove(score); // REMOVE: an empty total would slow every later top()
		}
	}

	public static void main(String[] args) {
		Leaderboard lb = new Leaderboard();
		lb.addScore(1, 73);
		lb.addScore(2, 56);
		lb.addScore(3, 39);
		lb.addScore(4, 51);
		lb.addScore(5, 4);
		System.out.println(lb.top(1)); // 73
		lb.reset(1);
		lb.reset(2);
		lb.addScore(2, 51);
		System.out.println(lb.top(3)); // 141
	}
}