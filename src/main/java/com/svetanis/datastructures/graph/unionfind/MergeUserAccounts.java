package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// 721. Accounts Merge

// there are a lot of users on platform
// with various emails attached to their accounts.
// for each user, there is a name and several emails
// associated with that user.
// a lot of users have multiple accounts
// registered with the same email.
// merge some accounts according to the following rules:
// 1. If two accounts have the same name and share at least 
// one common email, they must belong to the same user, 
// and thus can be safely merged.
// 2. Two different accounts can share the same name, 
// as long as they cannot be linked back to the same 
// person via email tracking.
// 3. Two different accounts with different names can 
// never be merged, even if they may share a common email.

public final class MergeUserAccounts {

	private Map<Integer, Integer> map;
	private Map<String, Integer> emails;

	public MergeUserAccounts() {
		this.map = new HashMap<>();
		this.emails = new HashMap<>();
	}

	public List<List<String>> merge(List<List<String>> accounts) {
		init(accounts.size());
		mergeEmails(accounts);
		Map<Integer, Set<String>> merged = mergeAccounts(accounts);
		List<List<String>> list = new ArrayList<>();
		for (int id : merged.keySet()) {
			String name = accounts.get(id).get(0);
			List<String> account = new ArrayList<>();
			account.add(name);
			account.addAll(merged.get(id));
			list.add(account);
		}
		return list;
	}

	private Map<Integer, Set<String>> mergeAccounts(List<List<String>> accounts) {
		Map<Integer, Set<String>> map = new HashMap<>();
		for (int i = 0; i < accounts.size(); i++) {
			List<String> account = accounts.get(i);
			for (int j = 1; j < account.size(); j++) {
				String email = account.get(j);
				int root = find(i);
				if (!map.containsKey(root)) {
					map.put(root, new TreeSet<>());
				}
				map.get(root).add(email);
			}
		}
		return map;
	}

	private void mergeEmails(List<List<String>> accounts) {
		for (int i = 0; i < accounts.size(); i++) {
			List<String> account = accounts.get(i);
			for (int j = 1; j < account.size(); j++) {
				String email = account.get(j);
				if (emails.containsKey(email)) {
					int index = emails.get(email);
					map.put(find(i), find(index));
				} else {
					emails.put(email, i);
				}
			}
		}
	}

	private void init(int n) {
		for (int i = 0; i < n; i++) {
			map.put(i, i);
		}
	}

	private int find(int x) {
		int y = map.getOrDefault(x, x);
		if (y != x) {
			y = find(y);
			map.put(x, y);
		}
		return y;
	}

	public static void main(String[] args) {
		List<List<String>> accounts = new ArrayList<>();
		accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
		accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_work@mail.com"));
		accounts.add(Arrays.asList("Mary", "mary@mail.com"));
		accounts.add(Arrays.asList("John", "johnny@mail.com"));
		MergeUserAccounts mua = new MergeUserAccounts();
		System.out.println(mua.merge(accounts));
	}
}
