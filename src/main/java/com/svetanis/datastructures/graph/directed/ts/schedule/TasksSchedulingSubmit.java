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

public final class TasksSchedulingSubmit {
	// Time Complexity: O(V + E)

	public static List<String> taskScheduling(List<String> tasks, List<List<String>> requirements) {
		Map<String, List<String>> graph = graph(tasks, requirements);
		return topoSort(graph);
	}

	private static List<String> topoSort(Map<String, List<String>> graph) {
		Map<String, Integer> inDegree = inDegree(graph);
		Queue<String> queue = sources(inDegree);
		List<String> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			list.add(curr);
			for (String neighbor : graph.get(curr)) {
				int ind = inDegree.get(neighbor) - 1;
				inDegree.put(neighbor, ind);
				if (ind == 0) {
					queue.add(neighbor);
				}
			}
		}
		boolean ts = graph.size() == list.size();
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
		List<List<String>> requirements = newArrayList();
		requirements.add(asList("a", "b"));
		requirements.add(asList("c", "b"));
		requirements.add(asList("b", "d"));
		System.out.println(taskScheduling(tasks, requirements)); // a, c, b, d

		List<String> tasks2 = asList("brad", "cad", "dag", "ethereum", "forget", "aggregate");
		List<List<String>> requirements2 = newArrayList();
		requirements2.add(asList("forget", "ethereum"));
		requirements2.add(asList("ethereum", "dag"));
		requirements2.add(asList("dag", "cad"));
		requirements2.add(asList("cad", "brad"));
		requirements2.add(asList("brad", "aggregate"));
		
		System.out.println(taskScheduling(tasks2, requirements2)); // forget, ethereum, dag, cad, brad, aggregate
		List<String> tasks3 = asList("abbreviate", "bricks", "cardinals", "dextrous", "fibre", "green", "height");
		List<List<String>> requirements3 = newArrayList();
		requirements3.add(asList("abbreviate", "bricks"));
		requirements3.add(asList("cardinals", "bricks"));
		requirements3.add(asList("dextrous", "bricks"));
		requirements3.add(asList("bricks", "fibre"));
		requirements3.add(asList("green", "fibre"));
		System.out.println(taskScheduling(tasks3, requirements3)); // dextrous, abbreviate, green, cardinals, height, bricks, fibre
	}
}
