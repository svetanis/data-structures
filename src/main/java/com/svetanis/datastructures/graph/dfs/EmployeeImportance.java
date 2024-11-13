package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 690. Employee Importance

public final class EmployeeImportance {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int importance(List<Employee> list, int id) {
		Map<Integer, Employee> map = employees(list);
		return dfs(id, map);
	}

	private static int dfs(int id, Map<Integer, Employee> map) {
		Employee e = map.get(id);
		int sum = e.importance;
		for (int sid : e.subordinates) {
			sum += dfs(sid, map);
		}
		return sum;
	}

	private static Map<Integer, Employee> employees(List<Employee> list) {
		Map<Integer, Employee> map = new HashMap<>();
		for (Employee e : list) {
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
