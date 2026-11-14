package com.svetanis.datastructures.graph.directed.eulerian;

import java.util.HashSet;
import java.util.Set;

// 753. Cracking the Safe
//
// Input: n -- the length of the password -- and k -- the digits 0 .. k - 1 it is made of. After
// every digit typed the safe compares the last n digits typed with the password. Returns any
// string that contains every possible password as a substring; the shortest such string has
// k^n + n - 1 digits, and this method returns one of that length. On LeetCode: 1 <= n <= 4,
// 1 <= k <= 10, 1 <= k^n <= 4096.
//
// The one idea: one node is the last n - 1 digits typed (an airport); one code is a node plus
// one more digit (a ticket), flying from its first n - 1 digits to its last n - 1. Every node has
// k tickets out and k in, so a walk exists that uses every ticket exactly once (an Euler circuit)
// and may start anywhere -- at n - 1 zeros. Using every ticket once means typing every code once,
// each new digit adding one new code, which is why the length is the minimum. The walk is 332's:
// from a node take each unused ticket, fly it, and write its digit only after that call returns;
// the digits come out in reverse, so the answer is the start node plus the digits reversed.
// Here a node is a NUMBER below 10^(n-1) and a ticket is node * 10 + d: unique because d < 10;
// ticket % mod drops the oldest digit and gives the node it lands on.
//
// Time: O(k^n) -- one set insertion and one call per ticket, and there are k^n tickets.
// Space: O(k^n) -- the set of used tickets, the answer, and the recursion.
// Known limit: the recursion is as deep as the number of tickets. With int nodes the calls are
// small and the whole LeetCode bound (4,096 tickets) runs on a default Java stack; the String
// version overflows from 2,401.
//
// Siblings: CrackingTheSafeStrings is the same walk with nodes and tickets as strings
// (substring(1) drops the oldest digit); CrackingTheSafeIterative walks with a stack of nodes,
// so no recursion can overflow.

public final class CrackingTheSafe {

	private int mod;

	public String crackSafe(int n, int k) {
		this.mod = (int) Math.pow(10, n - 1); // NODES: the numbers 0 .. 10^(n-1) - 1
		Set<Integer> used = new HashSet<>(); // USED: tickets already flown
		StringBuilder digits = new StringBuilder();
		visit(0, k, used, digits); // START: n - 1 zeros; every node is balanced
		String start = "0".repeat(n - 1);
		return start + digits.reverse(); // STRING: the start node, then the digits in flying order
	}

	private void visit(int node, int k, Set<Integer> used, StringBuilder digits) {
		for (int d = 0; d < k; d++) {
			int ticket = node * 10 + d; // TICKET: this node, then digit d
			if (used.add(ticket)) { // UNUSED: each ticket flown once
				visit(ticket % mod, k, used, digits); // FOLLOW: the oldest digit dropped
				digits.append(d); // WRITE: its digit, after the call returns
			}
		}
	}

	public static void main(String[] args) {
		CrackingTheSafe cts = new CrackingTheSafe();
		System.out.println(cts.crackSafe(1, 2)); // 01
		System.out.println(cts.crackSafe(2, 2)); // 00110
	}
}
