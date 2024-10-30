package com.svetanis.datastructures.tree.segment;

import static java.util.Arrays.asList;

import java.util.List;

public final class RangeMax {

	public RangeMax(List<Integer> list) {
		tree = new int[4 * list.size()];
		build(list);
	}

	private int[] tree;

	private void build(List<Integer> list) {
		int n = list.size();
		for (int i = 0; i < n; i++) {
			update(1, 0, n - 1, i, list.get(i));
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
			tree[curr] = Math.max(tree[left], tree[right]);
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
		return Math.max(left, right);
	}

	public static void main(String[] args) {
		List<Integer> list = asList(1, 2, 3, 4, 5);
		int n = list.size();
		RangeMax rm = new RangeMax(list);
		System.out.println(rm.query(n, 0, 4)); // 5
		rm.update(n, 4, 7);
		System.out.println(rm.query(n, 1, 4)); // 7
	}
}
