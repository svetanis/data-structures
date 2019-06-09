package com.svetanis.datastructures.tree.nary;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.graph.Traverser.forGraph;
import static com.svetanis.java.base.Preconditions.checkNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Multimaps.newMultimap;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.graph.Traverser;

public class DefaultNTreeTraverser<T extends Comparable<? super T>> implements NTreeTraverser<T> {

  public static <T extends Comparable<? super T>> NTreeTraverser<T> newNTreeTraverser(Node<T> root) {
    return new DefaultNTreeTraverser<T>(root);
  }

  private DefaultNTreeTraverser(Node<T> root) {
    this.root = checkNotNull(root, "root");
    this.traverser = checkNotNull(forGraph(r -> r.getChildren()), "traverser");
  }

  private final Node<T> root;
  private final Traverser<Node<T>> traverser;

  @Override
  public int size() {
    return preOrder().size();
  }

  @Override
  public int height() {
    return height(root);
  }

  private int height(Node<T> root) {
    if (root.getChildren().size() == 0) {
      return 0;
    }

    int max = MIN_VALUE;
    for (Node<T> node : root.getChildren()) {
      max = max(max, height(node));
    }
    return max + 1;
  }

  @Override
  public boolean contains(T key) {
    return contains(root, key);
  }

  private boolean contains(Node<T> node, T key) {
    return search(node, key).isPresent();
  }

  @Override
  public Optional<Node<T>> search(T key) {
    return search(root, key);
  }

  private Optional<Node<T>> search(Node<T> node, T key) {
    if (node == null) {
      return absent();
    }
    if (node.getData().equals(key)) {
      return of(node);
    }
    for (Node<T> child : node.getChildren()) {
      if (search(child, key).isPresent()) {
        return of(child);
      }
    }
    return absent();
  }

  @Override
  public ImmutableList<Node<T>> preOrder() {
    return newList(traverser.depthFirstPreOrder(root));
  }

  @Override
  public ImmutableList<Node<T>> postOrder() {
    return newList(traverser.depthFirstPostOrder(root));
  }

  @Override
  public ImmutableList<Node<T>> bfs() {
    return newList(traverser.breadthFirst(root));
  }

  @Override
  public ImmutableList<Node<T>> longestRootToLeafPath() {
    int max = 0;
    List<Node<T>> list = newArrayList();
    for (List<Node<T>> path : getRootToLeafPaths()) {
      if (path.size() > max) {
        max = path.size();
        list = path;
      }
    }
    return newList(list);
  }

  @Override
  public ImmutableMultimap<Node<T>, Node<T>> rootToLeafPaths() {
    Multimap<Node<T>, Node<T>> multi = LinkedHashMultimap.create();
    List<ImmutableList<Node<T>>> paths = getRootToLeafPaths();
    for (List<Node<T>> path : paths) {
      Node<T> leaf = path.get(path.size() - 1);
      multi.putAll(leaf, path);
    }
    return newMultimap(multi);
  }

  private ImmutableList<ImmutableList<Node<T>>> getRootToLeafPaths() {
    List<ImmutableList<Node<T>>> paths = newArrayList();
    List<Node<T>> path = newArrayList();
    rootToLeafPath(root, path, paths);
    return newList(paths);
  }

  private void rootToLeafPath(Node<T> node, List<Node<T>> path, List<ImmutableList<Node<T>>> paths) {
    path.add(node);
    if (isLeaf(node)) {
      paths.add(newList(path));
    }
    for (Node<T> child : node.getChildren()) {
      rootToLeafPath(child, path, paths);
    }
    // backtrack
    int index = path.indexOf(node);
    for (int i = index; i < path.size(); i++) {
      path.remove(index);
    }
  }

  private boolean isLeaf(Node<T> node) {
    return node.getChildren().size() == 0;
  }

  @Override
  public ImmutableMultimap<Node<T>, Node<T>> rootToNodePaths() {
    List<Node<T>> preOrder = preOrder();
    return rootToNodePaths(preOrder);
  }

  @Override
  public ImmutableMultimap<Node<T>, Node<T>> rootToNodePaths(Iterable<Node<T>> iterable) {
    Multimap<Node<T>, Node<T>> multi = LinkedHashMultimap.create();
    for (Node<T> node : iterable) {
      multi.putAll(node, rootToNodePath(node));
    }
    return newMultimap(multi);
  }

  @Override
  public ImmutableList<Node<T>> rootToNodePath(Node<T> node) {
    List<Node<T>> list = newArrayList();
    isPath(root, node, list);
    return newList(list);
  }

  private boolean isPath(Node<T> root, Node<T> node, List<Node<T>> path) {
    path.add(root);
    if (root.equals(node)) {
      return true;
    }
    for (Node<T> child : root.getChildren()) {
      if (isPath(child, node, path)) {
        return true;
      }
    }
    path.remove(path.size() - 1);
    return false;
  }

  @Deprecated
  public ImmutableList<Node<T>> getPreOrderTraversal() {
    List<Node<T>> preOrder = new ArrayList<>();
    buildPreOrder(root, preOrder);
    return newList(preOrder);
  }

  @Deprecated
  public ImmutableList<Node<T>> getPostOrderTraversal() {
    List<Node<T>> postOrder = new ArrayList<>();
    buildPostOrder(root, postOrder);
    return newList(postOrder);
  }

  @Deprecated
  private void buildPreOrder(Node<T> node, List<Node<T>> preOrder) {
    preOrder.add(node);
    for (Node<T> child : node.getChildren()) {
      buildPreOrder(child, preOrder);
    }
  }

  @Deprecated
  private void buildPostOrder(Node<T> node, List<Node<T>> postOrder) {
    for (Node<T> child : node.getChildren()) {
      buildPostOrder(child, postOrder);
    }
    postOrder.add(node);
  }

}
