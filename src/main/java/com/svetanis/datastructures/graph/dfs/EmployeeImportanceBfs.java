package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 690. Employee Importance
//
// Input: list -- the employees, each with an id, an importance value and the ids of its direct
// subordinates; id -- the employee to start from. Returns the importance of that employee plus
// the importance of everyone below them, direct or not. On LeetCode: 1 <= list.length <= 2000,
// ids are unique, -100 <= importance <= 100, each employee has at most one direct leader and
// every listed subordinate id exists.
//
// The one idea: one queue entry holds the id of one employee whose importance has not been added
// yet. Take an id off the front, add that employee's importance to the total, and put the ids of
// their direct subordinates at the back. The queue empties when everyone below the start has been
// added once. The reporting lines form a tree (one leader each), so no employee is queued twice
// and no mark is needed. A map from id to employee, built first, finds each employee in one step.
//
// Time: O(n) -- the map is built in one pass, and each employee is queued and added once.
// Space: O(n) -- the map and the queue; no recursion.
//
// Siblings: EmployeeImportance is the recursive form of the same sum.

public final class EmployeeImportanceBfs {

	public static int importance(List<Employee> list, int id) {
		Map<Integer, Employee> map = employees(list);
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(id);
		int total = 0;
		while(!dq.isEmpty()) {
			Employee e = map.get(dq.poll()); // WALK: next employee in the queue
			total += e.importance; // TAKE: this employee's own importance
			for(int sid : e.subordinates) {
				dq.offer(sid); // QUEUE: each direct subordinate, added later
			}
		}
		return total; // RETURN
	}

	private static Map<Integer, Employee> employees(List<Employee> list) {
		Map<Integer, Employee> map = new HashMap<>();
		for (Employee e : list) { // BUILD: id -> employee
			map.put(e.id, e);
		}
		return map;
	}

	public static void main(String[] args) {
		List<Employee> list = new ArrayList<>();
		list.add(new Employee(1, 5, Arrays.asList(2, 3)));
		list.add(new Employee(2, 3, Arrays.asList()));
		list.add(new Employee(3, 3, Arrays.asList()));
		System.out.println(importance(list, 1)); // 11

		List<Employee> list2 = new ArrayList<>();
		list2.add(new Employee(1, 2, Arrays.asList(5)));
		list2.add(new Employee(5, -3, Arrays.asList()));
		System.out.println(importance(list2, 5)); // -3
	}

	private static final class Employee {
		private int id;
		private int importance;
		private List<Integer> subordinates;

		public Employee(int id, int importance, List<Integer> subordinates) {
			this.id = id;
			this.importance = importance;
			this.subordinates = subordinates;
		}
	}
}
