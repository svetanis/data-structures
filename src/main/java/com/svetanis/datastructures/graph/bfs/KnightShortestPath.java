package com.svetanis.datastructures.graph.bfs;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Queue;
import java.util.Set;

import com.google.common.base.Optional;

public final class KnightShortestPath {

	// Time Complexity: O(n*m)

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static Optional<Integer> ksp(int n, Node src, Node dst) {
		Set<Node> set = newHashSet();
		Queue<Node> queue = newLinkedList();
		queue.add(src);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node.x == dst.x && node.y == dst.y) {
				return of(node.dist);
			}

			if (!set.contains(node)) {
				set.add(node);
				// check all 8 moves and enqueue
				// each valid movement in the queue
				for (int dir = 0; dir < dx.length; dir++) {
					int x = node.x + dx[dir];
					int y = node.y + dy[dir];
					if (valid(x, y, n)) {
						queue.add(new Node(x, y, node.dist + 1));
					}
				}
			}
		}
		return absent();
	}

	private static boolean valid(int x, int y, int n) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	public static void main(String[] args) {
		Node src1 = new Node(0, 0);
		Node dst1 = new Node(4, 1);
		System.out.println(ksp(5, src1, dst1));

		Node src2 = new Node(0, 7);
		Node dst2 = new Node(7, 0);
		System.out.println(ksp(8, src2, dst2));
	}
}
