package com.svetanis.datastructures.tree.trie;

import java.util.ArrayList;
import java.util.List;

// 616. Add Bold Tag in String

public final class AddBoldTag {

	public static String addBoldTag(String s, String[] words) {
		Trie root = init(words);
		List<int[]> intervals = intervals(s, root);
		if (intervals.isEmpty()) {
			return s;
		}
		List<int[]> merged = merge(intervals);
		return addTags(s, merged);
	}

	private static String addTags(String s, List<int[]> intervals) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		int interval = 0;
		int n = s.length();
		while (index < n) {
			if (interval == intervals.size()) {
				sb.append(s.substring(index));
				break;
			}
			int start = intervals.get(interval)[0];
			int end = intervals.get(interval)[1];
			if (index < start) {
				sb.append(s.subSequence(index, start));
			}
			interval++;
			sb.append("<b>");
			sb.append(s.substring(start, end + 1));
			sb.append("</b>");
			index = end + 1;
		}
		return sb.toString();
	}

	private static List<int[]> merge(List<int[]> intervals) {
		List<int[]> list = new ArrayList<>();
		int start = intervals.get(0)[0];
		int end = intervals.get(0)[1];
		for (int i = 1; i < intervals.size(); i++) {
			int left = intervals.get(i)[0];
			int right = intervals.get(i)[1];
			if (end + 1 < left) {
				list.add(new int[] { start, end });
				start = left;
				end = right;
			} else {
				end = Math.max(end, right);
			}
		}
		list.add(new int[] { start, end });
		return list;
	}

	private static List<int[]> intervals(String s, Trie root) {
		int n = s.length();
		List<int[]> intervals = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			Trie node = root;
			for (int j = i; j < n; j++) {
				char c = s.charAt(j);
				if (node.children[c] == null) {
					break;
				}
				node = node.children[c];
				if (node.endOfWord) {
					intervals.add(new int[] { i, j });
				}
			}
		}
		return intervals;
	}

	private static Trie init(String[] words) {
		Trie root = new Trie();
		for (String word : words) {
			root.insert(word);
		}
		return root;
	}

	public static void main(String[] args) {
		String[] words = { "abc", "123" };
		System.out.println(addBoldTag("abcxyz123", words));
	}

	private static class Trie {
		private boolean endOfWord;
		private Trie[] children = new Trie[128];

		public void insert(String word) {
			Trie node = this;
			for (char c : word.toCharArray()) {
				if (node.children[c] == null) {
					node.children[c] = new Trie();
				}
				node = node.children[c];
			}
			node.endOfWord = true;
		}
	}
}
