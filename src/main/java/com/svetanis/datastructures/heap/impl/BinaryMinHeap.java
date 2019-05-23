package com.svetanis.datastructures.heap.impl;

import static com.svetanis.java.base.Exceptions.illegalState;
import static com.svetanis.java.base.utils.Swap.swap;

import com.svetanis.java.base.utils.Print;

public final class BinaryMinHeap {

  private int[] data;
  private int size;

  public BinaryMinHeap(int size) {
    this.data = new int[size];
    this.size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean isFull() {
    return size == data.length;
  }

  public int size() {
    return size;
  }

  private int getLeftChild(int index) {
    return 2 * index + 1;
  }

  private int getRightChild(int index) {
    return 2 * index + 2;
  }

  private int getParent(int index) {
    return (index - 1) / 2;
  }

  public int getMin() {
    if (isEmpty()) {
      throw illegalState("heap underflow");
    }
    return data[0];
  }

  public void insert(int value) {
    if (isFull()) {
      throw illegalState("heap overflow");
    }
    size++;
    data[size - 1] = value;
    reheapifyUp(size - 1);
  }

  private void reheapifyUp(int index) {
    if (index != 0) {
      int parent = getParent(index);
      if (data[parent] > data[index]) {
        swap(data, parent, index);
        reheapifyUp(parent);
      }
    }
  }

  public void removeMin() {
    if (isEmpty()) {
      throw illegalState("heap underflow");
    }
    data[0] = data[size - 1];
    size--;
    if (size > 0) {
      reheapifyDown(0);
    }
  }

  private void reheapifyDown(int index) {
    int minIndex;
    int leftChild = getLeftChild(index);
    int rightChild = getRightChild(index);
    if (rightChild >= size) {
      if (leftChild >= size) {
        return;
      } else {
        minIndex = leftChild;
      }
    } else {
      boolean c = data[leftChild] <= data[rightChild];
      minIndex = c ? leftChild : rightChild;
    }
    if (data[index] > data[minIndex]) {
      swap(data, minIndex, index);
      reheapifyDown(minIndex);
    }
  }

  public void print() {
    Print.print(data);
  }

  @Override
  public String toString() {
    return "Heap Size: " + size;
  }
}
