package com.svetanis.datastructures.tree.nary;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Ordering.natural;
import static com.svetanis.java.base.Objects.equalByComparison;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.validate.Validation.checkNoBlanks;
import static java.util.Objects.hash;

import java.util.List;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.ProvidingBuilder;

// An immutable node of an N-ary tree: a value and an immutable list of child nodes. Not a
// LeetCode problem. This file keeps Guava and the java-base library on purpose: the builder, the
// reflective no-null check, the ImmutableList and the ordering are what the class is made of.
//
// Input: a Node is made from a Builder (Node.builder(), or Node.build(data, childBuilders)); the
// builder takes the value and the child builders, and build() builds the whole subtree. Every
// field must be non-null. Nodes compare by value: data first, then the children one by one, so
// two nodes with the same data and the same children are equal, wherever they sit in a tree.
//
// The one idea: a tree that cannot change after it is built can cache its hash code and be
// compared by value, so a node can be a key in a map or a set; the builders carry the mutable
// stage that the finished nodes leave out.
//
// Time: build O(size of the subtree) -- each node is built, checked and hashed once.
// compareTo and equals O(size of the smaller subtree). hashCode O(1) -- computed once, in the
// constructor.
// Space: O(size of the subtree).
//
// Siblings: Nodes adds a child to a builder; DefaultNTreeTraverser walks a built tree.

public final class Node<T extends Comparable<? super T>> implements Comparable<Node<T>> {

  private final T data;
  private final ImmutableList<Node<T>> children;
  private final int hash;

  private Node(Builder<T> builder) {
    this.data = builder.data;
    this.children = transform(builder.children, b -> b.build()); // BUILD: every child first
    this.hash = hash(data, children); // HASH: once, from the data and the finished children
  }

  public static <T extends Comparable<? super T>> Node<T> build(T data) {
    return build(data, newArrayList());
  }

  public static <T extends Comparable<? super T>> Node<T> build(T data, List<Node.Builder<T>> children) {
    Builder<T> builder = builder();
    builder.withData(data);
    builder.withChildren(children);
    return builder.build();
  }

  public static <T extends Comparable<? super T>> Builder<T> builder() {
    return new Builder<T>();
  }

  public static class Builder<T extends Comparable<? super T>> implements ProvidingBuilder<Node<T>> {

    private T data;
    private List<Node.Builder<T>> children = newArrayList();

    public Builder<T> withData(T data) {
      this.data = data;
      return this;
    }

    public Builder<T> withChildren(List<Node.Builder<T>> children) {
      this.children = children;
      return this;
    }

    public T getData() {
      return data;
    }

    public void setData(T data) {
      this.data = data;
    }

    public List<Node.Builder<T>> getChildren() {
      return children;
    }

    public void setChildren(List<Node.Builder<T>> children) {
      this.children = children;
    }

    @Override
    public Node<T> get() {
      return build();
    }

    @Override
    public Node<T> build() {
      return checkNoBlanks(new Node<T>(this)); // CHECK: no null field
    }
  }

  public T getData() {
    return data;
  }

  public ImmutableList<Node<T>> getChildren() {
    return children;
  }

  @Override
  public int hashCode() {
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    return equalByComparison(this, object, true); // EQUAL: same class, same hash, compareTo is 0
  }

  @Override
  public int compareTo(Node<T> other) {
    ComparisonChain chain = ComparisonChain.start();
    chain = chain.compare(data, other.data); // COMPARE: data first
    chain = chain.compare(children, other.children, natural().lexicographical()); // then the children
    return chain.result();
  }

  @Override
  public String toString() {
    ToStringHelper helper = toStringHelper(this);
    helper.add("data", data);
    helper.add("children", children);
    return helper.toString();
  }

}