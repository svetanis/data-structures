package com.svetanis.datastructures.graph.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 841. Keys and Rooms
//
// Input: rooms -- rooms[i] is the list of keys found in room i; a key is the number of the room it
// opens. Room 0 is open and you start in it. Returns true when every room can be entered. On
// LeetCode: n == rooms.length, 2 <= n <= 1000, 0 <= rooms[i].length <= 1000, the key counts add
// up to at most 3000, every key is in 0 .. n - 1, and no room lists the same key twice.
//
// The one idea: think of each room as a node and each key as an arrow to the room it opens. The
// set holds the rooms entered so far. One call of dfs stands for one room just entered: it adds
// the room to the set and then follows each key in it to a room not yet in the set. When the walk
// ends, the set holds exactly the rooms that can be reached from room 0, so all rooms can be
// visited when the set has n members.
//
// Time: O(N + E) -- every room is entered once and every key is looked at once.
// Space: O(N) -- the set, and a recursion as deep as the longest chain of keys (at most N).

public final class KeysAndRooms {

	public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
		int n = rooms.size();
		Set<Integer> visited = new HashSet<>();
		dfs(rooms, 0, visited);
		return visited.size() == n; // RETURN: every room reached
	}

	private static void dfs(List<List<Integer>> g, int src, Set<Integer> visited) {
		visited.add(src); // MARK: this room is entered
		for (int key : g.get(src)) {
			if (!visited.contains(key)) { // SKIP: that room was already entered
				dfs(g, key, visited); // WALK: use the key
			}
		}
	}

	public static void main(String[] args) {
		List<List<Integer>> list1 = new ArrayList<>();
		list1.add(asList(1));
		list1.add(asList(2));
		list1.add(asList(3));
		list1.add(asList());
		System.out.println(canVisitAllRooms(list1)); // true

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(asList(1, 3));
		list2.add(asList(3, 0, 1));
		list2.add(asList(2));
		list2.add(asList(0));
		System.out.println(canVisitAllRooms(list2)); // false
	}
}
