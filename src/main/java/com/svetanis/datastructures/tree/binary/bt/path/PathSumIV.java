package com.svetanis.datastructures.tree.binary.bt.path;

import java.util.HashMap;
import java.util.Map;

// 666. Path Sum IV
//
// Input: a sorted array of three-digit numbers describing a binary tree of depth less than 5.
// For one number, the hundreds digit d is the depth (the root is 1), the tens digit p is the
// position within that depth (1 to 2^(d-1), counted left to right as in a full tree), and the
// units digit v is the value. Returns the sum of the values of all root-to-leaf paths, each
// path's values added up and the path sums added together. On LeetCode: 1 <= nums.length <= 15,
// 110 <= nums[i] <= 489, and the array describes a valid tree.
//
// The one idea: the pair (d, p) is a node's address, written as the two-digit number 10 * d + p.
// A node's children are at depth d + 1, in positions 2p - 1 and 2p, so the address of both
// children follows from the parent's with no tree built. The map holds address -> value. Walk
// down from address 11 carrying the sum so far; a node with neither child present is a leaf,
// and its path sum is added to the total.
//
// Time: O(n) -- each present node is visited once.
// Space: O(n) -- the map; the recursion is at most 4 deep.
//
// Siblings: none.
public final class PathSumIV {

	private int total;
	private Map<Integer, Integer> map;

	public int pathSum(int[] a) {
		this.total = 0;
		this.map = new HashMap<>();
		for (int num : a) {
			map.put(num / 10, num % 10); // MAP: address (depth and position) -> value
		}
		dfs(11, 0); // START: the root's address
		return total;
	}

	private void dfs(int node, int sum) {
		if (!map.containsKey(node)) { // BASE: no node at this address
			return;
		}
		sum += map.get(node); // TAKE: the value joins the path sum
		int level = node / 10;
		int position = node % 10;
		int left = (level + 1) * 10 + (2 * position) - 1; // ADDRESS: left child
		int right = left + 1; // ADDRESS: right child
		if (!map.containsKey(left) && !map.containsKey(right)) { // LEAF: add the finished path
			total += sum;
			return;
		}
		dfs(left, sum); // WALK: left child
		dfs(right, sum); // WALK: right child
	}

	public static void main(String[] args) {
		PathSumIV ps = new PathSumIV();
		// not a valid encoding: the hundreds digit 1 repeats, so only 111 is read as the root
		int[] a = {111, 121, 122, 131, 132};
		System.out.println(ps.pathSum(a)); // 1

		// LeetCode example 1
		int[] a1 = {113, 215, 221};
		System.out.println(ps.pathSum(a1)); // 12

		// LeetCode example 2
		int[] a2 = {113, 221};
		System.out.println(ps.pathSum(a2)); // 4
	}
}
