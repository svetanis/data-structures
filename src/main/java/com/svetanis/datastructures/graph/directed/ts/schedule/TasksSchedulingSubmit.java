package com.svetanis.datastructures.graph.directed.ts.schedule;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// Task Scheduling (the same task as 210. Course Schedule II, with named tasks)
//
// Input: a list of distinct task names and a list of requirements [a, b], each meaning task a must
// be completed before task b. Every name in a requirement is in `tasks`. The statement guarantees
// a solution exists.
// Returns one order that completes every task once and respects every requirement; null if the
// requirements form a loop, which the statement rules out. Any valid order is correct, and this
// one depends on the hash order of the names.
//
// The one idea: a task is READY when no requirement still points at it (its in-degree is 0).
// Take a ready task, append it to the answer, and cross off the requirements leaving it; any task
// whose count just reached 0 is now ready. A task is appended only after all the tasks that must
// precede it, so the answer is valid.
//
// Time: O(V + E) -- each task enters the queue once and each requirement is crossed off once.
// Space: O(V + E) -- the graph, the in-degree map and the queue.
//
// Siblings: TasksScheduling is the same method returning an empty list on a loop;
// TasksSchedulingTimed adds a time per task and asks for the least total time instead of the order.

public final class TasksSchedulingSubmit {

	public static List<String> taskScheduling(List<String> tasks, List<List<String>> requirements) {
		Map<String, List<String>> graph = graph(tasks, requirements);
		return topoSort(graph);
	}

	private static List<String> topoSort(Map<String, List<String>> graph) {
		Map<String, Integer> inDegree = inDegree(graph);
		Queue<String> queue = sources(inDegree);
		List<String> list = new ArrayList<>(); // ORDER: the tasks taken so far
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			list.add(curr); // TAKE
			for (String neighbor : graph.get(curr)) {
				int ind = inDegree.get(neighbor) - 1; // CROSS OFF: one requirement into neighbor is met
				inDegree.put(neighbor, ind);
				if (ind == 0) {
					queue.add(neighbor); // READY
				}
			}
		}
		boolean ts = graph.size() == list.size(); // RETURN: short == a loop
		return ts ? list : null;
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
		List<List<String>> requirements = new ArrayList<>();
		requirements.add(asList("a", "b"));
		requirements.add(asList("c", "b"));
		requirements.add(asList("b", "d"));
		System.out.println(taskScheduling(tasks, requirements)); // [a, c, b, d]

		List<String> tasks2 = asList("brad", "cad", "dag", "ethereum", "forget", "aggregate");
		List<List<String>> requirements2 = new ArrayList<>();
		requirements2.add(asList("forget", "ethereum"));
		requirements2.add(asList("ethereum", "dag"));
		requirements2.add(asList("dag", "cad"));
		requirements2.add(asList("cad", "brad"));
		requirements2.add(asList("brad", "aggregate"));

		System.out.println(taskScheduling(tasks2, requirements2)); // [forget, ethereum, dag, cad, brad, aggregate]
		List<String> tasks3 = asList("abbreviate", "bricks", "cardinals", "dextrous", "fibre", "green", "height");
		List<List<String>> requirements3 = new ArrayList<>();
		requirements3.add(asList("abbreviate", "bricks"));
		requirements3.add(asList("cardinals", "bricks"));
		requirements3.add(asList("dextrous", "bricks"));
		requirements3.add(asList("bricks", "fibre"));
		requirements3.add(asList("green", "fibre"));
		System.out.println(taskScheduling(tasks3, requirements3)); // [dextrous, abbreviate, green, cardinals, height, bricks, fibre]
	}
}
