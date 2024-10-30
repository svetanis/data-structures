package com.svetanis.datastructures.tree.segment;

public final class SumInRange {

	public SumInRange(int[] a) {
		tree = new int[4 * a.length];
		build(a);
	}

	private int[] tree;

	private void build(int[] a) {
		for (int i = 0; i < a.length; i++) {
			update(1, 0, a.length - 1, i, a[i]);
		}
	}

	public void update(int n, int index, int value) {
		update(1, 0, n - 1, index, value);
	}

	private void update(int curr, int low, int high, int index, int value) {
		if (low == high && low == index) {
			tree[curr] = value;
		} else {
			int mid = middle(low, high);
			int left = 2 * curr; // left child
			int right = 2 * curr + 1; // right child
			if (index <= mid) {
				update(left, low, mid, index, value);
			} else {
				update(right, mid + 1, high, index, value);
			}
			tree[curr] = tree[left] + tree[right];
		}
	}

	private int middle(int low, int high) {
		return low + (high - low) / 2;
	}

	public int query(int n, int start, int end) {
		return query(1, 0, n - 1, start, end);
	}

	private int query(int curr, int low, int high, int start, int end) {
		if (low > end || high < start) {
			return 0;
		} else if (start <= low && end >= high) {
			return tree[curr];
		}
		int mid = middle(low, high);
		int left = query(2 * curr, low, mid, start, end);
		int right = query(2 * curr + 1, mid + 1, high, start, end);
		return left + right;
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
