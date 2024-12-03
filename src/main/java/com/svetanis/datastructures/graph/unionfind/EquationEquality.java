package com.svetanis.datastructures.graph.unionfind;

// 990. Satisfiability of Equality Equations

public final class EquationEquality {
	// Time Complexity: O(n)

	public EquationEquality() {
		this.parent = init();
	}

	private int[] parent;

	public boolean equationsPossible(String[] equations) {
		merge(equations);
		for (String equation : equations) {
			if (equation.charAt(1) == '!') {
				int x = equation.charAt(0) - 'a';
				int y = equation.charAt(3) - 'a';
				if (find(x) == find(y)) {
					return false;
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
				parent[find(x)] = find(y);
			}
		}
	}

	public int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y);
			parent[x] = y;
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
