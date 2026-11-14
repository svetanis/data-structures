package com.svetanis.datastructures.graph.bfs.bidirectional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 752. Open the Lock
//
// Input: dst -- the combination to reach; deadends -- combinations that jam the lock. The lock has
// 4 wheels, each showing a digit 0-9, and starts at "0000"; one move turns one wheel one slot, up
// or down, and 9 wraps to 0. Returns the least number of moves to reach dst without landing on a
// deadend, or -1. On LeetCode: 1 <= deadends.length <= 500, every deadend and the target is 4
// digits, target is not a deadend.
//
// The one idea: one queue entry holds one combination of the lock, and each end of the search has
// its own queue and its own map of "combination -> moves from my end". Start one search at "0000"
// and one at the target; always grow the side with fewer combinations waiting, one whole ring at a
// time. The moment a combination built by one side is already in the other side's map, the two
// halves of a path have met, and the answer is this side's moves to the parent, plus the step
// just taken, plus the other side's moves. Both ends are searched only half as deep, which is
// why this beats one search when the graph fans out. The backward search starts ON the target,
// so the target is the one combination it never filters; that is why it is tested before the
// search, as is the start.
//
// Time: O(10^d * d) -- d = 4 wheels, so at most 10,000 combinations are settled between the two
// searches, each building 8 neighbours of d characters
// Space: O(10^d) -- the two queues and the two maps
//
// Siblings: OpenLock is the one-way search; OpenLockFrontiers is the same meeting search with
// each end held as a set (one shared counter, no depth per combination); OpenLockRings is the
// one-way search counting rings; OpenLockSubmit is the LeetCode submission.
public final class OpenLockBidirectional {

	private Set<String> excluded = new HashSet<>();

	public int openLock(String dst, String[] deadends) {
		String src = "0000";
		// this call's deadends, not this call's plus every earlier call's
		excluded.clear();
		Collections.addAll(excluded, deadends);
		if (excluded.contains(src)) {
			return -1; // BASE: the start is jammed
		}
		if (src.equals(dst)) {
			return 0; // BASE: already open
		}
		// the backward search STARTS at dst, so dst is the one combination
		// it never filters. A jammed target can still be walked away from,
		// and the two halves then meet on a lock that never opens
		if (excluded.contains(dst)) {
			return -1; // BASE: the target is jammed
		}
		return bidirBfs(src, dst);
	}

	private int bidirBfs(String src, String dst) {
		Deque<String> sdq = new ArrayDeque<>();
		Deque<String> edq = new ArrayDeque<>();
		Map<String, Integer> smap = new HashMap<>();
		Map<String, Integer> emap = new HashMap<>();
		// initialize bfs from both ends
		sdq.offer(src);
		edq.offer(dst);
		smap.put(src, 0); // RECORD: each end is 0 moves from itself
		emap.put(dst, 0);

		while (!sdq.isEmpty() && !edq.isEmpty()) {
			// extend the smaller queue
			boolean smaller = sdq.size() <= edq.size();
			int turns = smaller ? bfs(sdq, smap, emap) : bfs(edq, emap, smap); // WALK: one ring of the smaller side
			if (turns != -1) {
				return turns; // RETURN: the two halves met
			}
		}
		return -1; // RETURN: one side ran dry, no route
	}

	private int bfs(Deque<String> dq, Map<String, Integer> fmap, Map<String, Integer> rmap) {
		int size = dq.size(); // frozen: exactly one ring is expanded per call
		for (int i = 0; i < size; i++) {
			String curr = dq.poll();
			int count = fmap.get(curr);
			List<String> combos = nextCombinations(curr);
			for (String combo : combos) {
				if (fmap.containsKey(combo)) {
					continue; // SKIP: this side already has it
				}
				if (rmap.containsKey(combo)) {
					int val = rmap.get(combo);
					return count + 1 + val; // RETURN: this side's moves + the step taken + the other side's moves
				}
				fmap.put(combo, count + 1); // RECORD: one move more than its parent
				dq.offer(combo);
			}
		}
		return -1; // no meeting in this ring
	}

	private List<String> nextCombinations(String s) {
		List<String> list = new ArrayList<>();
		char[] chars = s.toCharArray();
		for (int i = 0; i < 4; i++) { // WALK: each wheel in turn
			char c = chars[i];
			// next digit
			chars[i] = c == '9' ? '0' : (char) (c + 1);
			String next = new String(chars);
			if (!excluded.contains(next)) { // SKIP: a deadend
				list.add(next);
			}
			// prev digit
			chars[i] = c == '0' ? '9' : (char) (c - 1);
			String prev = new String(chars);
			if (!excluded.contains(prev)) { // SKIP: a deadend
				list.add(prev);
			}
			chars[i] = c; // restore the wheel
		}
		return list;
	}

	public static void main(String[] args) {
		OpenLockBidirectional olb = new OpenLockBidirectional();
		String[] deadends = { "0201", "0101", "0102", "1212", "2002" };
		System.out.println(olb.openLock("0202", deadends)); // 6

		OpenLockBidirectional olb1 = new OpenLockBidirectional();
		String[] deadends1 = { "8888" };
		System.out.println(olb1.openLock("0009", deadends1)); // 1

		OpenLockBidirectional olb2 = new OpenLockBidirectional();
		String[] deadends2 = { "8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888" };
		System.out.println(olb2.openLock("8888", deadends2)); // -1

		// the target itself is jammed. One turn away from the start, and
		// still unreachable -- the backward search begins ON it, so
		// nothing downstream ever tests it
		System.out.println(olb2.openLock("0001", new String[] { "0001" })); // -1

		// the start is jammed
		System.out.println(olb2.openLock("8888", new String[] { "0000" })); // -1

		// the SAME object, called a fifth time with no deadends at all.
		// Without the clear(), "0001" and "0000" are still excluded from
		// the four calls above and this answers -1 for a lock that opens
		System.out.println(olb2.openLock("0001", new String[0])); // 1
	}
}
