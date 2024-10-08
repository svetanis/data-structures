package com.svetanis.datastructures.graph.bfs.knight;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Optional;
import com.svetanis.datastructures.graph.Cell;

public final class KnightShortestPathPattern {
	// Time Complexity: O(n^2)

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static Optional<Integer> ksp(int n, Cell src, Cell dst) {
		Set<Cell> set = newHashSet();
		Queue<Cell> queue = newLinkedList();
		queue.add(src);
		set.add(src);
		while (!queue.isEmpty()) {
			Cell node = queue.poll();
			if (node.getX() == dst.getX() && node.getY() == dst.getY()) {
				return of(node.getDist());
			}
			List<Cell> neighbors = neighbors(node, n);
			for (Cell neighbor : neighbors) {
				if (!set.contains(neighbor)) {
					set.add(neighbor);
					queue.add(neighbor);
				}
			}
		}
		return absent();
	}

	private static List<Cell> neighbors(Cell node, int n) {
		List<Cell> list = new ArrayList<>();
		for (int dir = 0; dir < dx.length; dir++) {
			int x = node.getX() + dx[dir];
			int y = node.getY() + dy[dir];
			if (valid(x, y, n)) {
				list.add(new Cell(x, y, node.getDist() + 1));
			}
		}
		return list;
	}

	private static boolean valid(int x, int y, int n) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	public static void main(String[] args) {
		Cell src1 = new Cell(0, 0);
		Cell dst1 = new Cell(4, 1);
		System.out.println(ksp(5, src1, dst1)); // 3

		Cell src2 = new Cell(0, 7);
		Cell dst2 = new Cell(7, 0);
		System.out.println(ksp(8, src2, dst2)); // 6
	}
}
