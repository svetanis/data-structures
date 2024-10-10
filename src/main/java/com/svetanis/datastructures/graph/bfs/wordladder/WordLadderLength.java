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

	private static int bfs(String src, String dst, Set<String> words) {
		boolean isLarge = largeDictionary(src, words);
		Queue<Node> queue = newLinkedList();
		queue.add(new Node(src, 1));
		words.add(dst);

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
		System.out.println(ladderLen("hit", "cog", set));
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
