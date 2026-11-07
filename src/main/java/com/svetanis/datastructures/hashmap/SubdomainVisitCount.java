package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 811. Subdomain Visit Count
//
// subdomainVisits(cpdomains) takes entries like "9001 discuss.leetcode.com": a visit count and
// a domain. A visit to a domain is also a visit to every domain it ends with, here
// "leetcode.com" and "com". It returns one "count domain" string per domain with its total
// visits, in any order. On LeetCode: 1 <= cpdomains.length <= 100, each domain has one or two
// dots, counts up to 10^4.
//
// Every space or dot in an entry is followed by one of the domains to credit: after the space
// comes the full domain, and after each dot a shorter one. So each such position adds the
// entry's count to the text that follows it, in a map from domain to total.
//
// Siblings, same problem: none in this package.

public final class SubdomainVisitCount {
	// Time complexity: O(n * m), m the longest entry: each entry is scanned once and credits
	//   at most three domains, each a substring and a hash of length at most m
	// Auxiliary Space: O(d * m), d distinct domains held as keys

	public static List<String> subdomainVisits(String[] domains) {
		Map<String, Integer> map = new HashMap<>();
		for (String domain : domains) {
			int index = domain.indexOf(" ");
			int count = Integer.parseInt(domain.substring(0, index)); // COUNT: the number before the space
			for (int i = index; i < domain.length(); i++) {
				char c = domain.charAt(i);
				if (c == ' ' || c == '.') { // SUFFIX: a domain starts right after this
					String subdomain = domain.substring(i + 1);
					map.put(subdomain, map.getOrDefault(subdomain, 0) + count); // ADD
				}
			}
		}
		List<String> list = new ArrayList<>();
		for (String key : map.keySet()) {
			list.add(map.get(key) + " " + key); // PAIR: back to "count domain"
		}
		return list;
	}

	public static void main(String[] args) {
		String[] a1 = { "9001 discuss.leetcode.com" };
		System.out.println(subdomainVisits(a1));

		String[] a2 = { "900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org" };
		System.out.println(subdomainVisits(a2));
	}
}