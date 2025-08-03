package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 2043. Simple Bank System

public final class BankSystem {

	private Map<Integer, Long> map;

	public BankSystem(long[] balance) {
		this.map = new HashMap<>();
		init(balance);
	}

	private void init(long[] balance) {
		for (int i = 0; i < balance.length; i++) {
			this.map.put(i + 1, balance[i]);
		}
	}

	public boolean transfer(int account1, int account2, long money) {
		if (map.getOrDefault(account1, 0L) < money) {
			return false;
		}
		if (!map.containsKey(account2)) {
			return false;
		}
		map.merge(account1, -money, Long::sum);
		map.merge(account2, money, Long::sum);
		return true;
	}

	public boolean deposit(int account, long money) {
		if (!map.containsKey(account)) {
			return false;
		}
		map.merge(account, money, Long::sum);
		return true;
	}

	public boolean withdraw(int account, long money) {
		if (map.getOrDefault(account, 0L) < money) {
			return false;
		}
		map.merge(account, -money, Long::sum);
		return true;
	}

	public static void main(String[] args) {
		long[] a1 = { 10, 100, 20, 50, 30 };
		BankSystem bs = new BankSystem(a1);
		System.out.println(bs.withdraw(3, 10)); // true
		System.out.println(bs.transfer(5, 1, 20)); // true
		System.out.println(bs.deposit(5, 20)); // true
		System.out.println(bs.transfer(3, 4, 15)); // false
		System.out.println(bs.withdraw(10, 50)); // false
	}
}