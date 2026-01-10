package com.svetanis.datastructures.tree.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 588. Design In-Memory File System

public final class InMemoryFileSystem {

	private Node root ;

	public InMemoryFileSystem() {
		this.root = new Node();
	}

	public String readContentFromFile(String path) {
		Node node = root.search(path);
		if (node != null && node.isFile) {
			return node.content.toString();
		}
		return "";
	}

	public void addContentToFile(String path, String content) {
		Node node = root.insert(path, true);
		node.content.append(content);
	}

	public void mkdir(String path) {
		root.insert(path, false);
	}

	public List<String> ls(String path) {
		List<String> list = new ArrayList<>();
		Node node = root.search(path);
		if (node == null) {
			return list;
		}
		if (node.isFile) {
			list.add(node.name);
		} else {
			list.addAll(node.children.keySet());
		}
		Collections.sort(list);
		return list;
	}

	public static void main(String[] args) {
		InMemoryFileSystem mfs = new InMemoryFileSystem();
		mfs.mkdir("/a/b/c");
		mfs.addContentToFile("/a/b/c/x", "hello");
		System.out.println(mfs.ls("/a/b/c"));
		System.out.println(mfs.readContentFromFile("/a/b/c/x"));
	}

	private static class Node {
		private String name;
		private boolean isFile;
		private StringBuilder content = new StringBuilder();
		private Map<String, Node> children = new HashMap<>();

		public Node insert(String path, boolean isFile) {
			Node node = this;
			String[] fragments = path.split("/");
			for (int i = 1; i < fragments.length; i++) {
				String fragment = fragments[i];
				if (!node.children.containsKey(fragment)) {
					node.children.put(fragment, new Node());
				}
				node = node.children.get(fragment);
			}
			node.isFile = isFile;
			if (isFile) {
				node.name = fragments[fragments.length - 1];
			}
			return node;
		}

		public Node search(String path) {
			Node node = this;
			String[] fragments = path.split("/");
			for (int i = 1; i < fragments.length; i++) {
				String fragment = fragments[i];
				if (!node.children.containsKey(fragment)) {
					return null;
				}
				node = node.children.get(fragment);
			}
			return node;
		}

	}
}
