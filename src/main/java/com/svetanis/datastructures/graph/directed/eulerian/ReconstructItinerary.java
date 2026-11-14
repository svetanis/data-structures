package com.svetanis.datastructures.graph.directed.eulerian;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

// 332. Reconstruct Itinerary
//
// Input: tickets -- tickets[i] = {from, to} is a one-way flight; every ticket must be used exactly
// once, and the trip starts at "JFK". When several itineraries use all the tickets, the answer is
// the one that is smallest when its airport names are compared in order, as in a dictionary.
// Returns the itinerary as a list of airports. On LeetCode: 1 <= tickets.length <= 300, each name
// is three capital letters, from != to, and at least one valid itinerary exists.
//
// The one idea: walk from "JFK", always taking the smallest-named unused ticket (each airport keeps
// its destinations in a priority queue, and a ticket is used by taking it out of its queue). Keep
// walking until you stand at an airport with no unused ticket out of it -- the walk is stuck. That
// airport is the end of whatever part of the trip is still unwritten: either the airport where the
// whole trip ends, or the airport where a loop closed. So an airport is written at the FRONT of the
// answer list, and only once all of its tickets are used. Each airport written later lands in front
// of the ones written earlier, so the stuck airport ends up after the loops that were walked
// before it, and the answer reads in trip order. Writing at the front is the same as adding at the
// end and reversing the list once at the end. Airports are never marked -- one can appear in the
// trip as often as tickets lead into it; only a ticket, once used, is gone.
//
// Time: O(E log E) -- E is the number of tickets; each is added to a priority queue and taken out
// of it once.
// Space: O(E) -- the queues and the answer; the recursion is at most E + 1 calls deep.
// Known limit: one open call per ticket on the current path -- fine at 300 tickets; a chain of
// 10,000 tickets throws StackOverflowError.
//
// Siblings: ReconstructItineraryIterative replaces the recursion with a stack of airports, so a
// long chain of tickets runs. Itenerary in the hashmap package is an easier one: each city has
// exactly one ticket out.

public final class ReconstructItinerary {

	public static List<String> itenerary(List<List<String>> tickets) {
		Map<String, Queue<String>> graph = graph(tickets);
		LinkedList<String> list = new LinkedList<>();
		dfs("JFK", graph, list);
		return list; // RETURN: airports in trip order
	}

	private static void dfs(String src, Map<String, Queue<String>> graph, LinkedList<String> list) {
		Queue<String> neighbours = graph.get(src); // null: no ticket ever leaves src
		while (neighbours != null && !neighbours.isEmpty()) { // WALK: until src has no ticket left
			// TAKE: the smallest-named unused ticket; poll REMOVES it -- that removal is the mark
			String neighbour = neighbours.poll();
			dfs(neighbour, graph, list); // FOLLOW: a new call; src stays, re-checks its queue
		}
		list.addFirst(src); // RECORD: src is written only when all its tickets are used
	}

	private static Map<String, Queue<String>> graph(List<List<String>> tickets) {
		Map<String, Queue<String>> map = new HashMap<>();
		for (List<String> ticket : tickets) { // BUILD: from -> its destinations, smallest first
			String src = ticket.get(0);
			String dst = ticket.get(1);
			map.computeIfAbsent(src, k -> new PriorityQueue<>()).offer(dst);
		}
		return map;
	}

	public static void main(String[] args) {
		List<List<String>> tickets1 = new ArrayList<>();
		tickets1.add(asList("MUC", "LHR"));
		tickets1.add(asList("JFK", "MUC"));
		tickets1.add(asList("SFO", "SJC"));
		tickets1.add(asList("LHR", "SFO"));
		System.out.println(itenerary(tickets1)); // [JFK, MUC, LHR, SFO, SJC]

		List<List<String>> tickets2 = new ArrayList<>();
		tickets2.add(asList("JFK", "SFO"));
		tickets2.add(asList("JFK", "ATL"));
		tickets2.add(asList("SFO", "ATL"));
		tickets2.add(asList("ATL", "JFK"));
		tickets2.add(asList("ATL", "SFO"));
		System.out.println(itenerary(tickets2)); // [JFK, ATL, JFK, SFO, ATL, SFO]

		List<List<String>> tickets3 = new ArrayList<>();
		tickets3.add(asList("JFK", "KUL"));
		tickets3.add(asList("JFK", "NRT"));
		tickets3.add(asList("NRT", "JFK"));
		System.out.println(itenerary(tickets3)); // [JFK, NRT, JFK, KUL]

	}
}
