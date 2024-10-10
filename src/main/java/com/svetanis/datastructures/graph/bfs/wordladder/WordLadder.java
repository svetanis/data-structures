package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.datastructures.graph.bfs.wordladder.WordLadderUtil.largeDictionary;
import static com.svetanis.datastructures.graph.bfs.wordladder.WordLadderUtil.neighbors;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.ImmutableList;

public final class WordLadder {

	public static ImmutableList<String> ladder(String src, String dst, Set<String> words) {
		if (src.equals(dst) && words.isEmpty()) {
			return newList();
		}
		boolean isLarge = largeDictionary(src, words);
		Queue<Node> queue = newLinkedList();
		queue.add(new Node(src, 0, null));
		words.add(dst);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			String word = node.word;
			for (String neighbor : neighbors(word, words, isLarge)) {
				if (neighbor.equals(dst)) {
					return path(dst, node);
				}
				if (words.contains(neighbor)) {
					queue.add(new Node(neighbor, node.dist + 1, node));
					words.remove(neighbor);
				}
			}
		}
		return newList();
	}

	private static ImmutableList<String> path(String dst, Node node) {
		List<String> list = newArrayList();
		list.add(dst);
		Node parent = node;
		while (parent != null) {
			list.add(0, parent.word);
			parent = parent.prev;
		}
		return newList(list);
	}

	public static void main(String[] args) {
		Set<String> set = newHashSet("cccw", "accc", "accw");
		print(ladder("cccc", "cccc", set)); // cccc cccw cccc

		Set<String> set1 = newHashSet("hit", "hot", "dot", "dog", "dog", "cog");
		print(ladder("hit", "cog", set1)); // hit hot dot dog cog

		Set<String> set2 = newHashSet("fool", "pool", "poll", "pole", "pale", "sale", "sage");
		print(ladder("fool", "sage", set2)); // fool pool poll pole pale sale sage

		Set<String> set3 = newHashSet("cold", "gold", "cord", "sold", "card", "ward", "warm", "tard");
		print(ladder("cold", "warm", set3)); // cold cord card ward warm
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
