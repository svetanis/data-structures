package com.svetanis.datastructures.tree.nary;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;

// The questions DefaultNTreeTraverser answers about an N-ary tree of Node<T>. Not a LeetCode
// problem. This file keeps Guava on purpose: the immutable lists, the Multimap and the Optional
// are the return types the interface promises.
//
// Input: a tree, given to the implementation's factory. size counts the nodes; height counts the
// edges on the longest path down from the root; contains and search look for a node whose data
// equals the key; bfs, preOrder and postOrder list the nodes in those orders; the root-to-leaf
// and root-to-node methods return paths from the root, as lists or as a Multimap from a node to
// its path.
//
// The one idea: separate what is asked of an N-ary tree from how it is computed, so that a
// different implementation (explicit stack, parent pointers) could answer the same questions.
//
// Time: O(1) -- an interface; each implementation states its own costs.
// Space: O(1) -- an interface holds nothing.
//
// Siblings: DefaultNTreeTraverser is the one implementation.

public interface NTreeTraverser<T extends Comparable<? super T>> {

  int size();

  int height();

  boolean contains(T key);

  Optional<Node<T>> search(T key);

  ImmutableList<Node<T>> bfs();

  ImmutableList<Node<T>> preOrder();

  ImmutableList<Node<T>> postOrder();

  ImmutableMultimap<Node<T>, Node<T>> rootToLeafPaths();

  ImmutableMultimap<Node<T>, Node<T>> rootToNodePaths();

  ImmutableMultimap<Node<T>, Node<T>> rootToNodePaths(Iterable<Node<T>> iterable);

  ImmutableList<Node<T>> rootToNodePath(Node<T> node);

  ImmutableList<Node<T>> longestRootToLeafPath();

}
