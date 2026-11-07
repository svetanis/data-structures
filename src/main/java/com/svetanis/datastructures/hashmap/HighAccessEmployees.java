package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2933. High-Access Employees
//
// hae takes a list of accesses, each [employee name, time as "HHMM" on a 24-hour clock],
// all on one day, and returns the names of the employees who accessed the system three or
// more times within one hour, in any order. Two times exactly 60 minutes apart are not in
// the same hour, and the hour does not wrap around midnight ("0005" and "2350" are far
// apart). On LeetCode: 1 <= access_times.length <= 100.
//
// Turn each time into minutes since midnight and group them by employee. Three accesses
// fit inside one hour exactly when the earliest and the latest of them are under 60
// minutes apart, and once an employee's minutes are sorted, the closest three are always
// three in a row: so it is enough to check times[i] - times[i - 2] < 60 for every i.

public final class HighAccessEmployees {
	// Time complexity: O(n log n), sorting each employee's minutes; all lists together hold n
	// Auxiliary Space: O(n), every access time stored once in the map

	public static List<String> hae(List<List<String>> accessTimes) {
		Map<String, List<Integer>> map = access(accessTimes);
		List<String> list = new ArrayList<>();
		for (String emp : map.keySet()) {
			List<Integer> times = map.get(emp);
			if (times.size() < 3) {
				continue; // SKIP: fewer than three accesses in the whole day
			}
			if (highAccess(times)) {
				list.add(emp);
			}
		}
		return list;
	}

	private static boolean highAccess(List<Integer> times) {
		Collections.sort(times); // SORT: the closest three accesses are now neighbours
		for (int i = 2; i < times.size(); i++) {
			int prev = times.get(i - 2);
			int curr = times.get(i);
			if (curr - prev < 60) {
				return true; // FOUND: three accesses, first to last under an hour
			}
		}
		return false;
	}

	private static Map<String, List<Integer>> access(List<List<String>> times) {
		Map<String, List<Integer>> map = new HashMap<>();
		for (List<String> time : times) {
			String emp = time.get(0);
			int minutes = minutes(time.get(1));
			map.computeIfAbsent(emp, e -> new ArrayList<>()).add(minutes); // GROUP by employee
		}
		return map;
	}

	// "HHMM" -> minutes since midnight
	private static int minutes(String s) {
		int hour = Integer.parseInt(s.substring(0, 2));
		int mins = Integer.parseInt(s.substring(2));
		return hour * 60 + mins;
	}

	public static void main(String[] args) {
		List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("a", "0549"));
		list.add(Arrays.asList("b", "0457"));
		list.add(Arrays.asList("a", "0532"));
		list.add(Arrays.asList("a", "0621"));
		list.add(Arrays.asList("b", "0540"));
		System.out.println(hae(list)); // a

		List<List<String>> list1 = new ArrayList<>();
		list1.add(Arrays.asList("d", "0002"));
		list1.add(Arrays.asList("c", "0808"));
		list1.add(Arrays.asList("c", "0829"));
		list1.add(Arrays.asList("e", "0215"));
		list1.add(Arrays.asList("d", "1508"));
		list1.add(Arrays.asList("d", "1444"));
		list1.add(Arrays.asList("d", "1410"));
		list1.add(Arrays.asList("c", "0809"));
		System.out.println(hae(list1)); // c, d

		List<List<String>> list2 = new ArrayList<>();
		list2.add(Arrays.asList("cd", "1025"));
		list2.add(Arrays.asList("ab", "1025"));
		list2.add(Arrays.asList("cd", "1046"));
		list2.add(Arrays.asList("cd", "1055"));
		list2.add(Arrays.asList("ab", "1124"));
		list2.add(Arrays.asList("ab", "1120"));
		System.out.println(hae(list2)); // ab, cd

		List<List<String>> list3 = new ArrayList<>();
		list3.add(Arrays.asList("ff", "1508"));
		list3.add(Arrays.asList("ff", "1508"));
		list3.add(Arrays.asList("ff", "1516"));
		System.out.println(hae(list3)); // ff

		List<List<String>> list4 = new ArrayList<>();
		list4.add(Arrays.asList("eazbkekis", "1034"));
		list4.add(Arrays.asList("relf", "1126"));
		list4.add(Arrays.asList("afwpabwyds", "1114"));
		list4.add(Arrays.asList("afwpabwyds", "1105"));
		list4.add(Arrays.asList("relf", "1031"));
		list4.add(Arrays.asList("afwpabwyds", "1010"));
		list4.add(Arrays.asList("vzqpz", "1047"));
		list4.add(Arrays.asList("relf", "1103"));
		System.out.println(hae(list4)); // relf
	}
}