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

public final class Node<T extends Comparable<? super T>> implements Comparable<Node<T>> {

  private final T data;
  private final ImmutableList<Node<T>> children;
  private final int hash;

  private Node(Builder<T> builder) {
    this.data = builder.data;
    this.children = transform(builder.children, b -> b.build());
    this.hash = hash(data, children);
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
      return checkNoBlanks(new Node<T>(this));
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
    return equalByComparison(this, object, true);
  }

  @Override
  public int compareTo(Node<T> other) {
    ComparisonChain chain = ComparisonChain.start();
    chain = chain.compare(data, other.data);
    chain = chain.compare(children, other.children, natural().lexicographical());
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