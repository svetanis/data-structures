package com.svetanis.datastructures.tree.ternary;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.ternary.Node.isLeaf;
import static com.svetanis.datastructures.tree.ternary.Node.isNotNull;
import static com.svetanis.datastructures.tree.ternary.Node.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;
import static java.lang.Integer.parseInt;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public final class AllRootToLeafPathTernary {

	private static ImmutableList<String> allRootToLeafPaths(Node<Integer> root) {
		List<Integer> path = newArrayList();
		List<String> paths = newArrayList();
		if (isNull(root)) {
			return newList();
		}
		dfs(root, path, paths);
		return newList(paths);
	}

	private static void dfs(Node<Integer> root, List<Integer> path, List<String> paths) {
		if (isLeaf(root)) {
			path.add(root.getVal());
			String joined = Joiner.on("->").join(path);
			paths.add(joined);
			path.remove(path.size() - 1);
			return;
		}
		for (Node<Integer> child : root.getChildren()) {
			if (isNotNull(child)) {
				path.add(root.getVal());
				dfs(child, path, paths);
				path.remove(path.size() - 1);
			}
		}
	}

	public static void main(String[] args) {
		String s = "1 3 2 1 3 0 4 0 6 0";
		String s2 = "7 3 2 1 5 0 3 0 4 0";
		Node<Integer> root = Node.build(s, i -> parseInt(i));
		Node<Integer> root2 = Node.build(s2, i -> parseInt(i));
		printLines(allRootToLeafPaths(root));
		System.out.println();
		printLines(allRootToLeafPaths(root2));
	}
}
