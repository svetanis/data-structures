package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 609. Find Duplicate File in System
//
// duplicates(paths) takes one string per directory: the directory path, then each of its
// files as name(content), all separated by single spaces, as in "root/a 1.txt(abcd) 2.txt(ef)".
// Returns the groups of two or more files with the same content, each file as its full path
// "directory/name"; groups and the files inside them in any order. On LeetCode:
// 1 <= paths.length <= 2 * 10^4, no two files in one directory share a name.
//
// Group by content: a map from content to the full paths that hold it. Split each string on
// spaces; the first piece is the directory, every later piece is one file, its name before
// the '(' and its content between the '(' and the final ')'. The key keeps the opening '(' --
// harmless, since every key has it. Then keep only the groups with more than one path.
//
// Siblings, same problem: none in this package.

public final class DuplicateFileInSystem {
	// Time complexity: O(c), c = characters in all the input strings -- each is split, cut
	// and hashed a constant number of times
	// Auxiliary Space: O(c) -- the map holds every content and every full path

	public static List<List<String>> duplicates(String[] paths) {
		Map<String, List<String>> map = contentToPath(paths);
		List<List<String>> list = new ArrayList<>();
		for (List<String> path : map.values()) {
			if (path.size() > 1) { // DUPLICATE?: a content held by two files or more
				list.add(path);
			}
		}
		return list;
	}

	private static Map<String, List<String>> contentToPath(String[] paths) {
		Map<String, List<String>> map = new HashMap<>(); // GROUP: content -> full paths
		for (String path : paths) {
			String[] parts = path.split(" "); // SPLIT: the directory, then one piece per file
			for (int i = 1; i < parts.length; i++) {
				String part = parts[i];
				int index = part.indexOf('('); // CUT: the name ends at '('
				String content = part.substring(index, part.length() - 1); // CONTENT: from '(' to before ')'
				String fp = parts[0] + "/" + part.substring(0, index); // PATH: directory/name
				map.computeIfAbsent(content, k -> new ArrayList<>()).add(fp); // JOIN: its content's group
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