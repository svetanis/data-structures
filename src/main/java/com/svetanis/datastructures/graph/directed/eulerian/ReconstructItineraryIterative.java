package com.svetanis.datastructures.graph.directed.eulerian;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
// The one idea: the same walk as ReconstructItinerary -- take the smallest unused ticket, write an
// airport at the front of the answer only once it has no tickets left. The only change is that a
// stack of airports replaces the recursion. One stack entry is an open call: an airport the walk
// has reached and not yet written. The top of the stack is the airport the walk stands at;
// following a ticket pushes its destination, and a stuck top is popped and written.
//
// Time: O(E log E) -- E is the number of tickets; each is added to a priority queue and taken out
// of it once.
// Space: O(E) -- the queues, the stack and the answer; no call stack, so a chain of 10,000 tickets
// runs.
//
// Siblings: ReconstructItinerary is the recursive version, the shorter code, which overflows a
// default Java stack on a long chain of tickets.

public final class ReconstructItineraryIterative {

	public static List<String> itenerary(List<List<String>> tickets) {
		Map<String, Queue<String>> graph = graph(tickets);
		LinkedList<String> list = new LinkedList<>();
		Deque<String> stack = new ArrayDeque<>(); // OPEN: airports reached and not yet written
		stack.push("JFK"); // START: the trip begins at JFK
		while (!stack.isEmpty()) {
			String src = stack.peek(); // AT: the airport the walk stands at
			Queue<String> neighbours = graph.get(src); // null: no ticket ever leaves src
			if (neighbours != null && !neighbours.isEmpty()) {
				// FOLLOW: the smallest unused ticket; poll REMOVES it -- that removal is the mark
				stack.push(neighbours.poll());
			} else {
				list.addFirst(stack.pop()); // RECORD: src is stuck -- all its tickets are used
			}
		}
		return list; // RETURN: airports in trip order
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
