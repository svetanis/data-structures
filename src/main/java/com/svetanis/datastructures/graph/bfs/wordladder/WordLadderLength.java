package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.datastructures.graph.bfs.wordladder.WordLadderUtil.largeDictionary;
import static com.svetanis.datastructures.graph.bfs.wordladder.WordLadderUtil.neighbors;

import java.util.Queue;
import java.util.Set;

public final class WordLadderLength {

	public static int ladderLen(String src, String dst, Set<String> words) {
		return bfs(src, dst, words);
	}

	private static int bfs(String src, String dst, Set<String> dictionary) {
		// the BFS marks visited by REMOVING from this set, and adds dst to
		// it -- so it must own the set. O(n) against an O(n * L * 26) walk
		Set<String> words = newHashSet(dictionary);
		// the end word has to BE in the dictionary. Adding it here instead
		// builds a ladder onto a word nobody offered, and the last step of
		// that ladder is a word transformation the problem never allowed
		if (!words.contains(dst)) {
			return 0;
		}
		boolean isLarge = largeDictionary(src, words);
		Queue<Node> queue = newLinkedList();
		queue.add(new Node(src, 1));

		while (!queue.isEmpty()) {
			Node node = queue.poll();
			String word = node.word;
			if (word.equals(dst)) {
				return node.len;
			}
			for (String neighbor : neighbors(word, words, isLarge)) {
				if (words.contains(neighbor)) {
					queue.add(new Node(neighbor, node.len + 1));
					words.remove(neighbor);
				}
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		Set<String> set = newHashSet("hit", "hot", "dot", "dog", "dog", "cog");
		System.out.println(ladderLen("hit", "cog", set)); // 5

		// the same dictionary with the end word taken out. There is no
		// ladder, because "cog" is not a word you are allowed to reach
		Set<String> noEnd = newHashSet("hit", "hot", "dot", "dog");
		System.out.println(ladderLen("hit", "cog", noEnd)); // 0

		// the dictionary is the caller's, and it comes back untouched
		System.out.println(set.size()); // 5
	}

	private static class Node {
		private int len;
		private String word;

		public Node(String word, int len) {
			this.word = word;
			this.len = len;
		}
	}
}
