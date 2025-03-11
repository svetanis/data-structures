package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 737. Sentence Similarity II

public final class SentenceSimilarityII {

	private int[] parent;

	public boolean areSimilar(String[] snt1, String[] snt2, List<List<String>> pairs) {
		if (snt1.length != snt2.length) {
			return false;
		}
		int n = pairs.size();
		this.parent = init(2 * n);
		Map<String, Integer> map = union(pairs);
		for (int i = 0; i < snt1.length; i++) {
			String s1 = snt1[i];
			String s2 = snt2[i];
			if (s1.equals(s2)) {
				continue;
			}
			boolean one = map.containsKey(s1);
			boolean two = map.containsKey(s2);
			if (!one || !two || find(map.get(s1)) != find(map.get(s2))) {
				return false;
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
				map.put(w1, index++);
			}
			if (!map.containsKey(w2)) {
				map.put(w2, index++);
			}
			int p1 = find(map.get(w1));
			int p2 = find(map.get(w2));
			parent[p1] = p2;
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
			y = find(y);
			parent[x] = y;
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
		System.out.println(ss.areSimilar(s1, s2, pairs)); // true
	}
}
