package com.svetanis.datastructures.graph.directed.ts.schedule;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// given a list of tasks and a list of requirements,
// compute a sequence of tasks that can be performed,
// such that we complete every task once while satisfying
// all the requirements
// each requirement will be in the form of a list [a, b]
// where task a needs to be completed first before 
// task b can be completed.
// there is guaranteed to be a solution

public final class TasksSchedulingTimedSubmit {
	// Time Complexity: O(V + E)

	public static int taskScheduling(List<String> tasks, List<Integer> times, List<List<String>> requirements) {
		Map<String, Integer> timing = timing(tasks, times);
		Map<String, List<String>> graph = graph(tasks, requirements);
		return topoSort(timing, graph);
	}

	private static int topoSort(Map<String, Integer> timing, Map<String, List<String>> graph) {
		Map<String, Integer> inDegree = inDegree(graph);
		Map<String, Integer> distances = distances(timing, inDegree, graph);
		Queue<String> queue = sources(inDegree);
		int max = 0;
		while (!queue.isEmpty()) {
			String src = queue.poll();
			max = Math.max(max, distances.get(src));
			for (String neighbor : graph.get(src)) {
				int ind = inDegree.get(neighbor) - 1;
				inDegree.put(neighbor, ind);
				if (ind == 0) {
					queue.add(neighbor);
				}
				int dist = distances.get(src) + timing.get(neighbor);
				distances.put(neighbor, Math.max(distances.get(neighbor), dist));
			}
		}
		return max;
	}

	private static Map<String, Integer> timing(List<String> tasks, List<Integer> times) {
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < tasks.size(); i++) {
			map.put(tasks.get(i), times.get(i));
		}
		return map;
	}

	private static Map<String, Integer> distances(Map<String, Integer> timing, Map<String, Integer> inDegree,
			Map<String, List<String>> graph) {
		Map<String, Integer> map = inDegreeInit(graph);
		// all vertices with 0 in-degree
		for (String key : inDegree.keySet()) {
			if (inDegree.get(key) == 0) {
				map.put(key, timing.get(key));
			}
		}
		return map;
	}

	private static Queue<String> sources(Map<String, Integer> inDegree) {
		Queue<String> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		for (String key : inDegree.keySet()) {
			if (inDegree.get(key) == 0) {
				queue.add(key);
			}
		}
		return queue;
	}

	private static Map<String, List<String>> graph(List<String> tasks, List<List<String>> requirements) {
		Map<String, List<String>> map = graphInit(tasks);
		for (List<String> requirement : requirements) {
			map.get(requirement.get(0)).add(requirement.get(1));
		}
		return map;
	}

	private static Map<String, List<String>> graphInit(List<String> tasks) {
		Map<String, List<String>> map = new HashMap<>();
		for (String task : tasks) {
			map.put(task, new ArrayList<>());
		}
		return map;
	}

	private static Map<String, Integer> inDegree(Map<String, List<String>> graph) {
		Map<String, Integer> map = inDegreeInit(graph);
		for (String node : graph.keySet()) {
			for (String neighbor : graph.get(node)) {
				map.put(neighbor, map.get(neighbor) + 1);
			}
		}
		return map;
	}

	private static Map<String, Integer> inDegreeInit(Map<String, List<String>> graph) {
		Map<String, Integer> map = new HashMap<>();
		for (String node : graph.keySet()) {
			map.put(node, 0);
		}
		return map;
	}

	public static void main(String[] args) {
		List<String> tasks = asList("a", "b", "c", "d");
		List<Integer> times = asList(1, 1, 2, 1);
		List<List<String>> requirements = newArrayList();
		requirements.add(asList("a", "b"));
		requirements.add(asList("c", "b"));
		requirements.add(asList("b", "d"));
		System.out.println(taskScheduling(tasks, times, requirements)); // 4

		List<String> tasks2 = asList("brad", "cad", "dag", "ethereum", "forget", "aggregate");
		List<Integer> times2 = asList(20, 190, 2930, 2375, 123, 5623749);
		List<List<String>> requirements2 = newArrayList();
		requirements2.add(asList("forget", "ethereum"));
		requirements2.add(asList("ethereum", "dag"));
		requirements2.add(asList("dag", "cad"));
		requirements2.add(asList("cad", "brad"));
		requirements2.add(asList("brad", "aggregate"));
		System.out.println(taskScheduling(tasks2, times2, requirements2)); // 5629387

		List<String> tasks3 = asList("abbreviate", "bricks", "cardinals", "dextrous", "fibre", "green", "height");
		List<Integer> times3 = asList(1, 1, 1, 1, 1, 100, 1);
		List<List<String>> requirements3 = newArrayList();
		requirements3.add(asList("abbreviate", "bricks"));
		requirements3.add(asList("cardinals", "bricks"));
		requirements3.add(asList("dextrous", "bricks"));
		requirements3.add(asList("bricks", "fibre"));
		requirements3.add(asList("green", "fibre"));
		System.out.println(taskScheduling(tasks3, times3, requirements3)); // 101

		List<String> tasks4 = asList("aaaabc", "bbbbbabc", "cccccabc", "ddddddabc");
		List<Integer> times4 = asList(1, 2, 3, 1);
		List<List<String>> requirements4 = newArrayList();
		requirements4.add(asList("aaaabc", "bbbbbabc"));
		requirements4.add(asList("aaaabc", "cccccabc"));
		requirements4.add(asList("bbbbbabc", "ddddddabc"));
		requirements4.add(asList("cccccabc", "ddddddabc"));
		System.out.println(taskScheduling(tasks4, times4, requirements4)); // 5
	}
}
