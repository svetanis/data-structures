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
// one more digit (a ticket), flying from its first n - 1 digits to its last n - 1 -- the ticket
// "100" flies from "10" to "00". Every node has k tickets out and k in, so the walk that uses
// every ticket once may start anywhere, at n - 1 zeros. It is 332's walk, except that nobody
// hands over the tickets: from a node they are node + d for each digit d, and a Set of codes
// marks the used ones. Each ticket puts one digit in the string, the digit typed; it is written
// after the call returns, so the digits come out reversed.
//
// Time: O(k^n * n) -- k^n tickets, each a string of n digits built and hashed.
// Space: O(k^n * n) -- the set of used tickets, the answer, and the recursion.
// Known limit: the recursion is as deep as the number of tickets; the default Java stack
// overflows at n = 4, k = 7 (2,401 tickets), inside LeetCode's bound.
//
// Siblings: CrackingTheSafe is the same walk with nodes as numbers; CrackingTheSafeIterative
// walks with a stack of nodes.

public final class CrackingTheSafeStrings {

	public String crackSafe(int n, int k) {
		String start = "0".repeat(n - 1); // START: n - 1 zeros; every airport is balanced
		Set<String> used = new HashSet<>(); // USED: tickets (codes) already flown
		StringBuilder digits = new StringBuilder();
		visit(start, k, used, digits);
		return start + digits.reverse(); // STRING: the start, then the digits in flying order
	}

	private void visit(String node, int k, Set<String> used, StringBuilder digits) {
		for (int d = 0; d < k; d++) {
			String ticket = node + d; // TICKET: this airport plus the digit d
			if (!used.contains(ticket)) { // UNUSED: each ticket flown once
				used.add(ticket);
				visit(ticket.substring(1), k, used, digits); // FOLLOW: the airport it lands at
				digits.append(d); // WRITE: its digit, after the call returns
			}
		}
	}

	public static void main(String[] args) {
		CrackingTheSafeStrings cts = new CrackingTheSafeStrings();
		System.out.println(cts.crackSafe(1, 2)); // 01
		System.out.println(cts.crackSafe(2, 2)); // 00110
	}
}
