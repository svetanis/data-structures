package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 811. Subdomain Visit Count

public final class SubdomainVisitCount {
	// Time Complexity: O(n * m)
	// Space Complexity: O(d)

	public static List<String> subdomainVisits(String[] domains) {
		Map<String, Integer> map = new HashMap<>();
		for (String domain : domains) {
			int index = domain.indexOf(" ");
			int count = Integer.parseInt(domain.substring(0, index));
			for (int i = index; i < domain.length(); i++) {
				char c = domain.charAt(i);
				if (c == ' ' || c == '.') {
					String subdomain = domain.substring(i + 1);
					map.put(subdomain, map.getOrDefault(subdomain, 0) + count);
				}
			}
		}
		List<String> list = new ArrayList<>();
		for (String key : map.keySet()) {
			list.add(map.get(key) + " " + key);
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