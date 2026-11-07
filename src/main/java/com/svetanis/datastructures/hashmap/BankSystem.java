package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 2043. Simple Bank System
//
// The bank has n accounts, numbered 1..n, starting with balance[i - 1]. transfer(a1, a2, m),
// deposit(a, m) and withdraw(a, m) each return true and change the balances if every account
// named exists and the account paying has at least m; otherwise they return false and change
// nothing. On LeetCode: 1 <= n <= 10^5, account numbers 1..10^5 (so they can name an account
// that does not exist), balances and money up to 10^12, hence long.
//
// A map of account -> balance: an account exists if it is a key. Every operation checks
// first and writes after, so a false return leaves the map untouched. The existence check
// comes before the balance check on its own: a missing account read as balance 0 passes
// "0 >= money" when money is 0, and merge would then create the account.
//
// Siblings, same problem:
//   BankSystemSimple  the same checks on a plain array, account a at index a - 1; "does it
//                     exist" is a range check, which works because the numbers have no gaps

public final class BankSystem {
	// Time complexity: O(1) per operation, expected, for the hash lookups; O(n) to build the map
	// Auxiliary Space: O(n)

	private Map<Integer, Long> map; // BALANCES: account -> balance; a key means it exists

	public BankSystem(long[] balance) {
		this.map = new HashMap<>();
		init(balance);
	}

	private void init(long[] balance) {
		for (int i = 0; i < balance.length; i++) {
			this.map.put(i + 1, balance[i]); // NUMBER: accounts start at 1
		}
	}

	public boolean transfer(int account1, int account2, long money) {
		if (!map.containsKey(account1) || map.get(account1) < money) { // EXISTS? then ENOUGH?
			return false;
		}
		if (!map.containsKey(account2)) { // EXISTS?: checked before any write
			return false;
		}
		map.merge(account1, -money, Long::sum); // TAKE
		map.merge(account2, money, Long::sum); // GIVE
		return true;
	}

	public boolean deposit(int account, long money) {
		if (!map.containsKey(account)) { // EXISTS?
			return false;
		}
		map.merge(account, money, Long::sum); // GIVE
		return true;
	}

	public boolean withdraw(int account, long money) {
		if (!map.containsKey(account) || map.get(account) < money) { // EXISTS? then ENOUGH?
			return false;
		}
		map.merge(account, -money, Long::sum); // TAKE
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