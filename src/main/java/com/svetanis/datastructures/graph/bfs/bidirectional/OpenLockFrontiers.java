package com.svetanis.datastructures.graph.bfs.bidirectional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// 752. Open the Lock

// The same meet-in-the-middle search as OpenLockBidirectional, holding each
// frontier as a SET rather than as a queue plus a map. A whole level is
// replaced at once, so the set IS the level and no size snapshot is needed;
// and because every turn advances one full level of one side, a single shared
// counter already holds the total number of turns when the two sides touch.
// OpenLockBidirectional makes the other choice -- a depth per code -- and so
// has to add three numbers at the meeting: its own depth, the step just taken,
// and the other side's depth
public final class OpenLockFrontiers {

	private static final String SRC = "0000";

	// Time Complexity: O(n^d * d * 10) over reachable codes; d = 4 wheels
	// Space Complexity: O(n^d) -- capped at the 10,000 codes that exist
	public int openLock(String dst, String[] deadends) {
		Set<String> excluded = new HashSet<>();
		Collections.addAll(excluded, deadends);

		// every neighbour is filtered inside neighbors(), so the codes no
		// filter ever sees are the ones a search STARTS on. A one-way search
		// has one of those and needs one guard; this has two
		if (excluded.contains(SRC) || excluded.contains(dst)) {
			return -1;
		}
		if (SRC.equals(dst)) {
			return 0;
		}
		return bidirBfs(dst, excluded);
	}

	private int bidirBfs(String dst, Set<String> excluded) {
		Set<String> begin = new HashSet<>();
		Set<String> end = new HashSet<>();
		Set<String> visited = new HashSet<>();
		begin.add(SRC);
		end.add(dst);
		visited.add(SRC);
		visited.add(dst);

		int moves = 0;
		// a level that comes back empty means that side can go no further
		while (!begin.isEmpty() && !end.isEmpty()) {
			if (begin.size() > end.size()) {
				// take turns by always expanding through `begin`. On this
				// graph every code has eight neighbours, so the two sides
				// stay the same size and this reduces to plain alternation --
				// it earns its keep where the degrees differ, as in a word
				// ladder
				Set<String> swap = begin;
				begin = end;
				end = swap;
			}
			moves++;
			begin = expand(begin, end, visited, excluded);
			if (begin == null) {
				return moves;
			}
		}
		return -1;
	}

	// returns the next level, or null once a generated code is found sitting
	// in the other side's level -- at which point `moves` is already the
	// answer, because each side has walked one half of the same path
	//
	// `visited` here buys SPEED, not correctness. Drop the marking and the
	// answers are unchanged -- re-added codes cannot forge a shorter path,
	// because the other side's frontier is a superset in the same way -- but
	// reaching "5555" generates 95,922 neighbours instead of 59,922. That is
	// unlike every one-way search in this folder, where losing the mark loses
	// the answer or the termination
	private Set<String> expand(Set<String> begin, Set<String> end, Set<String> visited,
			Set<String> excluded) {
		// built separately because `begin` is being iterated
		Set<String> next = new HashSet<>();
		for (String code : begin) {
			for (String neighbor : neighbors(code, excluded)) {
				if (end.contains(neighbor)) {
					return null;
				}
				if (visited.add(neighbor)) {
					next.add(neighbor);
				}
			}
		}
		return next;
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
		OpenLockFrontiers olf = new OpenLockFrontiers();

		String[] deadends = { "0201", "0101", "0102", "1212", "2002" };
		System.out.println(olf.openLock("0202", deadends)); // 6

		System.out.println(olf.openLock("0009", new String[] { "8888" })); // 1

		String[] boxed = { "8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888" };
		System.out.println(olf.openLock("8888", boxed)); // -1

		// the target itself is jammed. The backward search STARTS on it, so
		// no filter downstream ever tests it -- a one-way search never
		// generates it and gets this right without a guard
		System.out.println(olf.openLock("0001", new String[] { "0001" })); // -1

		// the start is jammed
		System.out.println(olf.openLock("8888", new String[] { "0000" })); // -1

		// already open. Checked before the loop, because the meeting test
		// only ever looks at a code's neighbours and "0000" is not its own
		System.out.println(olf.openLock("0000", new String[] { "1000" })); // 0

		// the furthest target: every wheel five slots away. Both this and a
		// one-way search settle all 10,000 codes here, so meeting in the
		// middle saves nothing at the extreme
		System.out.println(olf.openLock("5555", new String[0])); // 20

		// no shared state between calls -- `excluded` is a local, so the
		// deadends above cannot leak into this one
		System.out.println(olf.openLock("0001", new String[0])); // 1
	}
}
