package com.svetanis.datastructures.heap.impl;

import static com.svetanis.java.base.Exceptions.illegalArgument;
import static com.svetanis.java.base.utils.Swap.swap;

import com.svetanis.java.base.utils.Print;

public final class BinaryMaxHeap {

  private int[] data;
  private int size;

  public BinaryMaxHeap(int size) {
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

  private int leftChild(int index) {
    return 2 * index + 1;
  }

  private int rightChild(int index) {
    return 2 * index + 2;
  }

  private int parent(int index) {
    return (index - 1) / 2;
  }

  public int getMax() {
    if (isEmpty()) {
      throw illegalArgument("heap underflow");
    }
    return data[0];
  }

  public void insert(int value) {
    if (isFull()) {
      throw illegalArgument("heap overflow");
    }
    size++;
    data[size - 1] = value;
    reheapifyUp(size - 1);
  }

  private void reheapifyUp(int index) {
    if (index != 0) {
      int parent = parent(index);
      if (data[parent] < data[index]) {
        swap(data, parent, index);
        reheapifyUp(parent);
      }
    }
  }

  public void removeMax() {
    if (isEmpty()) {
      throw illegalArgument("heap underflow");
    }
    data[0] = data[size - 1];
    size--;
    if (size > 0) {
      reheapifyDown(0);
    }
  }

  private void reheapifyDown(int index) {
    int maxIndex;
    int leftChild = leftChild(index);
    int rightChild = rightChild(index);

    if (rightChild >= size) {
      if (leftChild >= size) {
        return;
      } else {
        maxIndex = leftChild;
      }
    } else {
      boolean c = data[leftChild] >= data[rightChild];
      maxIndex = c ? leftChild : rightChild;
    }
    if (data[index] < data[maxIndex]) {
      swap(data, maxIndex, index);
      reheapifyDown(maxIndex);
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
