package com.svetanis.datastructures.graph.dfs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 3481. Apply Substitutions
//
// Input: replacements -- pairs [key, value]: key is one uppercase letter, value a string of at most
// 8 characters that may hold placeholders %X% naming other keys; text -- the placeholders of every
// key, in some order, joined by '_'. Returns text with every placeholder replaced, again and again,
// until none is left. On LeetCode: 1 <= replacements.length <= 10, keys distinct, every placeholder
// names a key, and no key depends on itself through other keys.
//
// The one idea: one node is a key, with an arrow from C to B when C's value mentions %B%. A key
// can be finished only after the keys it mentions -- for D -> C -> B the order is B, C, D -- which
// is what a recursive call gives: resolve(value) reads left to right, copies a plain character,
// and on a '%' pastes finish(key) for the letter at i + 1, then goes on at i + 3 (a key is one
// letter, so a placeholder is always three characters). finish(key) resolves the key's raw value
// and keeps the result in memo, so a key mentioned many times is worked out once. resolve and
// finish call each other; the calls end because the deepest key's value has no '%'.
//
// Known limit: a cycle (C mentions D, D mentions C) is a StackOverflowError -- memo is written only
// after resolve returns, and with a cycle it never does. Catching one needs a third state per key,
// "being worked on now", as in a topological sort's cycle check. The statement rules cycles out.
//
// Time: O(k * L + |answer|) -- k keys, each raw value of length L read once thanks to memo, plus
// building the answer. Space: O(k * |finished value|) for memo, and O(k) calls deep.

public final class ApplySubstitutions {

	private Map<Character, String> raw; // key -> its value as given
	private Map<Character, String> memo; // key -> its finished value

	public String applySubstitutions(List<List<String>> replacements, String text) {
		this.raw = new HashMap<>();
		this.memo = new HashMap<>();
		for (List<String> replacement : replacements) { // RAW: each entry is [key, value]
			char key = replacement.get(0).charAt(0);
			raw.put(key, replacement.get(1));
		}
		return resolve(text); // text is resolved like any value
	}

	private String resolve(String value) { // a string with placeholders -> one with none
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < value.length()) {
			if (value.charAt(i) == '%') { // PLACEHOLDER: '%', the key letter, '%'
				char key = value.charAt(i + 1); // the LETTER at i + 1, not the number i + 1
				sb.append(finish(key));
				i = i + 3; // past the closing '%'
			} else { // COPY: any other character -- and only when it is not a placeholder
				sb.append(value.charAt(i));
				i += 1;
			}
		}
		return sb.toString();
	}

	private String finish(char key) { // one key's finished value
		if (memo.get(key) != null) {
			return memo.get(key); // MEMO: finished already
		}
		String val = resolve(raw.get(key)); // RESOLVE: its own placeholders first
		memo.put(key, val);
		return val;
	}

	public static void main(String[] args) {
		ApplySubstitutions as = new ApplySubstitutions();
		System.out.println(as.applySubstitutions(List.of(List.of("A", "abc"), List.of("B", "def")), "%A%_%B%")); // abc_def
		System.out.println(as.applySubstitutions(
				List.of(List.of("A", "bce"), List.of("B", "ace"), List.of("C", "abc%B%")), "%A%_%B%_%C%")); // bce_ace_abcace
		System.out.println(as.applySubstitutions(
				List.of(List.of("B", "ace"), List.of("C", "abc%B%"), List.of("D", "%C%x")), "%D%_%C%_%B%")); // abcacex_abcace_ace
	}
}
