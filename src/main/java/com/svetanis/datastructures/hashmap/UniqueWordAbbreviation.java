package com.svetanis.datastructures.hashmap;

import static com.google.common.base.Functions.identity;
import static com.google.common.collect.Multimaps.invertFrom;
import static com.svetanis.java.base.collect.Multimaps.asMultimap;
import static com.svetanis.java.base.collect.Sets.newSet;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

// 288. Unique Word Abbreviation

public final class UniqueWordAbbreviation {

	public UniqueWordAbbreviation(List<String> list) {
		this.multi = dictionary(list);
	}

	private final Multimap<String, String> multi;

	public boolean isUnique(String word) {
		// Time Complexity: O(k), k is word length
		String abbreviation = abbreviation(word);
		Set<String> set = newSet(multi.get(abbreviation));
		return set.isEmpty() || set.size() == 1 && set.contains(word);
	}

	private Multimap<String, String> dictionary(List<String> list) {
		// Time Complexity: O(n * k), n is number of words, k is word length
		Function<String, String> values = s -> abbreviation(s);
		Multimap<String, String> multi = asMultimap(list, identity(), values);
		return invertFrom(multi, HashMultimap.create());
	}

	private String abbreviation(String s) {
		int len = s.length();
		if (len < 3) {
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
