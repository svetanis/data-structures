package com.svetanis.datastructures.graph.directed.ts.schedule;

import static java.lang.Math.max;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// Task Scheduling With Times: least total time (the longest path in a graph with no loop)
//
// Input: a list of distinct task names, times.get(i) = how long tasks.get(i) takes, and
// requirements [a, b], each meaning task a must be completed before task b starts. Any number of
// tasks may run at the same moment (friends help). Every name in a requirement is in `tasks`, and
// the statement guarantees there is no loop.
// Returns the least time in which every task can be finished.
//
// The one idea: let finish[t] be the earliest moment task t can be done. A task with nothing
// before it finishes at its own time. Any other task cannot start until the last of its
// prerequisites is done, so finish[t] = time[t] + the largest finish among the tasks that must
// precede it. Take tasks in an order where a task comes after all of those (the ready queue: a
// task is READY once every requirement into it is met). When a task is taken its finish is final,
// so push it onward: for each task it points at, finish[next] = max(finish[next],
// finish[task] + time[next]). The answer is the largest finish of all.
//
// Time: O(V + E) -- each task is taken once and each requirement is looked at once.
// Space: O(V + E) -- the graph, the in-degree map, the times and the finish map.
//
// Siblings: TasksSchedulingTimedSubmit is the same method; TasksScheduling returns an order and
// ignores time.

public final class TasksSchedulingTimed {

	public static int taskScheduling(List<String> tasks, List<Integer> times, List<List<String>> requirements) {
		Map<String, Integer> timing = timing(tasks, times);
		Map<String, List<String>> graph = graph(tasks, requirements);
		return topoSort(timing, graph);
	}

	private static int topoSort(Map<String, Integer> timing, Map<String, List<String>> graph) {
		Map<String, Integer> inDegree = inDegree(graph);
		Map<String, Integer> distances = distances(timing, inDegree, graph); // CARDS: earliest finish per task
		Queue<String> queue = sources(inDegree);
		int max = 0;
		while (!queue.isEmpty()) {
			String src = queue.poll();
			max = max(max, distances.get(src)); // RECORD: src's finish is final now
			for (String neighbor : graph.get(src)) {
				int ind = inDegree.get(neighbor) - 1; // CROSS OFF: one requirement into neighbor is met
				inDegree.put(neighbor, ind);
				if (ind == 0) {
					queue.add(neighbor); // READY
				}
				int dist = distances.get(src) + timing.get(neighbor); // PUSH: neighbor starts after src
				distances.put(neighbor, max(distances.get(neighbor), dist)); // KEEP the later start
			}
		}
		return max;
	}

	private static Map<String, Integer> distances(Map<String, Integer> timing, Map<String, Integer> inDegree,
			Map<String, List<String>> graph) {
		Map<String, Integer> map = inDegreeInit(graph);
		// all vertices with 0 in-degree finish at their own time
		for (String key : inDegree.keySet()) {
			if (inDegree.get(key) == 0) {
				map.put(key, timing.get(key));
			}
		}
		return map;
	}

	private static Map<String, Integer> timing(List<String> tasks, List<Integer> times) {
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < tasks.size(); i++) {
			map.put(tasks.get(i), times.get(i));
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
			map.get(requirement.get(0)).add(requirement.get(1)); // ARROW: first before second
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
		List<List<String>> requirements = new ArrayList<>();
		requirements.add(asList("a", "b"));
		requirements.add(asList("c", "b"));
		requirements.add(asList("b", "d"));
		System.out.println(taskScheduling(tasks, times, requirements)); // 4

		List<String> tasks2 = asList("brad", "cad", "dag", "ethereum", "forget", "aggregate");
		List<Integer> times2 = asList(20, 190, 2930, 2375, 123, 5623749);
		List<List<String>> requirements2 = new ArrayList<>();
		requirements2.add(asList("forget", "ethereum"));
		requirements2.add(asList("ethereum", "dag"));
		requirements2.add(asList("dag", "cad"));
		requirements2.add(asList("cad", "brad"));
		requirements2.add(asList("brad", "aggregate"));
		System.out.println(taskScheduling(tasks2, times2, requirements2)); // 5629387

		List<String> tasks3 = asList("abbreviate", "bricks", "cardinals", "dextrous", "fibre", "green", "height");
		List<Integer> times3 = asList(1, 1, 1, 1, 1, 100, 1);
		List<List<String>> requirements3 = new ArrayList<>();
		requirements3.add(asList("abbreviate", "bricks"));
		requirements3.add(asList("cardinals", "bricks"));
		requirements3.add(asList("dextrous", "bricks"));
		requirements3.add(asList("bricks", "fibre"));
		requirements3.add(asList("green", "fibre"));
		System.out.println(taskScheduling(tasks3, times3, requirements3)); // 101

		List<String> tasks4 = asList("aaaabc", "bbbbbabc", "cccccabc", "ddddddabc");
		List<Integer> times4 = asList(1, 2, 3, 1);
		List<List<String>> requirements4 = new ArrayList<>();
		requirements4.add(asList("aaaabc", "bbbbbabc"));
		requirements4.add(asList("aaaabc", "cccccabc"));
		requirements4.add(asList("bbbbbabc", "ddddddabc"));
		requirements4.add(asList("cccccabc", "ddddddabc"));
		System.out.println(taskScheduling(tasks4, times4, requirements4)); // 5
	}
}
