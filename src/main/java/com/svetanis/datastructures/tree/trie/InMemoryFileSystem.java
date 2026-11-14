package com.svetanis.datastructures.tree.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 588. Design In-Memory File System
//
// Input: calls on one file system that starts holding only the root directory "/". ls(path) lists
// what is in a directory in alphabetical order, or just the file's own name when path is a file;
// mkdir(path) makes a directory and every missing directory above it; addContentToFile(path,
// content) makes the file if it is missing and appends content to it; readContentFromFile(path)
// returns the file's content. On LeetCode: paths are absolute and start with '/', 1 <= path.length
// <= 100, 1 <= content.length <= 50, names are lowercase letters, no name is both a file and a
// directory, ls and readContentFromFile are called only on paths that exist, at most 300 calls.
//
// The one idea: a folder tree is a trie whose letters are whole names. One Node holds one name on a
// path: the files or folders directly inside it (children, by name), and, when it is a file, the
// text written to it. A path such as /a/b/c is the walk root -> a -> b -> c, so every call is one
// walk down the names of its path.
//
// Time: O(L) per mkdir, addContentToFile and readContentFromFile, O(L + k log k) per ls -- L is
// the length of the path (one step per name), k the number of names listed and sorted
// Space: O(total characters of all names and contents) -- one Node per distinct name on a path
//
// Siblings: none; MapSum and Trie in this package use the same walk with letters as the steps.

public final class InMemoryFileSystem {

  private Node root;

  public InMemoryFileSystem() {
    this.root = new Node();
  }

  public String readContentFromFile(String path) {
    Node node = root.search(path); // WALK to the file
    if (node != null && node.isFile) {
      return node.content.toString(); // RETURN: everything appended so far
    }
    return "";
  }

  public void addContentToFile(String path, String content) {
    Node node = root.insert(path, true); // WALK, creating the missing names, mark a file
    node.content.append(content); // APPEND to what the file already holds
  }

  public void mkdir(String path) {
    root.insert(path, false); // WALK, creating the missing names, as directories
  }

  public List<String> ls(String path) {
    List<String> list = new ArrayList<>();
    Node node = root.search(path); // WALK to the path
    if (node == null) {
      return list;
    }
    if (node.isFile) {
      list.add(node.name); // A FILE lists only its own name
    } else {
      list.addAll(node.children.keySet()); // A DIRECTORY lists what is inside it
    }
    Collections.sort(list); // SORT: alphabetical, as LeetCode asks
    return list;
  }

  public static void main(String[] args) {
    InMemoryFileSystem mfs = new InMemoryFileSystem();
    System.out.println(mfs.ls("/")); // []
    mfs.mkdir("/a/b/c");
    mfs.addContentToFile("/a/b/c/x", "hello");
    System.out.println(mfs.ls("/")); // [a]
    System.out.println(mfs.readContentFromFile("/a/b/c/x")); // hello
    mfs.addContentToFile("/a/b/c/x", " world");
    System.out.println(mfs.readContentFromFile("/a/b/c/x")); // hello world
    System.out.println(mfs.ls("/a/b/c")); // [x]
    System.out.println(mfs.ls("/a/b/c/x")); // [x]
  }

  private static class Node {
    private String name;
    private boolean isFile;
    private StringBuilder content = new StringBuilder();
    private Map<String, Node> children = new HashMap<>();

    public Node insert(String path, boolean isFile) {
      Node node = this;
      String[] fragments = path.split("/"); // fragments[0] is "" because the path starts with '/'
      for (int i = 1; i < fragments.length; i++) {
        String fragment = fragments[i];
        if (!node.children.containsKey(fragment)) {
          node.children.put(fragment, new Node()); // CREATE the missing name
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
          return null; // MISS: the path does not exist
        }
        node = node.children.get(fragment);
      }
      return node;
    }

  }
}
