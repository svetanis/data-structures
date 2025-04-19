package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// 1244. Design A Leaderboard

public final class Leaderboard {

	private Map<Integer, Integer> scores;
	private TreeMap<Integer, Integer> sorted;

	public Leaderboard() {
		this.scores = new HashMap<>();
		this.sorted = new TreeMap<>((a, b) -> b - a);
	}

	public void addScore(int pid, int score) {
		int prev = scores.getOrDefault(pid, 0);
		scores.put(pid, prev + score);
		int curr = scores.get(pid);
		// reduce frequency of prev score
		if (curr != prev) {
			sorted.merge(prev, -1, Integer::sum);
		}
		// update frequency of curr score
		sorted.merge(curr, 1, Integer::sum);
	}

	public int top(int k) {
		int sum = 0;
		for (int score : sorted.keySet()) {
			int count = sorted.get(score);
			count = Math.min(count, k);
			sum += count * score;
			k -= count;
			if (k == 0) {
				break;
			}
		}
		return sum;
	}

	public void reset(int pid) {
		int score = scores.remove(pid);
		// decrement frequency
		sorted.merge(score, -1, Integer::sum);
		// remove it if frequency becomes zero
		if (sorted.get(score) == 0) {
			sorted.remove(score);
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