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

public final class AllPeopleWithSecret {

	public static List<Integer> findAll(int n, int[][] grid, int first) {
		boolean[] visited = new boolean[n];
		visited[0] = true;
		visited[first] = true;
		List<Meeting> meetings = meetings(grid);
		List<Integer> times = times(meetings);
		Set<Integer> set = new HashSet<>();
		set.add(0);
		set.add(first);
		for (int time : times) {
			Queue<Integer> queue = new ArrayDeque<>();
			queue.addAll(set);
			Map<Integer, List<Integer>> map = neighbors(meetings, time);
			set.addAll(bfs(map, queue, visited));
		}
		return new ArrayList<>(set);
	}

	private static List<Integer> times(List<Meeting> meetings) {
		Set<Integer> set = meetings.stream().map(m -> m.time).collect(Collectors.toSet());
		List<Integer> list = new ArrayList<>(set);
		Collections.sort(list);
		return list;
	}

	private static Map<Integer, List<Integer>> neighbors(List<Meeting> meetings, int time) {
		List<Meeting> filtered = meetings.stream().filter(m -> m.time == time).collect(Collectors.toList());
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (Meeting meeting : filtered) {
			map.computeIfAbsent(meeting.first, k -> new ArrayList<>()).add(meeting.second);
			map.computeIfAbsent(meeting.second, k -> new ArrayList<>()).add(meeting.first);
		}
		return map;
	}

	private static List<Integer> bfs(Map<Integer, List<Integer>> map, Queue<Integer> queue, boolean[] visited) {
		Set<Integer> set = new HashSet<>();
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			set.add(curr);
			List<Integer> neighbors = map.getOrDefault(curr, new ArrayList<>());
			for (int neighbor : neighbors) {
				if (!visited[neighbor]) {
					queue.add(neighbor);
					visited[neighbor] = true;
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
		System.out.println(findAll(6, m1, 1)); // 0,1,2,3,5

		int[][] m2 = { { 3, 1, 3 }, { 1, 2, 2 }, { 0, 3, 3 } };
		System.out.println(findAll(4, m2, 3)); // 0,1,3

		int[][] m3 = { { 3, 4, 2 }, { 1, 2, 1 }, { 2, 3, 1 } };
		System.out.println(findAll(5, m3, 1)); // 0,1,2,3,4

		int[][] m4 = { { 5, 1, 4 }, { 0, 4, 18 } };
		System.out.println(findAll(11, m4, 1)); // 0,1,4,5

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
