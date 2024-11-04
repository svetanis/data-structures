package com.svetanis.datastructures.graph.dfs;

import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Maps.newMap;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

// 332. Reconstruct Itinerary
// The trip must start from "JFK".
// You must use each ticket exactly once.
// If multiple itineraries are possible, 
// return the one that is lexicographically smallest.

public final class ReconstructItenerary {
	// Time Complexity: O(n log n)

	public static ImmutableList<String> itenerary(List<List<String>> tickets) {
		Map<String, Queue<String>> graph = graph(tickets);
		LinkedList<String> list = new LinkedList<>();
		dfs("JFK", graph, list);
		return newList(list);
	}

	private static void dfs(String src, Map<String, Queue<String>> graph, LinkedList<String> list) {
		Queue<String> neighbours = graph.get(src);
		while (neighbours != null && !neighbours.isEmpty()) {
			String neighbour = neighbours.poll();
			dfs(neighbour, graph, list);
		}
		list.addFirst(src);
	}

	private static ImmutableMap<String, Queue<String>> graph(List<List<String>> tickets) {
		Map<String, Queue<String>> map = new HashMap<>();
		for (List<String> ticket : tickets) {
			String src = ticket.get(0);
			String dst = ticket.get(1);
			map.computeIfAbsent(src, k -> new PriorityQueue<>()).offer(dst);
		}
		return newMap(map);
	}

	public static void main(String[] args) {
		List<List<String>> tickets1 = new ArrayList<>();
		tickets1.add(asList("MUC", "LHR"));
		tickets1.add(asList("JFK", "MUC"));
		tickets1.add(asList("SFO", "SJC"));
		tickets1.add(asList("LHR", "SFO"));
		System.out.println(itenerary(tickets1)); // JFK, MUC, LHR, SFO, SJC

		List<List<String>> tickets2 = new ArrayList<>();
		tickets2.add(asList("JFK", "SFO"));
		tickets2.add(asList("JFK", "ATL"));
		tickets2.add(asList("SFO", "ATL"));
		tickets2.add(asList("ATL", "JFK"));
		tickets2.add(asList("ATL", "SFO"));
		System.out.println(itenerary(tickets2)); // JFK, ATL, JFK, SFO, ATL, SFO

		List<List<String>> tickets3 = new ArrayList<>();
		tickets3.add(asList("JFK", "KUL"));
		tickets3.add(asList("JFK", "NRT"));
		tickets3.add(asList("NRT", "JFK"));
		System.out.println(itenerary(tickets3)); // JFK, NRT, JFK, KUL

	}
}
