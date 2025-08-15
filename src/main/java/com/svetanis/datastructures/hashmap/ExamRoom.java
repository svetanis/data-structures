package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

// 855. Exam Room

public final class ExamRoom {

	private int n;
	private TreeSet<int[]> set;
	private Map<Integer, Integer> leftNeighbours;
	private Map<Integer, Integer> rightNeighbours;

	public ExamRoom(int n) {
		this.n = n;
		this.leftNeighbours = new HashMap<>();
		this.rightNeighbours = new HashMap<>();
		this.set = new TreeSet<>((a, b) -> compare(a, b));
		add(-1, n);
	}

	public int seat() {
		int[] segment = set.first();
		int left = segment[0];
		int right = segment[1];
		int seat = (right + left) / 2;
		if (left == -1) {
			seat = 0;
		} else if (right == n) {
			seat = n - 1;
		}
		remove(segment);
		add(left, seat);
		add(seat, right);
		return seat;
	}

	public void leave(int p) {
		int left = leftNeighbours.get(p);
		int right = rightNeighbours.get(p);
		// remove gaps created by the leaving student
		remove(new int[] { left, p });
		remove(new int[] { p, right });
		add(left, right);
	}

	private int compare(int[] a, int[] b) {
		int da = dist(a);
		int db = dist(b);
		return da == db ? a[0] - b[0] : db - da;
	}

	private int dist(int[] segment) {
		int left = segment[0];
		int right = segment[1];
		if (left == -1 || right == n) {
			return right - left - 1;
		} else {
			return (right - left) / 2;
		}
	}

	private void add(int left, int right) {
		set.add(new int[] { left, right });
		leftNeighbours.put(right, left);
		rightNeighbours.put(left, right);
	}

	private void remove(int[] segment) {
		set.remove(segment);
		leftNeighbours.remove(segment[1]);
		rightNeighbours.remove(segment[0]);
	}

	public static void main(String[] args) {
		ExamRoom examRoom = new ExamRoom(10);
		System.out.println(examRoom.seat()); // return 0, no one is in the room, then the student sits at seat number 0.
		System.out.println(examRoom.seat()); // return 9, the student sits at the last seat number 9.
		System.out.println(examRoom.seat()); // return 4, the student sits at the last seat number 4.
		System.out.println(examRoom.seat()); // return 2, the student sits at the last seat number 2.
		examRoom.leave(4);
		System.out.println(examRoom.seat()); // return 5, the student sits at the last seat number 5.
	}
}