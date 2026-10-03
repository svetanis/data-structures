package com.svetanis.datastructures.stack;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.google.common.collect.ImmutableList;

// 71. Simplify Path
//
// Input: an absolute Unix-style path. Returns its simplified form: one '/' between names, no
// "." or ".." left, and no '/' at the end.
//
// Same stack of directory names as SimplifyPath, but the TAIL of the deque is the top: names
// go in with offerLast and ".." takes one out with pollLast. Reading the deque from its head
// then gives the names root first, so nothing has to be reversed before the join.

public final class SimplifyPathSubmit {
	// Time Complexity: O(n), the path is split once and each piece is pushed or popped once
	// Space Complexity: O(n) for the pieces and the deque

	public static String simplify(String path) {
		Deque<String> stack = fill(path);
		return "/" + String.join("/", stack); // head to tail is root to deepest
	}

	private static Deque<String> fill(String str) {
		String[] segments = str.split("/");
		Deque<String> stack = new ArrayDeque<>();
		for (String segment : segments) {
			if (segment.isEmpty() || ".".equals(segment)) {
				continue;
			}
			if ("..".equals(segment)) {
				if (!stack.isEmpty()) { // ".." at the root stays at the root
					stack.pollLast();
				}
			} else {
				stack.offerLast(segment);
			}
		}
		return stack;
	}

	public static void main(String[] args) {
		List<String> list = getList();
		for (String str : list) {
			System.out.println(simplify(str));
		}
	}

	private static ImmutableList<String> getList() {
		List<String> list = newArrayList();
		list.add("/home/"); // "/home"
		list.add("/home//foo/"); // "/home/foo"
		list.add("/home/user/Documents/../Pictures"); // "/home/user/Pictures"
		list.add("/../"); // "/"
		list.add("/.../a/../b/c/../d/./"); // "/.../b/d
		list.add("/a/./b/../../c/");
		list.add("/a/..");
		list.add("/a/../");
		list.add("/../../../../../a");
		list.add("/a/./b/./c/./d/");
		list.add("/a/../.././../../.");
		list.add("/a//b//c///////d");
		return copyOf(list);
	}
}
