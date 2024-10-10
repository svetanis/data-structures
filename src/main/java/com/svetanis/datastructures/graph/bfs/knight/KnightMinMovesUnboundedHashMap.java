package com.svetanis.datastructures.graph.bfs.knight;

import static com.google.common.collect.Maps.newHashMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.graph.Coordinate;

// On an infinitely large chessboard,
// a knight is located on [0, 0].
// a knight can move in 8 directions.

// given a destination coordinate [x, y],
// determine the min number of moves from
// [0, 0] to [x, y]

public final class KnightMinMovesUnboundedHashMap {
	// Time Complexity: O(|x| * |y|)
	// Space Complexity: O(|x| * |y|)

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static int ksp(int x, int y) {
		Coordinate src = new Coordinate(0, 0);
		Coordinate dst = new Coordinate(x, y);
		return bfs(src, dst);
	}

	public static int bfs(Coordinate src, Coordinate dst) {
		Map<Coordinate, Integer> map = newHashMap();
		Queue<Coordinate> queue = new ArrayDeque<>();
		queue.add(src);
		map.put(src, 0);
		while (!queue.isEmpty()) {
			Coordinate curr = queue.poll();
			if (curr.getRow() == dst.getRow() && curr.getCol() == dst.getCol()) {
				return map.get(curr);
			}
			List<Coordinate> neighbors = neighbors(curr);
			for (Coordinate neighbor : neighbors) {
				if (!map.containsKey(neighbor)) {
					map.put(neighbor, map.get(curr) + 1);
					queue.add(neighbor);
				}
			}
		}
		return -1;
	}

	private static List<Coordinate> neighbors(Coordinate curr) {
		List<Coordinate> list = new ArrayList<>();
		for (int dir = 0; dir < dx.length; dir++) {
			int x = curr.getRow() + dx[dir];
			int y = curr.getCol() + dy[dir];
			list.add(new Coordinate(x, y));
		}
		return list;
	}

	public static void main(String[] args) {
		System.out.println(ksp(4, 1)); // 3
		System.out.println(ksp(7, 0)); // 5
		System.out.println(ksp(2, 1)); // 1
		System.out.println(ksp(5, 5)); // 4
		System.out.println(ksp(1, 1)); // 2
	}
}
