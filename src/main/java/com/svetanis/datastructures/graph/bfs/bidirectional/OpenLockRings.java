package com.svetanis.datastructures.graph.bfs.bidirectional;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

// 752. Open the Lock

// One-way, counting rings: a frozen size marks each ring's edge and one shared
// `moves` counter advances at the ring boundary. OpenLock.java solves the same
// problem the other way, giving every code its own depth in a map -- so it
// needs no ring loop at all, and reads the answer back out of the map instead
// of off a counter. Both are correct; this one keeps the shape used everywhere
// else in graph/bfs, and needs one structure fewer
public final class OpenLockRings {

	private static final String SRC = "0000";

	// Time Complexity: O(10^d * d) -- d = 4 wheels, so at most 10,000 codes
	// Space Complexity: O(10^d)
	public int openLock(String dst, String[] deadends) {
		Set<String> excluded = new HashSet<>();
		Collections.addAll(excluded, deadends);

		// every neighbour is filtered inside neighbors(), so the one code that
		// filter never sees is the one the search starts on. A jammed target
		// needs no guard here: it is simply never generated, so the search
		// runs out and answers -1 on its own. The two-ended version in
		// OpenLockFrontiers cannot rely on that, because it seeds the target
		if (excluded.contains(SRC)) {
			return -1;
		}
		return bfs(dst, excluded);
	}

	private int bfs(String dst, Set<String> excluded) {
		Set<String> visited = new HashSet<>();
		Deque<String> queue = new ArrayDeque<>();
		queue.offer(SRC);
		visited.add(SRC);

		int moves = 0;
		while (!queue.isEmpty()) {
			// frozen, because `moves` outside the loop counts rings. Written
			// as "i < queue.size()" the bound grows as children are offered
			// and the ring boundary lands in the wrong place
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				String code = queue.poll();
				// tested on the POLLED code, whose distance is exactly `moves`.
				// Testing each neighbour instead costs a + 1 and a separate
				// case for the target being the start
				if (code.equals(dst)) {
					return moves;
				}
				for (String neighbor : neighbors(code, excluded)) {
					// asks and marks in one call, so the two cannot drift
					if (visited.add(neighbor)) {
						queue.offer(neighbor);
					}
				}
			}
			moves++;
		}
		return -1;
	}

	// the eight codes one turn away, minus the deadends. Nothing holds this
	// graph anywhere -- these strings do not exist until they are built here
	private Set<String> neighbors(String src, Set<String> excluded) {
		Set<String> set = new HashSet<>();
		char[] chars = src.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char old = chars[i];
			int digit = old - '0';
			// + 10 before the %, because Java's % keeps the sign of its left
			// operand: (0 - 1) % 10 is -1, and '0' + -1 is the character '/'
			chars[i] = (char) ('0' + (digit - 1 + 10) % 10);
			addIfAllowed(set, new String(chars), excluded);
			chars[i] = (char) ('0' + (digit + 1) % 10);
			addIfAllowed(set, new String(chars), excluded);
			chars[i] = old;
		}
		return set;
	}

	private void addIfAllowed(Set<String> set, String combo, Set<String> excluded) {
		if (!excluded.contains(combo)) {
			set.add(combo);
		}
	}

	public static void main(String[] args) {
		OpenLockRings olr = new OpenLockRings();

		String[] deadends = { "0201", "0101", "0102", "1212", "2002" };
		System.out.println(olr.openLock("0202", deadends)); // 6

		System.out.println(olr.openLock("0009", new String[] { "8888" })); // 1

		String[] boxed = { "8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888" };
		System.out.println(olr.openLock("8888", boxed)); // -1

		// the target is jammed. No guard for it anywhere above -- it is never
		// generated, so the queue drains and the answer falls out
		System.out.println(olr.openLock("0001", new String[] { "0001" })); // -1

		// the start is jammed. This one DOES need its guard
		System.out.println(olr.openLock("8888", new String[] { "0000" })); // -1

		// already open. Free, because the test is on the polled code and the
		// start is polled first, at moves = 0
		System.out.println(olr.openLock("0000", new String[] { "1000" })); // 0

		// two rings deep, so the ring boundary has to land in the right place
		System.out.println(olr.openLock("0002", new String[0])); // 2

		// the furthest target: every wheel five slots away
		System.out.println(olr.openLock("5555", new String[0])); // 20

		// no shared state between calls -- `excluded` is a local
		System.out.println(olr.openLock("0001", new String[0])); // 1
	}
}
