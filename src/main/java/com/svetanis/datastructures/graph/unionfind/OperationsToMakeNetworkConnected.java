package com.svetanis.datastructures.graph.unionfind;

// 1319. Number of Operations to Make Network Connected
//
// Input: n -- computers numbered 0..n-1; connections -- cables [a, b] between two computers. One
// operation takes a cable out from between two computers and plugs it in between two others.
// Returns the fewest operations that make every computer reachable from every other, or -1 if
// that cannot be done. On LeetCode: 1 <= n <= 10^5, 1 <= connections.length <= min(n * (n - 1) /
// 2, 10^5), a != b, no cable repeated, no two computers joined by more than one cable.
//
// The one idea: one element is one computer. Going through the cables, a cable between two
// computers already in one group is REDUNDANT -- removing it breaks nothing -- and a cable between
// two different groups merges them. Afterwards `n` has dropped to the number of groups, and joining
// g groups takes exactly g - 1 cables. If the redundant cables number at least g - 1, move g - 1 of
// them; if fewer, no sequence of moves works, because only a redundant cable can be moved without
// splitting a group.
//
// Time: O(c * log n) -- one find per end of each of c cables; no union by size, so a find is
// O(log n) amortised from compression alone.
// Space: O(n) -- the parent array.
//
// Known limit: find is recursive. With n = 10^5 and the cables chained 0-1, 1-2, ...
// and then a cable reaching back to computer 0, the first find on 0 is about 10^5 calls deep and
// overflows the default Java stack.

public final class OperationsToMakeNetworkConnected {

	private int[] parent;

	public int network(int n, int[][] connections) {
		init(n);
		int redundant = 0; // cables whose two ends were already connected
		for (int[] connection : connections) {
			int px = find(connection[0]);
			int py = find(connection[1]);
			if (px != py) {
				parent[px] = py; // JOIN: two groups become one
				n -= 1; // COUNT: n now holds the number of groups
			} else {
				redundant++; // SPARE: this cable closes a loop
			}
		}
		return n - 1 > redundant ? -1 : n - 1; // RETURN: groups - 1 moves, if enough spares
	}

	private void init(int n) {
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]); // WALK up, COMPRESS on the way back
		}
		return parent[x];
	}

	public static void main(String[] args) {
		OperationsToMakeNetworkConnected mnc = new OperationsToMakeNetworkConnected();
		int[][] g1 = { { 0, 1 }, { 0, 2 }, { 1, 2 } };
		System.out.println(mnc.network(4, g1)); // 1 -- the spare cable 1-2 goes to computer 3

		int[][] g2 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 } };
		System.out.println(mnc.network(6, g2)); // 2 -- two spares for the two lone computers

		int[][] g3 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 } };
		System.out.println(mnc.network(6, g3)); // -1 -- one spare, two moves needed
	}
}
