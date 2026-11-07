package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

// 855. Exam Room
//
// A row of n seats, 0 to n - 1. seat() seats a student where the distance to the closest
// student is largest, takes the lowest seat number on a tie, takes seat 0 in an empty room,
// and returns the seat. leave(p) empties seat p, which is guaranteed to hold a student. On
// LeetCode: 1 <= n <= 10^9, at most 10^4 calls.
//
// The free seats lie in gaps between neighbours. A gap is { left, right }: the seated
// students on each side, or the walls -1 and n at the ends of the row. Every gap sits in a
// TreeSet ordered by how far from everyone a student seated in it would be, best first,
// lower left on a tie, so seat() takes the first gap: its middle, or the wall seat for a gap
// that touches a wall. Seating splits that gap in two. Leaving joins the two gaps on either
// side of p, and the two neighbour maps find them: leftNeighbours maps a student to the
// left end of the gap to their left, rightNeighbours to the right end of the gap to their
// right. A free seat always gives a distance of 1 or more, so a gap with no free seat (its
// distance is 0) is never first while a seat is free.

public final class ExamRoom {
	// Time complexity: seat and leave O(log k), k = students seated: a few TreeSet and map
	// calls each, on k + 1 gaps
	// Auxiliary Space: O(k), one gap and two map entries per student

	private int n;
	private TreeSet<int[]> set; // GAPS: best seat first
	private Map<Integer, Integer> leftNeighbours; // right end of a gap -> its left end
	private Map<Integer, Integer> rightNeighbours; // left end of a gap -> its right end

	public ExamRoom(int n) {
		this.n = n;
		this.leftNeighbours = new HashMap<>();
		this.rightNeighbours = new HashMap<>();
		this.set = new TreeSet<>((a, b) -> compare(a, b));
		add(-1, n); // START: one gap, wall to wall
	}

	public int seat() {
		int[] segment = set.first(); // FIND: the gap that seats farthest from everyone
		int left = segment[0];
		int right = segment[1];
		int seat = (right + left) / 2; // MIDDLE of a gap between two students
		if (left == -1) {
			seat = 0; // WALL: the first seat, also for the empty room
		} else if (right == n) {
			seat = n - 1; // WALL: the last seat
		}
		remove(segment);
		add(left, seat); // SPLIT: the gap becomes two
		add(seat, right);
		return seat;
	}

	public void leave(int p) {
		int left = leftNeighbours.get(p); // FIND: the gaps on both sides of p
		int right = rightNeighbours.get(p);
		// remove gaps created by the leaving student
		remove(new int[] { left, p });
		remove(new int[] { p, right });
		add(left, right); // JOIN: one gap where there were two
	}

	// ORDER: larger distance first, then lower left; no two gaps share a left end, so no
	// two gaps compare equal, and remove() finds a gap by its two ends
	private int compare(int[] a, int[] b) {
		int da = dist(a);
		int db = dist(b);
		return da == db ? Integer.compare(a[0], b[0]) : Integer.compare(db, da);
	}

	// how far from everyone a student seated in this gap would be
	private int dist(int[] segment) {
		int left = segment[0];
		int right = segment[1];
		if (left == -1 || right == n) {
			return right - left - 1; // WALL: seated at the wall, the whole gap is the distance
		} else {
			return (right - left) / 2; // MIDDLE: half the gap, rounded down
		}
	}

	private void add(int left, int right) {
		set.add(new int[] { left, right });
		leftNeighbours.put(right, left); // LINK both ends of the gap to each other
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