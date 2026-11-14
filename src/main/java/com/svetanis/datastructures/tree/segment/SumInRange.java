package com.svetanis.datastructures.tree.segment;

// Sum of a range of positions of an array, with single-position updates. Not a LeetCode
// submission as written; LeetCode 307 (Range Sum Query - Mutable) asks for this, and
// RangeSumQueryMutableSegTree is the file in LeetCode's class shape.
//
// Input: the constructor takes the values. query(n, start, end) returns the sum of the values at
// positions start..end, both ends included; update(n, index, value) replaces the value at one
// position. Positions start at 0, and n, the number of values, is passed on every call. The
// calls assume 0 <= start <= end < n and 0 <= index < n.
//
// The one idea: one node of the tree covers a range of positions low..high and stores the sum
// of the values in that range. The root, in slot 1 of the array, covers 0..n-1. The node in
// slot c splits its range at the middle: the left half goes to slot 2c, the right half to
// slot 2c + 1, and a leaf covers one position and stores that position's value. A query asks
// each node it visits one thing: is my range outside start..end (then I add 0), inside it (then
// my stored sum answers for my whole range and nothing below me is read), or partly inside
// (then add up both halves)? Only nodes on the two edges of the query range are partly inside,
// so a query reads a few nodes per level. An update rewrites one leaf and then recomputes the
// sum of each node on the way back up to the root, one node per level.
//
// Time: build O(n) -- every node is written once; query and update O(log n) -- a few nodes on
// each of the log n levels.
// Space: O(n) -- the array has 4n slots, which is enough for any n.
//
// Siblings: the same tree in other files, each answering ranges over an array with
// single-position updates (* is this file):
//
//   file                          answers  root    children        update
// * SumInRange                    sum      slot 1  2c, 2c + 1      sets the leaf, recomputes
//                                                                  on the way up
//   RangeMax                      max      slot 1  2c, 2c + 1      as SumInRange, Math.max for +
//                                                                  and Integer.MIN_VALUE for 0
//   SumInRangeII                  sum      slot 0  2i + 1, 2i + 2  adds the change to each node
//                                                                  on the way down; static
//                                                                  methods
//   RangeSumQueryMutableSegTree   sum      slot 0  2i + 1, 2i + 2  as SumInRange; LC 307's
//                                                                  class shape
//
// SumInRange and RangeMax are in com.svetanis.datastructures.tree.segment, SumInRangeII in
// com.svetanis.datastructures.todo.tree.segment, RangeSumQueryMutableSegTree in the algorithms
// repo, com.svetanis.algorithms.todo.bit.

public final class SumInRange {

	public SumInRange(int[] a) {
		tree = new int[4 * a.length]; // ALLOCATE: 4n slots
		build(a); // BUILD: every node
	}

	private int[] tree;

	private void build(int[] a) {
		if (a.length == 0) { // BASE: no values, no tree
			return;
		}
		build(a, 1, 0, a.length - 1); // BUILD: the root covers 0..n-1
	}

	// both halves first, then the node from them: every node is written exactly once,
	// so the build is O(n)
	private void build(int[] a, int curr, int low, int high) {
		if (low == high) {
			tree[curr] = a[low]; // LEAF: one position holds its own value
			return;
		}
		int mid = middle(low, high); // SPLIT: at the middle
		int left = 2 * curr; // left child
		int right = 2 * curr + 1; // right child
		build(a, left, low, mid); // BUILD: left half first
		build(a, right, mid + 1, high); // BUILD: then right half
		tree[curr] = tree[left] + tree[right]; // COMBINE: the sums of the two halves
	}

	public void update(int n, int index, int value) {
		update(1, 0, n - 1, index, value); // START: at the root
	}

	private void update(int curr, int low, int high, int index, int value) {
		if (low == high && low == index) {
			tree[curr] = value; // SET: the leaf for index
		} else {
			int mid = middle(low, high); // SPLIT: at the middle
			int left = 2 * curr; // left child
			int right = 2 * curr + 1; // right child
			if (index <= mid) { // index lies in the left half, low..mid
				update(left, low, mid, index, value); // DESCEND: left
			} else {
				update(right, mid + 1, high, index, value); // DESCEND: right
			}
			tree[curr] = tree[left] + tree[right]; // RECOMPUTE: one half changed
		}
	}

	private int middle(int low, int high) {
		return low + (high - low) / 2; // MIDDLE: written so low + high cannot overflow
	}

	public int query(int n, int start, int end) {
		return query(1, 0, n - 1, start, end); // START: at the root
	}

	private int query(int curr, int low, int high, int start, int end) {
		if (low > end || high < start) {
			return 0; // OUTSIDE: adds nothing to the sum
		} else if (start <= low && end >= high) {
			return tree[curr]; // INSIDE: this node's sum answers for all of its range
		}
		int mid = middle(low, high); // PARTLY: add up both halves
		int left = query(2 * curr, low, mid, start, end);
		int right = query(2 * curr + 1, mid + 1, high, start, end);
		return left + right; // COMBINE: the two answers
	}

	public static void main(String[] args) {
		int[] a = { 1, 3, 5, 7, 9, 11 };
		int n = a.length;
		SumInRange sir = new SumInRange(a);
		System.out.println(sir.query(n, 1, 3)); // 15
		sir.update(n, 1, 10);
		System.out.println(sir.query(n, 1, 3)); // 22
	}
}
