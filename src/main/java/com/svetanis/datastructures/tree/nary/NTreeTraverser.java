package com.svetanis.datastructures.tree.nary;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;

public interface NTreeTraverser<T extends Comparable<? super T>> {

  int size();

  int getHeight();

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
