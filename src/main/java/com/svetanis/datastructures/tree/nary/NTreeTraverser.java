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

  ImmutableMultimap<T, T> traverse();

  ImmutableList<Node<T>> longestRootToLeafPath();

  ImmutableList<ImmutableList<Node<T>>> getRootToLeafPaths();
  
  ImmutableMultimap<T, ImmutableList<T>> rootToLeafPaths();

}
