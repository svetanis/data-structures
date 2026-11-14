package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.svetanis.datastructures.graph.bfs.wordladder.WordLadderUtil.largeDictionary;
import static com.svetanis.datastructures.graph.bfs.wordladder.WordLadderUtil.neighbors;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// Word Ladder, returning the ladder itself (not a LeetCode problem; it is 127 Word Ladder and
// 126 Word Ladder II with one shortest ladder returned instead of its length).
//
// Input: src, dst -- two words of the same length; words -- the dictionary. A ladder changes one
// letter at a time, and every word on it must be in the dictionary. Returns one shortest ladder
// from src to dst, both ends included, or an empty list when there is none. The end word is
// added to the dictionary, so it does not have to be in it; src and dst are expected to differ
// (if they are equal and the dictionary is not empty, the result is a round trip src, a
// neighbour, src). The words set is changed: dst is added and every word reached is removed.
//
// The one idea: the words are the places and one changed letter is a step, so the shortest ladder
// is the fewest steps, which a breadth-first search finds. A queue entry is a Node: one word,
// its step count, and the Node it was reached from (prev). Removing a word from the set when it
// is queued is the visited mark, so each word is queued once. The first time a neighbour equals
// dst, the ladder is read back by following prev from the current Node to src.
//
// Time: O(n * min(26 * m * m, n * m)) -- n dictionary words, each queued once and expanded by
// the cheaper way of WordLadderUtil (m is the word length).
// Space: O(n * m) -- the queue, the Nodes and the dictionary.
//
// Siblings: WordLadderLength returns only the length (LeetCode 127) and does not change the
// caller's dictionary; WordLadder126 returns all the shortest ladders.

public final class WordLadder {

	public static List<String> ladder(String src, String dst, Set<String> words) {
		if (src.equals(dst) && words.isEmpty()) {
			return new ArrayList<>(); // BASE: nothing to climb
		}
		boolean isLarge = largeDictionary(src, words);
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(new Node(src, 0, null));
		words.add(dst); // the end word is always a legal last step
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			String word = node.word;
			for (String neighbor : neighbors(word, words, isLarge)) { // WALK: one letter away
				if (neighbor.equals(dst)) { // RETURN: the first arrival is the shortest ladder
					return path(dst, node);
				}
				if (words.contains(neighbor)) { // SKIP: already queued
					queue.add(new Node(neighbor, node.dist + 1, node));
					words.remove(neighbor); // RECORD: queued, so never queued again
				}
			}
		}
		return new ArrayList<>(); // RETURN: no ladder
	}

	private static List<String> path(String dst, Node node) {
		List<String> list = new ArrayList<>();
		list.add(dst);
		Node parent = node;
		while (parent != null) { // WALK: back along prev to src
			list.add(0, parent.word); // RECORD: put each word in front
			parent = parent.prev;
		}
		return list;
	}

	public static void main(String[] args) {
		Set<String> set = new HashSet<>(Arrays.asList("cccw", "accc", "accw"));
		System.out.println(ladder("cccc", "cccc", set)); // [cccc, cccw, cccc]

		Set<String> set1 = new HashSet<>(Arrays.asList("hit", "hot", "dot", "dog", "dog", "cog"));
		System.out.println(ladder("hit", "cog", set1)); // [hit, hot, dot, dog, cog]

		Set<String> set2 = new HashSet<>(Arrays.asList("fool", "pool", "poll", "pole", "pale", "sale", "sage"));
		System.out.println(ladder("fool", "sage", set2)); // [fool, pool, poll, pole, pale, sale, sage]

		Set<String> set3 = new HashSet<>(Arrays.asList("cold", "gold", "cord", "sold", "card", "ward", "warm", "tard"));
		System.out.println(ladder("cold", "warm", set3)); // [cold, cord, card, ward, warm]
	}

	private static class Node {
		private int dist;
		private String word;
		private Node prev;

		public Node(String word, int dist, Node prev) {
			this.word = word;
			this.dist = dist;
			this.prev = prev;
		}
	}

}
