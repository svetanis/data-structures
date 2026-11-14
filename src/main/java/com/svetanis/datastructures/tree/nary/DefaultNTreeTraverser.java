package com.svetanis.datastructures.tree.nary;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.graph.Traverser.forTree;
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

// Traversals, height, search and root-to-node paths over an immutable N-ary tree of Node<T>.
// Not a LeetCode problem. This file keeps Guava and the java-base library on purpose: the
// Traverser, the immutable lists and the Multimap return types are what the class offers.
//
// Input: newNTreeTraverser(root) wraps the root of a tree. size is the number of nodes; height
// is the number of edges on the longest path down from the root (a single node has height 0);
// contains and search look for a node whose data equals the key; preOrder, postOrder and bfs
// list the nodes in those orders; longestRootToLeafPath is the first longest path from the root
// to a leaf; rootToLeafPaths maps each leaf to the path that reaches it; rootToNodePath is the
// path from the root to one node, and rootToNodePaths maps each node to its path.
//
// The one idea: a node is a value and the list of its children, each child the root of a smaller
// tree, so every question about the tree is a question about the node plus the same question
// about each child. The three orders come from Guava's Traverser; the path questions walk
// down keeping the nodes of the current path in one list and take the last one off on the way
// back, so one list serves the whole walk.
//
// Nodes are compared by value (data, then children). Two equal subtrees are therefore two equal
// nodes: every traversal lists both of them, and the two Multimaps, which use a node as the
// key, give them one key.
//
// Time: size, height, contains, search, preOrder, postOrder, bfs O(n) -- every node once.
// rootToLeafPaths and longestRootToLeafPath O(n * h) -- a path of up to h nodes copied per
// leaf. rootToNodePath O(n); rootToNodePaths O(n^2) -- one search per node.
// Space: O(n) for a traversal's list, O(h) recursion stack, O(n * h) for the path maps.
//
// Siblings: MaxDepthNaryTree (559) measures the same longest path in nodes, one more than the
// height here, on LeetCode's own Node; PostOrderTraversal and PreOrderTraversal (590) walk with
// an explicit stack instead of Guava's Traverser.

public class DefaultNTreeTraverser<T extends Comparable<? super T>> implements NTreeTraverser<T> {

  public static <T extends Comparable<? super T>> NTreeTraverser<T> newNTreeTraverser(Node<T> root) {
    return new DefaultNTreeTraverser<T>(root);
  }

  private DefaultNTreeTraverser(Node<T> root) {
    this.root = checkNotNull(root, "root");
    // TREE: forTree keeps no visited set, so equal nodes are not skipped; forGraph would skip them
    this.traverser = checkNotNull(forTree(r -> r.getChildren()), "traverser");
  }

  private final Node<T> root;
  private final Traverser<Node<T>> traverser;

  @Override
  public int size() {
    return preOrder().size(); // COUNT: the nodes in any order
  }

  @Override
  public int height() {
    return height(root);
  }

  private int height(Node<T> root) {
    if (root.getChildren().size() == 0) { // BASE: a leaf is height 0
      return 0;
    }

    int max = MIN_VALUE;
    for (Node<T> node : root.getChildren()) { // WALK: every child
      max = max(max, height(node));
    }
    return max + 1; // RETURN: one edge more than the tallest child
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
    if (node.getData().equals(key)) { // FOUND: this node holds the key
      return of(node);
    }
    for (Node<T> child : node.getChildren()) { // WALK: every child's subtree
      Optional<Node<T>> found = search(child, key); // SEARCH: the matching node itself
      if (found.isPresent()) {
        return found;
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
    for (List<Node<T>> path : getRootToLeafPaths()) { // WALK: every root-to-leaf path
      if (path.size() > max) { // KEEP: the first longest one
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
      multi.putAll(leaf, path); // GROUP: the path under its leaf
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
    path.add(node); // TAKE: this node joins the current path
    if (isLeaf(node)) {
      paths.add(newList(path)); // RECORD: a copy, since the list keeps changing
    }
    for (Node<T> child : node.getChildren()) { // WALK: every child
      rootToLeafPath(child, path, paths);
    }
    // backtrack
    int index = path.indexOf(node);
    for (int i = index; i < path.size(); i++) {
      path.remove(index); // UNDO: this node leaves the current path
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
      multi.putAll(node, rootToNodePath(node)); // GROUP: each node's path under the node
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
    path.add(root); // TAKE: this node joins the current path
    if (root.equals(node)) { // FOUND: the path ends here
      return true;
    }
    for (Node<T> child : root.getChildren()) { // WALK: every child
      if (isPath(child, node, path)) {
        return true;
      }
    }
    path.remove(path.size() - 1); // UNDO: not on the way to the node
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
    preOrder.add(node); // TAKE: the node before its children
    for (Node<T> child : node.getChildren()) {
      buildPreOrder(child, preOrder);
    }
  }

  @Deprecated
  private void buildPostOrder(Node<T> node, List<Node<T>> postOrder) {
    for (Node<T> child : node.getChildren()) {
      buildPostOrder(child, postOrder);
    }
    postOrder.add(node); // TAKE: the node after its children
  }

}
