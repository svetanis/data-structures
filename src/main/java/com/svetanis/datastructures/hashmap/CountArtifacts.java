package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 2201. Count Artifacts That Can Be Extracted

public final class CountArtifacts {

	public static int artifacts(int n, int[][] a, int[][] dig) {
		Set<Integer> set = new HashSet<>();
		for (int[] cell : dig) {
			set.add(cell[0] * n + cell[1]);
		}
		int count = 0;
		for (int[] artifact : a) {
			if (isArtifact(artifact, set, n)) {
				count++;
			}
		}
		return count;
	}

	private static boolean isArtifact(int[] artifact, Set<Integer> set, int n) {
		int top = artifact[0], bottom = artifact[2];
		int left = artifact[1], right = artifact[3];
		for (int i = top; i <= bottom; i++) {
			for (int j = left; j <= right; j++) {
				if (!set.contains(i * n + j)) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[][] a1 = { { 0, 0, 0, 0 }, { 0, 1, 1, 1 } };
		int[][] d1 = { { 0, 0 }, { 0, 1 } };
		System.out.println(artifacts(2, a1, d1)); // 1

		int[][] a2 = { { 0, 0, 0, 0 }, { 0, 1, 1, 1 } };
		int[][] d2 = { { 0, 0 }, { 0, 1 }, { 1, 1 } };
		System.out.println(artifacts(2, a2, d2)); // 2
	}
}
