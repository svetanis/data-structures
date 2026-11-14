package com.svetanis.datastructures.graph.bfs.wordladder;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

// 433. Minimum Genetic Mutation
//
// Input: src and dst -- two gene strings of 8 letters from 'A', 'C', 'G', 'T'; bank -- the valid
// genes. One mutation changes one letter, and the new gene must be in the bank. The start gene
// is assumed valid, so it need not be in the bank. Returns the fewest mutations from src to dst,
// or an empty Optional when there is no way (LeetCode returns -1). On LeetCode: the genes have
// exactly 8 letters from "ACGT", 0 <= bank.length <= 10.
//
// The one idea: the genes are the places and one mutation is a step, so the fewest mutations is
// the fewest steps, which a breadth-first search finds. A queue entry is one gene; map says how
// many mutations it took to reach that gene, and doubles as the visited mark. The neighbours of
// a gene are made, not looked up: change each of the 8 letters to each of the 3 other letters
// and keep the results that are in the bank. The first time dst comes off the queue is by the
// fewest mutations, because genes leave the queue in order of their count.
//
// Time: O(B * 8 * 3 * 8) -- each of the B bank genes is queued once; it makes 8 * 3 candidates,
// each an 8-letter string built and looked up.
// Space: O(B) -- the map, the queue and the bank.
//
// Siblings: bfs2 (private, unused) is the same search that carries the count in the queue entry
// and uses the bank itself as the visited set, removing each gene as it is queued, which empties
// the caller's bank.

public final class MinGeneticMutation {

	private static final Map<Character, String> GENE = genes();

	public static Optional<Integer> mgm(String src, String dst, Set<String> bank) {
		return bfs(src, dst, bank);
	}

	private static Optional<Integer> bfs(String src, String dst, Set<String> bank) {
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> map = new HashMap<>(); // gene -> mutations to reach it
		queue.add(src);
		map.put(src, 0); // BASE: src is 0 mutations from itself
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			if (curr.equals(dst)) { // RETURN: the first arrival is the fewest mutations
				return Optional.of(map.get(curr));
			}
			for (String neighbor : neighbors(curr, bank)) {
				if (!map.containsKey(neighbor)) { // SKIP: already reached
					queue.add(neighbor);
					map.put(neighbor, map.get(curr) + 1); // RECORD: one mutation farther than curr
				}
			}
		}
		return Optional.empty(); // RETURN: dst cannot be reached
	}

	private static Optional<Integer> bfs2(String src, String dst, Set<String> bank) {
		Queue<SimpleEntry<String, Integer>> queue = new ArrayDeque<>();
		queue.add(new SimpleEntry<>(src, 0));
		while (!queue.isEmpty()) {
			SimpleEntry<String, Integer> pair = queue.poll();
			String curr = pair.getKey();
			int step = pair.getValue();
			if (curr.equals(dst)) { // RETURN: the first arrival is the fewest mutations
				return Optional.of(step);
			}
			for (String neighbor : neighbors(curr, bank)) {
				if (bank.contains(neighbor)) { // TAKE: still in the bank, so not queued before
					queue.add(new SimpleEntry<>(neighbor, step + 1));
					bank.remove(neighbor); // RECORD: queued, so never queued again
				}
			}
		}
		return Optional.empty();
	}

	private static List<String> neighbors(String src, Set<String> bank) {
		List<String> out = new ArrayList<>();
		char[] letters = src.toCharArray(); // a char[] can be changed; a String cannot
		for (int pos = 0; pos < letters.length; pos++) { // WALK: each letter of the gene
			char letter = letters[pos]; // SAVE: the letter now at pos
			for (char mutation : GENE.get(letter).toCharArray()) { // WALK: each of the 3 other letters
				letters[pos] = mutation; // SWAP
				String mutated = new String(letters);
				if (bank.contains(mutated)) { // TAKE: only genes in the bank
					out.add(mutated);
				}
			}
			letters[pos] = letter; // RESTORE: before the next position
		}
		return out;
	}

	private static Map<Character, String> genes() {
		Map<Character, String> map = new HashMap<>();
		map.put('A', "CGT"); // the 3 letters other than the key
		map.put('C', "AGT");
		map.put('G', "ACT");
		map.put('T', "ACG");
		return Collections.unmodifiableMap(map);
	}

	public static void main(String[] args) {
		Set<String> set1 = new HashSet<>(Arrays.asList("AACCGGTA"));
		System.out.println(mgm("AACCGGTT", "AACCGGTA", set1)); // Optional[1]

		Set<String> set2 = new HashSet<>(Arrays.asList("AACCGGTA", "AACCGCTA", "AAACGGTA"));
		System.out.println(mgm("AACCGGTT", "AAACGGTA", set2)); // Optional[2]

		Set<String> set3 = new HashSet<>(Arrays.asList("AACCGGTA", "AACCGGTC", "AACCGGCT"));
		System.out.println(mgm("AACCGGTT", "AACCGGTA", set3)); // Optional[1]

		Set<String> set4 = new HashSet<>(Arrays.asList("AACCGGTA"));
		System.out.println(mgm("AACCGGTT", "AACCGGTC", set4)); // Optional.empty
	}
}
