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

public final class AllPeopleWithSecretSubmit {
	// Time Complexity: O(m log m + m + n)
	// Space Complexity: O(m + n)

	public static List<Integer> findAll(int n, int[][] meetings, int first) {
		boolean[] visited = new boolean[n];
		visited[0] = true;
		visited[first] = true;
		Arrays.sort(meetings, Comparator.comparingInt(a -> a[2]));
		for (int start = 0; start < meetings.length;) {
			int end = start;
			while (end + 1 < meetings.length && meetings[end + 1][2] == meetings[start][2]) {
				end++;
			}
			Map<Integer, List<Integer>> map = neighbors(meetings, start, end);
			Deque<Integer> queue = enque(map, visited);
			bfs(map, queue, visited);
			start = end + 1;
		}
		return allVisited(visited);
	}

	private static Deque<Integer> enque(Map<Integer, List<Integer>> map, boolean[] visited) {
		Deque<Integer> queue = new ArrayDeque<>();
		for (int key : map.keySet()) {
			if (visited[key]) {
				queue.offer(key);
			}
		}
		return queue;
	}

	private static List<Integer> allVisited(boolean[] visited) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < visited.length; i++) {
			if (visited[i]) {
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
			map.computeIfAbsent(first, key -> new ArrayList<>()).add(second);
			map.computeIfAbsent(second, key -> new ArrayList<>()).add(first);
		}
		return map;
	}

	private static void bfs(Map<Integer, List<Integer>> map, Queue<Integer> queue, boolean[] visited) {
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			List<Integer> neighbors = map.getOrDefault(curr, new ArrayList<>());
			for (int neighbor : neighbors) {
				if (!visited[neighbor]) {
					queue.offer(neighbor);
					visited[neighbor] = true;
				}
			}
		}
	}

	public static void main(String[] args) {
		int[][] m1 = { { 1, 2, 5 }, { 2, 3, 8 }, { 1, 5, 10 } };
		System.out.println(findAll(6, m1, 1)); // 0,1,2,3,5

		int[][] m2 = { { 3, 1, 3 }, { 1, 2, 2 }, { 0, 3, 3 } };
		System.out.println(findAll(4, m2, 3)); // 0,1,3

		int[][] m3 = { { 3, 4, 2 }, { 1, 2, 1 }, { 2, 3, 1 } };
		System.out.println(findAll(5, m3, 1)); // 0,1,2,3,4

		int[][] m4 = { { 5, 1, 4 }, { 0, 4, 18 } };
		System.out.println(findAll(11, m4, 1)); // 0,1,4,5
	}
}
