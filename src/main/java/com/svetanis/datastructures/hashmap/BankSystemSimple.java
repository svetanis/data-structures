package com.svetanis.datastructures.hashmap;

// 2043. Simple Bank System
//
// The bank has n accounts, numbered 1..n, starting with balance[i - 1]. transfer(a1, a2, m),
// deposit(a, m) and withdraw(a, m) each return true and change the balances if every account
// named is in 1..n and the account paying has at least m; otherwise they return false and
// change nothing. On LeetCode: 1 <= n <= 10^5, account numbers 1..10^5 (so they can name an
// account that does not exist), balances and money up to 10^12, hence long.
//
// The accounts are numbered 1..n with no gaps, so an array is enough: account a lives at
// index a - 1, and "does it exist" is a range check. Every operation checks everything first
// and only then writes, so a false return leaves the balances untouched.
//
// Siblings, same problem:
//   BankSystem  the same checks on a map of account -> balance; "does it exist" is a key lookup

public final class BankSystemSimple {
	// Time complexity: O(1) per operation; O(n) to copy the starting balances
	// Auxiliary Space: O(n)

	private long[] balance; // BALANCES: account a at index a - 1

	public BankSystemSimple(long[] balance) {
		this.balance = balance.clone();
	}

	private boolean validAccount(int account) {
		return account >= 1 && account <= balance.length; // EXISTS?: numbered 1..n, no gaps
	}

	private boolean validMoney(int account, long money) {
		return balance[account - 1] >= money; // ENOUGH?: only after EXISTS?, or the index is wrong
	}

	public boolean transfer(int account1, int account2, long money) {
		if (validAccount(account1) && validAccount(account2)
				&& validMoney(account1, money)) { // CHECK: everything before any write
			balance[account1 - 1] -= money; // TAKE
			balance[account2 - 1] += money; // GIVE
			return true;
		}
		return false;
	}

	public boolean deposit(int account, long money) {
		if (validAccount(account)) {
			balance[account - 1] += money; // GIVE
			return true;
		}
		return false;
	}

	public boolean withdraw(int account, long money) {
		if (validAccount(account) && validMoney(account, money)) {
			balance[account - 1] -= money; // TAKE
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		long[] a1 = { 10, 100, 20, 50, 30 };
		BankSystemSimple bs = new BankSystemSimple(a1);
		System.out.println(bs.withdraw(3, 10)); // true
		System.out.println(bs.transfer(5, 1, 20)); // true
		System.out.println(bs.deposit(5, 20)); // true
		System.out.println(bs.transfer(3, 4, 15)); // false
		System.out.println(bs.withdraw(10, 50)); // false
	}
}