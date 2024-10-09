package com.svetanis.datastructures.graph.bfs.knight;

import static com.google.common.collect.Sets.newHashSet;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Coordinate;

public final class KnightMinMoves {
	// Time Complexity: O(n^2)

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static int ksp(int n, Coordinate src, Coordinate dst) {
		Set<Coordinate> set = newHashSet();
		Queue<Coordinate> queue = new ArrayDeque<>();
		queue.add(src);
		set.add(src);
		int steps = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Coordinate curr = queue.poll();
				if (curr.getRow() == dst.getRow() && curr.getCol() == dst.getCol()) {
					return steps;
				}
				List<Coordinate> neighbors = neighbors(curr, n);
				for (Coordinate neighbor : neighbors) {
					if (!set.contains(neighbor)) {
						set.add(curr);
						queue.add(neighbor);
					}
				}
			}
			steps++;
		}
		return -1;
	}

	private static List<Coordinate> neighbors(Coordinate curr, int n) {
		List<Coordinate> list = new ArrayList<>();
		for (int dir = 0; dir < dx.length; dir++) {
			int x = curr.getRow() + dx[dir];
			int y = curr.getCol() + dy[dir];
			if (valid(x, y, n)) {
				list.add(new Coordinate(x, y));
			}
		}
		return list;
	}

	private static boolean valid(int x, int y, int n) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	public static void main(String[] args) {
		Coordinate src1 = new Coordinate(0, 0);
		Coordinate dst1 = new Coordinate(4, 1);
		System.out.println(ksp(5, src1, dst1)); // 3

		Coordinate src2 = new Coordinate(0, 7);
		Coordinate dst2 = new Coordinate(7, 0);
		System.out.println(ksp(8, src2, dst2)); // 6
	}
}
