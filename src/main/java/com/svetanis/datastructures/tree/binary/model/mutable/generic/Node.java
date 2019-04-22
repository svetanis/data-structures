package com.svetanis.datastructures.tree.binary.model.mutable.generic;

import static com.svetanis.java.base.Objects.equalByComparison;
import static com.svetanis.java.base.validate.Validation.checkNoBlanks;
import static java.util.Objects.hash;

import com.google.common.collect.ComparisonChain;

public final class Node<T extends Comparable<? super T>> implements Comparable<Node<T>> {

  public T data;
  public Node<T> left;
  public Node<T> right;
  private int hash;

  public static <T extends Comparable<? super T>> Node<T> newNode(T data) {
    return new Node<>(data);
  }

  public Node(T data) {
    this(validate(data), null, null);
  }

  public Node(T data, Node<T> left, Node<T> right) {
    this.data = validate(data);
    this.left = left;
    this.right = right;
    this.hash = hash(data, left, right);
  }

  private static <T> T validate(T data) {
    return checkNoBlanks(data);
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
    chain = chain.compare(left, other.left);
    chain = chain.compare(right, other.right);
    return chain.result();
  }

  @Override
  public String toString() {
    return data.toString();
  }

}