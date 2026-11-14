package com.svetanis.datastructures.tree.ternary;

import static com.svetanis.datastructures.tree.ternary.Node.isLeaf;
import static com.svetanis.datastructures.tree.ternary.Node.isNotNull;
import static com.svetanis.datastructures.tree.ternary.Node.isNull;
import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// All root-to-leaf paths of a tree whose nodes may have any number of children. Not a LeetCode
// problem; LeetCode 257 (Binary Tree Paths) is the two-child case.
//
// Input: root, the root of the tree (null for an empty tree). Returns one string per leaf, the
// values from the root down to that leaf joined with "->", the first child's leaves first. An
// empty tree has no paths; a single node is one path, its own value.
//
// The one idea: a root-to-leaf path is the path down to a node plus that node's own value, so
// walk down keeping the values seen so far in one list. At a leaf the list plus the leaf's value
// is a finished path: write it out. After each child comes back, take its value off the end of
// the list so the next child starts from the same prefix -- one list serves the whole walk.
//
// Time: O(n * h) -- each of the leaves joins a path of up to h values; the walk itself visits
// each node once.
// Space: O(h) for the list and the recursion, plus the output strings.

public final class AllRootToLeafPathTernary {

	private static final String SEPARATOR = "->";

	private static List<String> allRootToLeafPaths(Node<Integer> root) {
		List<Integer> path = new ArrayList<>();
		List<String> paths = new ArrayList<>();
		if (isNull(root)) { // BASE: an empty tree has no paths
			return new ArrayList<>();
		}
		dfs(root, path, paths);
		return paths;
	}

	private static void dfs(Node<Integer> root, List<Integer> path, List<String> paths) {
		if (isLeaf(root)) {
			path.add(root.getVal()); // TAKE: the leaf's value completes the path
			String joined = path.stream().map(String::valueOf).collect(Collectors.joining(SEPARATOR));
			paths.add(joined); // RECORD: one finished path
			path.remove(path.size() - 1); // UNDO: the leaf's value
			return;
		}
		for (Node<Integer> child : root.getChildren()) { // WALK: every child in order
			if (isNotNull(child)) {
				path.add(root.getVal()); // TAKE: this node's value for the child's walk
				dfs(child, path, paths);
				path.remove(path.size() - 1); // UNDO: so the next child starts from the same prefix
			}
		}
	}

	public static void main(String[] args) {
		String s = "1 3 2 1 3 0 4 0 6 0";
		String s2 = "7 3 2 1 5 0 3 0 4 0";
		Node<Integer> root = Node.build(s, i -> parseInt(i));
		Node<Integer> root2 = Node.build(s2, i -> parseInt(i));
		System.out.println(allRootToLeafPaths(root)); // [1->2->3, 1->4, 1->6]
		System.out.println(allRootToLeafPaths(root2)); // [7->2->5, 7->3, 7->4]
		System.out.println(allRootToLeafPaths(Node.build("5 0", i -> parseInt(i)))); // [5]
		System.out.println(allRootToLeafPaths(null)); // []
	}
}
