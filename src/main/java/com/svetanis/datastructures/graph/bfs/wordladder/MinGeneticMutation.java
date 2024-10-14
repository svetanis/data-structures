package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Maps.newMap;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.svetanis.java.base.Pair;

// 433. Minimum Genetic Mutation
// a gene string can be represented by an
// 8-char long string, with choices from
// 'A', 'C', 'G', and 'T'.
// there is also a gene bank that records
// all the valid gene mutations. a gene
// must be in bank to make it a valid gene string.
// given the two gene strings src and dst and
// the gene bank, return the min number of 
// mutations needed to mutate from src to dst.

// note that the src point is assumed to be valid,
// so it might not be included in the bank

public final class MinGeneticMutation {
	// Time Complexity: O(n * m)

	private static final ImmutableMap<Character, String> GENE = genes();

	public static Optional<Integer> mgm(String src, String dst, Set<String> bank) {
		return bfs(src, dst, bank);
	}

	private static Optional<Integer> bfs(String src, String dst, Set<String> bank) {
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> map = newHashMap();
		queue.add(src);
		map.put(src, 0);
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			if (curr.equals(dst)) {
				return of(map.get(curr));
			}
			for (String neighbor : neighbors(curr, bank)) {
				if (!map.containsKey(neighbor)) {
					queue.add(neighbor);
					map.put(neighbor, map.get(curr) + 1);
				}
			}
		}
		return absent();
	}

	private static Optional<Integer> bfs2(String src, String dst, Set<String> bank) {
		Queue<Pair<String, Integer>> queue = new ArrayDeque<>();
		queue.add(Pair.build(src, 0));
		while (!queue.isEmpty()) {
			Pair<String, Integer> pair = queue.poll();
			String curr = pair.getLeft();
			int step = pair.getRight();
			if (curr.equals(dst)) {
				return of(step);
			}
			for (String neighbor : neighbors(curr, bank)) {
				if (bank.contains(neighbor)) {
					queue.add(Pair.build(neighbor, step + 1));
					bank.remove(neighbor);
				}
			}
		}
		return absent();
	}

	private static ImmutableList<String> neighbors(String src, Set<String> bank) {
		Set<String> set = newHashSet();
		for (int i = 0; i < src.length(); i++) {
			char curr = src.charAt(i);
			for (char mutation : GENE.get(curr).toCharArray()) {
				String mutated = src.substring(0, i) + mutation + src.substring(i + 1);
				if (!src.equals(mutated) && bank.contains(mutated)) {
					set.add(mutated);
				}
			}
		}
		return newList(set);
	}

	private static ImmutableMap<Character, String> genes() {
		Map<Character, String> map = newHashMap();
		map.put('A', "CGT");
		map.put('C', "AGT");
		map.put('G', "ACT");
		map.put('T', "ACG");
		return newMap(map);
	}

	public static void main(String[] args) {
		Set<String> set1 = newHashSet("AACCGGTA");
		System.out.println(mgm("AACCGGTT", "AACCGGTA", set1)); // 1

		Set<String> set2 = newHashSet("AACCGGTA", "AACCGCTA", "AAACGGTA");
		System.out.println(mgm("AACCGGTT", "AAACGGTA", set2)); // 2

		Set<String> set3 = newHashSet("AACCGGTA", "AACCGGTC", "AACCGGCT");
		System.out.println(mgm("AACCGGTT", "AACCGGTA", set3)); // 1
	}
}
