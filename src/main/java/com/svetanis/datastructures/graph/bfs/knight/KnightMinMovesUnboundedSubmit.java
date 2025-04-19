package com.svetanis.datastructures.graph.bfs.knight;

import java.util.ArrayDeque;
import java.util.Queue;

// 1197. Minimum Knight Moves

public final class KnightMinMovesUnboundedSubmit {
	// Time Complexity: O(|x| * |y|)
	// Space Complexity: O(|x| * |y|)

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static int ksp(int x, int y) {
		x += 310;
		y += 310;
		boolean[][] visited = new boolean[621][621];
		visited[310][310] = true;
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] { 310, 310 });
		int steps = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				int[] curr = queue.poll();
				if (curr[0] == x && curr[1] == y) {
					return steps;
				}
				for (int dir = 0; dir < dx.length; dir++) {
					int row = curr[0] + dx[dir];
					int col = curr[1] + dy[dir];
					if (row >= 0 && col >= 0 && row < visited.length && col < visited[0].length && !visited[row][col]) {
						visited[row][col] = true;
						queue.add(new int[] { row, col });
					}
				}
			}
			steps++;
		}
		return -1;
	}

	public static void main(String[] args) {
		System.out.println(ksp(5, 5)); // 4
		System.out.println(ksp(4, 1)); // 3
		System.out.println(ksp(7, 0)); // 5
		System.out.println(ksp(2, 1)); // 1
		System.out.println(ksp(5, 5)); // 4
		System.out.println(ksp(1, 1)); // 2
	}
}
