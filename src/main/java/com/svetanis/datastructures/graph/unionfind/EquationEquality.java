package com.svetanis.datastructures.graph.unionfind;

// 990. Satisfiability of Equality Equations
//
// Input: String[] equations, each of length 4 and shaped "a==b" or "a!=b" with single lowercase
// letters for variables. On LeetCode: 1 <= equations.length <= 500.
// Returns true when whole numbers can be given to the letters so every equation holds.
//
// The one idea: "==" is a rule that two letters carry the same number, and equal-to is
// transitive (a==b and b==c force a==c). So first use every "==" equation to glue letters into
// groups with union-find: letters in one group must all be equal. Then check every "!=": it
// fails exactly when its two letters ended up in the SAME group. If none does, give each group
// its own number and all equations hold. The order matters: all gluing first, then all checking,
// because a later "==" can join groups that an earlier "!=" already looked at.
//
// Time: O(n) -- two passes over the n equations; each find is close to constant on 26 letters.
// Space: O(1) -- one array of 26 parents, whatever n is.

public final class EquationEquality {

	private int[] parent; // CARDS: parent[c] -- the letter above letter c in its group

	public boolean equationsPossible(String[] equations) {
		// per call, not in the constructor -- unions from an earlier
		// call would otherwise still be joined for this one
		this.parent = init();
		merge(equations); // GLUE: every "==" first
		for (String equation : equations) {
			if (equation.charAt(1) == '!') { // CHECK: every "!=" against the finished groups
				int x = equation.charAt(0) - 'a';
				int y = equation.charAt(3) - 'a';
				if (find(x) == find(y)) {
					return false; // RETURN: told apart but forced equal
				}
			}
		}
		return true;
	}

	public void merge(String[] equations) {
		for (String equation : equations) {
			if (equation.charAt(1) == '=') {
				int x = equation.charAt(0) - 'a';
				int y = equation.charAt(3) - 'a';
				parent[find(x)] = find(y); // JOIN
			}
		}
	}

	public int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y);
			parent[x] = y; // COMPRESS
		}
		return y;
	}

	private int[] init() {
		int[] parent = new int[26];
		for (int i = 0; i < 26; i++) {
			parent[i] = i;
		}
		return parent;
	}

	public static void main(String[] args) {
		String[] a1 = { "a==b", "b!=a" };
		String[] a2 = { "b==a", "a==b" };
		EquationEquality ee = new EquationEquality();
		System.out.println(ee.equationsPossible(a1)); // false
		System.out.println(ee.equationsPossible(a2)); // true
	}
}
