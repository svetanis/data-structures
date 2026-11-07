package com.svetanis.datastructures.hashmap;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 288. Unique Word Abbreviation
//
// A word's abbreviation is its first letter, the number of letters between, and its last
// letter ("dog" -> "d1g"); a word of 2 letters or fewer is its own abbreviation. The
// constructor takes a dictionary of words. isUnique(word) returns true if no dictionary word
// has word's abbreviation, or if every dictionary word with it is word itself. On LeetCode:
// 1 <= dictionary.length <= 3 * 10^4, words of 1 to 20 lowercase letters, at most 5000 calls.
//
// Group the dictionary by abbreviation: a map from each abbreviation to the SET of distinct
// words that have it. Then isUnique is one lookup: no words, or exactly one word and it is
// word itself. The set matters: a dictionary listing "cake" twice still has only one word
// with "c2e", so "cake" stays unique.
//
// Siblings, same problem: none in this package.

public final class UniqueWordAbbreviation {
	// Time complexity: constructor O(n * k), n words of up to k letters; isUnique O(k)
	// Auxiliary Space: O(n * k), every distinct dictionary word kept once

	public UniqueWordAbbreviation(List<String> list) {
		this.byAbbreviation = dictionary(list);
	}

	private final Map<String, Set<String>> byAbbreviation; // abbreviation -> the distinct words with it

	public boolean isUnique(String word) {
		String abbreviation = abbreviation(word);
		Set<String> set = byAbbreviation.getOrDefault(abbreviation, Set.of()); // FIND: the words that share it
		return set.isEmpty() || set.size() == 1 && set.contains(word); // ONLY: none, or word itself
	}

	private Map<String, Set<String>> dictionary(List<String> list) {
		Map<String, Set<String>> map = new HashMap<>();
		for (String word : list) {
			map.computeIfAbsent(abbreviation(word), a -> new HashSet<>()).add(word); // GROUP: by abbreviation
		}
		return map;
	}

	private String abbreviation(String s) {
		int len = s.length();
		if (len < 3) { // SHORT: its own abbreviation
			return s;
		}
		char first = s.charAt(0);
		char last = s.charAt(len - 1);
		String between = Integer.toString(len - 2);
		return first + between + last;
	}

	public static void main(String[] args) {
		List<String> list = asList("deer", "door", "cake", "card");
		UniqueWordAbbreviation uwa = new UniqueWordAbbreviation(list);
		System.out.println(uwa.isUnique("dear")); // false
		System.out.println(uwa.isUnique("cart")); // true
		System.out.println(uwa.isUnique("cane")); // false
		System.out.println(uwa.isUnique("make")); // true
		System.out.println(uwa.isUnique("cake")); // true
	}
}
