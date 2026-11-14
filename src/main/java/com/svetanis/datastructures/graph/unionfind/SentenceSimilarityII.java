package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 737. Sentence Similarity II
//
// Input: sentence1 and sentence2 -- arrays of words; similarPairs -- pairs [x, y] of words that
// are similar. Similarity is symmetric and passes along: x ~ y and y ~ z make x ~ z. Returns true
// when the sentences have the same length and, at every position, the two words are equal or
// similar. On LeetCode: 1 <= sentence1.length, sentence2.length <= 1000, 1 <= word length <= 20,
// 0 <= similarPairs.length <= 2000, similarPairs[i].length == 2, letters only.
//
// The one idea: words are the nodes and each pair is a wire between two words; "similar" means
// "in the same group". A union-find needs numbers, so every distinct word in the pairs gets an id
// (a map word -> id); one element is then one word. Two different words are similar exactly when
// both appear in the pairs and have the same root. Equal words need no lookup at all.
//
// Time: O((p + s) * L) -- p pairs and s sentence positions, each with hashing and comparing words of
// length L; the finds are near-constant after compression.
// Space: O(p * L) -- the word -> id map and the parent array of 2 * p slots.

public final class SentenceSimilarityII {

	private int[] parent;

	public boolean areSimilar(String[] snt1, String[] snt2, List<List<String>> pairs) {
		if (snt1.length != snt2.length) {
			return false;
		}
		int n = pairs.size();
		this.parent = init(2 * n); // at most two new words per pair
		Map<String, Integer> map = union(pairs); // JOIN: the words of every pair
		for (int i = 0; i < snt1.length; i++) {
			String s1 = snt1[i];
			String s2 = snt2[i];
			if (s1.equals(s2)) {
				continue; // SKIP: equal words are always similar
			}
			boolean one = map.containsKey(s1);
			boolean two = map.containsKey(s2);
			if (!one || !two || find(map.get(s1)) != find(map.get(s2))) {
				return false; // a word with no pair, or two different groups
			}
		}
		return true;
	}

	private Map<String, Integer> union(List<List<String>> pairs) {
		int index = 0;
		Map<String, Integer> map = new HashMap<>();
		for (List<String> pair : pairs) {
			String w1 = pair.get(0);
			String w2 = pair.get(1);
			if (!map.containsKey(w1)) {
				map.put(w1, index++); // NUMBER: the next unused id
			}
			if (!map.containsKey(w2)) {
				map.put(w2, index++);
			}
			int p1 = find(map.get(w1));
			int p2 = find(map.get(w2));
			parent[p1] = p2; // JOIN the two groups
		}
		return map;
	}

	private int[] init(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = i;
		}
		return a;
	}

	public int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y); // WALK up to the root
			parent[x] = y; // COMPRESS: point straight at it
		}
		return y;
	}

	public static void main(String[] args) {
		String[] s1 = { "great", "acting", "skills" };
		String[] s2 = { "fine", "drama", "talent" };
		List<List<String>> pairs = new ArrayList<>();
		pairs.add(Arrays.asList("great", "good"));
		pairs.add(Arrays.asList("good", "fine"));
		pairs.add(Arrays.asList("acting", "drama"));
		pairs.add(Arrays.asList("skills", "talent"));
		SentenceSimilarityII ss = new SentenceSimilarityII();
		System.out.println(ss.areSimilar(s1, s2, pairs)); // true -- great ~ good ~ fine, and the others pair up
	}
}
