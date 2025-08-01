package com.svetanis.datastructures.tree.trie.suggestedsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1268. Search Suggested System

public final class SuggestedSystemBinary {
	// Time Complexity: O(m + n log n)
	// Space Complexity: O(1)

	public List<List<String>> suggestedProducts(String[] products, String word) {
		int left = 0;
		int right = products.length - 1;
		Arrays.sort(products);
		List<List<String>> suggestions = new ArrayList<>();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			while (left <= right && (products[left].length() <= i || products[left].charAt(i) != c)) {
				left++;
			}
			while (left <= right && (products[right].length() <= i || products[right].charAt(i) != c)) {
				right--;
			}
			List<String> list = new ArrayList<>();
			for (int j = left; j < Math.min(left + 3, right + 1); j++) {
				list.add(products[j]);
			}
			suggestions.add(list);
		}
		return suggestions;
	}

	public static void main(String[] args) {
		String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
		SuggestedSystemBinary wd = new SuggestedSystemBinary();
		Print.print(wd.suggestedProducts(products1, "mouse"));

		String[] products2 = { "havana" };
		SuggestedSystemBinary wd2 = new SuggestedSystemBinary();
		Print.print(wd2.suggestedProducts(products2, "havana"));
	}
}