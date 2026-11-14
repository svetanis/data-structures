package com.svetanis.datastructures.tree.nary;

// Helper for building a Node: adds one child builder to a node builder. Not a LeetCode problem.
//
// Input: addChild(node, child) takes two Node.Builder values and returns node, so calls can be
// chained. The child list of a builder is mutable until build() is called on it.
//
// The one idea: a Node is immutable once built, so a tree is assembled from builders first, each
// holding its children's builders, and built from the root down in one call.
//
// Time: O(1) -- one list add.
// Space: O(1).
//
// Siblings: Node holds the Builder this works on.

public final class Nodes {

  public static <T extends Comparable<? super T>> Node.Builder<T> addChild(Node.Builder<T> node, Node.Builder<T> child) {
    node.getChildren().add(child); // LINK: child under node
    return node;
  }

}
