package com.svetanis.datastructures.hashmap;

// 2043. Simple Bank System

public final class BankSystemSimple {

	private long[] balance;

	public BankSystemSimple(long[] balance) {
		this.balance = balance.clone();
	}

	private boolean validAccount(int account) {
		return account >= 1 && account <= balance.length;
	}

	private boolean validMoney(int account, long money) {
		return balance[account - 1] >= money;
	}

	public boolean transfer(int account1, int account2, long money) {
		if (validAccount(account1) && validAccount(account2) 
				&& validMoney(account1, money)) {
			balance[account1 - 1] -= money;
			balance[account2 - 1] += money;
			return true;
		}
		return false;
	}

	public boolean deposit(int account, long money) {
		if (validAccount(account)) {
			balance[account - 1] += money;
			return true;
		}
		return false;
	}

	public boolean withdraw(int account, long money) {
		if (validAccount(account) && validMoney(account, money)) {
			balance[account - 1] -= money;
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