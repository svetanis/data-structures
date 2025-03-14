package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 609. Find Duplicate File in System

public final class DuplicateFileInSystem {

	public static List<List<String>> duplicates(String[] paths) {
		Map<String, List<String>> map = contentToPath(paths);
		List<List<String>> list = new ArrayList<>();
		for (List<String> path : map.values()) {
			if (path.size() > 1) {
				list.add(path);
			}
		}
		return list;
	}

	private static Map<String, List<String>> contentToPath(String[] paths) {
		Map<String, List<String>> map = new HashMap<>();
		for (String path : paths) {
			String[] parts = path.split(" ");
			for (int i = 1; i < parts.length; i++) {
				String part = parts[i];
				int index = part.indexOf('(');
				String content = part.substring(index, part.length() - 1);
				String fp = parts[0] + "/" + part.substring(0, index);
				map.computeIfAbsent(content, k -> new ArrayList<>()).add(fp);
			}
		}
		return map;
	}

	public static void main(String[] args) {
		String s1 = "root/a 1.txt(abcd) 2.txt(efgh)";
		String s2 = "root/c 3.txt(abcd)";
		String s3 = "root/c/d 4.txt(efgh)";
		String s4 = "root 4.txt(efgh)";
		String[] paths = { s1, s2, s3, s4 };
		System.out.println(duplicates(paths));
		String[] paths2 = { s1, s2, s3 };
		System.out.println(duplicates(paths2));
	}
}