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

public final class OpenLockBidirectional {

	private Set<String> excluded = new HashSet<>();

	public int openLock(String dst, String[] deadends) {
		String src = "0000";
		Collections.addAll(excluded, deadends);
		if (excluded.contains(src)) {
			return -1;
		}
		if (src.equals(dst)) {
			return 0;
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
		smap.put(src, 0);
		emap.put(dst, 0);

		while (!sdq.isEmpty() && !edq.isEmpty()) {
			// extend the smaller queue
			boolean smaller = sdq.size() <= edq.size();
			int turns = smaller ? bfs(sdq, smap, emap) : bfs(edq, emap, smap);
			if (turns != -1) {
				return turns;
			}
		}
		return -1;
	}

	private int bfs(Deque<String> dq, Map<String, Integer> fmap, Map<String, Integer> rmap) {
		int size = dq.size();
		for (int i = 0; i < size; i++) {
			String curr = dq.poll();
			int count = fmap.get(curr);
			List<String> combos = nextCombinations(curr);
			for (String combo : combos) {
				if (fmap.containsKey(combo)) {
					continue;
				}
				if (rmap.containsKey(combo)) {
					int val = rmap.get(combo);
					return count + 1 + val;
				}
				fmap.put(combo, count + 1);
				dq.offer(combo);
			}
		}
		return -1;
	}

	private List<String> nextCombinations(String s) {
		List<String> list = new ArrayList<>();
		char[] chars = s.toCharArray();
		for (int i = 0; i < 4; i++) {
			char c = chars[i];
			// next digit
			chars[i] = c == '9' ? '0' : (char) (c + 1);
			String next = new String(chars);
			if (!excluded.contains(next)) {
				list.add(next);
			}
			// prev digit
			chars[i] = c == '0' ? '9' : (char) (c - 1);
			String prev = new String(chars);
			if (!excluded.contains(prev)) {
				list.add(prev);
			}
			chars[i] = c;
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
	}
}
