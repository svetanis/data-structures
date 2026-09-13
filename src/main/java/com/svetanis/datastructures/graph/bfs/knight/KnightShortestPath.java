package com.svetanis.datastructures.graph.bfs.knight;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Queue;
import java.util.Set;

import com.google.common.base.Optional;
import com.svetanis.datastructures.graph.Cell;

public final class KnightShortestPath {
	// Time Complexity: O(n^2)

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static Optional<Integer> ksp(int n, Cell src, Cell dst) {
		Set<Cell> set = newHashSet();
		Queue<Cell> queue = newLinkedList();
		queue.add(src);
		set.add(src);
		// one drain of the queue is one level, so every square dequeued
		// inside the inner loop is exactly `dist` moves from the source.
		// that is why the distance does not have to live on the node
		int dist = 0;
		while (!queue.isEmpty()) {
			for (int size = queue.size(); size > 0; size--) {
				Cell node = queue.poll();
				if (node.getX() == dst.getX() && node.getY() == dst.getY()) {
					return of(dist);
				}
				// check all 8 moves and enqueue
				// each valid movement in the queue
				for (int dir = 0; dir < dx.length; dir++) {
					int x = node.getX() + dx[dir];
					int y = node.getY() + dy[dir];
					Cell next = new Cell(x, y);
					// mark at ENQUEUE: marking at poll lets the same square
					// sit in the queue several times over
					if (valid(x, y, n) && set.add(next)) {
						queue.add(next);
					}
				}
			}
			dist++;
		}
		return absent();
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
