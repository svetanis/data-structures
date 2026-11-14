package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 2092. Find All People With Secret
//
// Input: n -- people numbered 0 to n - 1; meetings[i] = { x, y, time } -- person x and person y
// meet at that time; firstPerson. Person 0 knows a secret and tells firstPerson at time 0. At a
// meeting, a person who knows the secret tells the other at once, so a chain of meetings that all
// happen at the same time passes it along in that one moment; a chain that needs a later meeting
// before an earlier one does not count. Returns every person who knows the secret after all the
// meetings, in any order (this version returns them in increasing order). On LeetCode:
// 2 <= n <= 10^5, 1 <= meetings.length <= 10^5, 0 <= x, y <= n - 1, x != y,
// 1 <= time <= 10^5, 1 <= firstPerson <= n - 1.
//
// The one idea: sort the meetings by time, so the meetings of one time sit side by side, then
// handle each such group on its own. For one group, the map says: a person -> the people they
// meet in this group. A queue entry is one person who knows the secret and is about to tell the
// people he meets in this group. The queue starts with the people of the group who already know
// (visited[p] is true once person p knows); the walk follows the map outward, and everyone it
// reaches learns the secret at this same time. A group never looks at an earlier group again.
// The input array is sorted in place.
//
// Time: O(m log m + n) -- the sort, then every meeting is looked at once, in its own group;
// the final scan of the n marks is O(n).
// Space: O(m + n) -- the visited marks and one group's map.
//
// Siblings: AllPeopleWithSecret filters all the meetings again for every distinct time and
// re-queues everyone who knows the secret, which costs O(T * (n + m)).

public final class AllPeopleWithSecretSubmit {

	public static List<Integer> findAll(int n, int[][] meetings, int first) {
		boolean[] visited = new boolean[n];
		visited[0] = true; // BASE: person 0 knows the secret
		visited[first] = true; // BASE: and tells firstPerson at time 0
		Arrays.sort(meetings, Comparator.comparingInt(a -> a[2])); // SORT: by time
		for (int start = 0; start < meetings.length;) { // WALK: one group of equal times per pass
			int end = start;
			while (end + 1 < meetings.length && meetings[end + 1][2] == meetings[start][2]) {
				end++; // TAKE: the group is meetings[start..end]
			}
			Map<Integer, List<Integer>> map = neighbors(meetings, start, end);
			Deque<Integer> queue = enque(map, visited); // SEED: group members who know already
			bfs(map, queue, visited);
			start = end + 1;
		}
		return allVisited(visited);
	}

	private static Deque<Integer> enque(Map<Integer, List<Integer>> map, boolean[] visited) {
		Deque<Integer> queue = new ArrayDeque<>();
		for (int key : map.keySet()) {
			if (visited[key]) { // TAKE: knows the secret
				queue.offer(key);
			}
		}
		return queue;
	}

	private static List<Integer> allVisited(boolean[] visited) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < visited.length; i++) {
			if (visited[i]) { // RECORD: i knows the secret
				list.add(i);
			}
		}
		return list;
	}

	private static Map<Integer, List<Integer>> neighbors(int[][] meetings, int start, int end) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int k = start; k <= end; k++) {
			int first = meetings[k][0];
			int second = meetings[k][1];
			map.computeIfAbsent(first, key -> new ArrayList<>()).add(second); // RECORD: both ways
			map.computeIfAbsent(second, key -> new ArrayList<>()).add(first);
		}
		return map;
	}

	private static void bfs(Map<Integer, List<Integer>> map, Queue<Integer> queue, boolean[] visited) {
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			List<Integer> neighbors = map.getOrDefault(curr, new ArrayList<>());
			for (int neighbor : neighbors) { // WALK: the people curr meets in this group
				if (!visited[neighbor]) { // SKIP: already knows
					queue.offer(neighbor);
					visited[neighbor] = true; // TAKE: told now, so told once
				}
			}
		}
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
}
