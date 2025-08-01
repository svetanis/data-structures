package com.svetanis.datastructures.tree.trie.suggestedsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1268. Search Suggested System

public final class SuggestedSystemBruteForce {
	// Time Complexity: O(m * n + n log n)
	// Space Complexity: O(n)

	public List<List<String>> suggestedProducts(String[] products, String word) {
		Arrays.sort(products);
		List<List<String>> suggestions = new ArrayList<>();
		for (int i = 0; i < word.length(); i++) {
			String prefix = word.substring(0, i + 1);
			List<String> list = new ArrayList<>();
			for (String product : products) {
				if (product.startsWith(prefix)) {
					list.add(product);
					if (list.size() == 3) {
						break;
					}
				}
			}
			suggestions.add(list);
		}
		return suggestions;
	}

	public static void main(String[] args) {
		String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
		SuggestedSystemBruteForce wd = new SuggestedSystemBruteForce();
		Print.print(wd.suggestedProducts(products1, "mouse"));

		String[] products2 = { "havana" };
		SuggestedSystemBruteForce wd2 = new SuggestedSystemBruteForce();
		Print.print(wd2.suggestedProducts(products2, "havana"));
	}
}