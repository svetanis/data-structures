package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

// 2092. Find All People With Secret
//
// Input: n -- people numbered 0 to n - 1; meetings[i] = { x, y, time } -- person x and person y
// meet at that time; firstPerson. Person 0 knows a secret and tells firstPerson at time 0. At a
// meeting, a person who knows the secret tells the other at once, so a chain of meetings that all
// happen at the same time passes it along in that one moment; a chain that needs a later meeting
// before an earlier one does not count. Returns every person who knows the secret after all the
// meetings, in any order. On LeetCode: 2 <= n <= 10^5, 1 <= meetings.length <= 10^5, 0 <= x, y <= n - 1,
// x != y, 1 <= time <= 10^5, 1 <= firstPerson <= n - 1.
//
// The one idea: take the distinct times in increasing order, and treat each time on its own.
// At one time, the meetings of that time form a small map: a person -> the people they meet then.
// A queue entry is one person who knows the secret and is about to tell the people he meets at
// this time. The queue starts with everyone who already knows the secret; the walk follows the
// map outward from them, and everyone it reaches learns the secret at this same time. visited[p]
// is true once person p knows the secret, so nobody is told twice. Later times start from the
// larger set of people who know, and meetings of an earlier time are never looked at again.
//
// Time: O(T * (n + m)) -- T is the number of distinct times; each time re-queues every person who
// knows the secret (up to n) and filters all m meetings to find that time's ones.
// Space: O(n + m) -- the visited marks, the meeting list and one time's map.
//
// Siblings: AllPeopleWithSecretSubmit sorts the meetings once and gives each time only its own
// meetings, so it does not repeat the filtering and re-queuing for every time.

public final class AllPeopleWithSecret {

	public static List<Integer> findAll(int n, int[][] grid, int first) {
		boolean[] visited = new boolean[n];
		visited[0] = true; // BASE: person 0 knows the secret
		visited[first] = true; // BASE: and tells firstPerson at time 0
		List<Meeting> meetings = meetings(grid);
		List<Integer> times = times(meetings); // SORT: distinct times, earliest first
		Set<Integer> set = new HashSet<>(); // everyone who knows the secret so far
		set.add(0);
		set.add(first);
		for (int time : times) { // WALK: one time at a time
			Queue<Integer> queue = new ArrayDeque<>();
			queue.addAll(set); // SEED: everyone who knows already
			Map<Integer, List<Integer>> map = neighbors(meetings, time);
			set.addAll(bfs(map, queue, visited)); // RECORD: the people this time reached
		}
		return new ArrayList<>(set); // RETURN: all who know
	}

	private static List<Integer> times(List<Meeting> meetings) {
		Set<Integer> set = meetings.stream().map(m -> m.time).collect(Collectors.toSet());
		List<Integer> list = new ArrayList<>(set);
		Collections.sort(list); // SORT: increasing time
		return list;
	}

	private static Map<Integer, List<Integer>> neighbors(List<Meeting> meetings, int time) {
		List<Meeting> filtered = meetings.stream().filter(m -> m.time == time).collect(Collectors.toList()); // TAKE: this time only
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (Meeting meeting : filtered) {
			map.computeIfAbsent(meeting.first, k -> new ArrayList<>()).add(meeting.second); // RECORD: both ways
			map.computeIfAbsent(meeting.second, k -> new ArrayList<>()).add(meeting.first);
		}
		return map;
	}

	private static List<Integer> bfs(Map<Integer, List<Integer>> map, Queue<Integer> queue, boolean[] visited) {
		Set<Integer> set = new HashSet<>();
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			set.add(curr); // RECORD: curr knows the secret
			List<Integer> neighbors = map.getOrDefault(curr, new ArrayList<>());
			for (int neighbor : neighbors) { // WALK: the people curr meets at this time
				if (!visited[neighbor]) { // SKIP: already knows
					queue.add(neighbor);
					visited[neighbor] = true; // TAKE: told now, so told once
				}
			}
		}
		return new ArrayList<>(set);
	}

	private static List<Meeting> meetings(int[][] grid) {
		List<Meeting> list = new ArrayList<>();
		for (int[] a : grid) {
			list.add(new Meeting(a[0], a[1], a[2]));
		}
		return list;
	}

	public static void main(String[] args) {
		int[][] m1 = { { 1, 2, 5 }, { 2, 3, 8 }, { 1, 5, 10 } };
		System.out.println(findAll(6, m1, 1)); // [0, 1, 2, 3, 5]

		int[][] m2 = { { 3, 1, 3 }, { 1, 2, 2 }, { 0, 3, 3 } };
		System.out.println(findAll(4, m2, 3)); // [0, 1, 3]

		int[][] m3 = { { 3, 4, 2 }, { 1, 2, 1 }, { 2, 3, 1 } };
		System.out.println(findAll(5, m3, 1)); // [0, 1, 2, 3, 4]

		int[][] m4 = { { 5, 1, 4 }, { 0, 4, 18 } };
		System.out.println(findAll(11, m4, 1)); // [0, 1, 4, 5]

	}

	private static class Meeting {
		private int first;
		private int second;
		private int time;

		public Meeting(int first, int second, int time) {
			this.first = first;
			this.second = second;
			this.time = time;
		}
	}
}
