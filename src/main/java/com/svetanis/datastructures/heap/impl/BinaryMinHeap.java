package com.svetanis.datastructures.heap.impl;

// A binary min heap of ints in a fixed-size array (no LeetCode number)
//
// BinaryMinHeap(size) makes an empty heap that holds at most size values. insert(value) adds a
// value; getMin() returns the smallest value held; removeMin() removes it; size(), isEmpty() and
// isFull() report how full it is; print() writes the values held, in array order. insert on a
// full heap and getMin or removeMin on an empty one throw IllegalStateException.
//
// The one idea: one array slot holds one value, and the slots 0..size - 1 are a complete binary
// tree filled row by row: slot i's children are slots 2i + 1 and 2i + 2, its parent is slot
// (i - 1) / 2. The heap rule is that no value is smaller than its parent, so the smallest value
// is always in slot 0. insert puts the new value in the first free slot, which keeps the tree
// complete, then swaps it up while it is smaller than its parent. removeMin moves the last value
// into slot 0, which also keeps the tree complete, then swaps it down with its smaller child
// while that child is smaller. Each swap fixes the rule between one parent and its children, and
// only along the one path the value travels.
//
// Time: insert and removeMin O(log n) -- one swap per level, and a complete tree of n values has
// about log n levels; getMin, size, isEmpty, isFull O(1); print O(n).
// Space: O(capacity) -- the array; the swaps recurse once per level, O(log n) deep.
//
// Siblings: BinaryMaxHeap (this package) is the same class with every comparison turned round;
// it throws IllegalArgumentException where this one throws IllegalStateException.

public final class BinaryMinHeap {

  private int[] data;
  private int size;

  public BinaryMinHeap(int size) {
    this.data = new int[size]; // CAPACITY: slots 0..size - 1
    this.size = 0; // EMPTY: no slot in use yet
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
    return 2 * index + 1; // LEFT child's slot
  }

  private int getRightChild(int index) {
    return 2 * index + 2; // RIGHT child's slot
  }

  private int getParent(int index) {
    return (index - 1) / 2; // PARENT's slot
  }

  public int getMin() {
    if (isEmpty()) {
      throw new IllegalStateException("heap underflow"); // EMPTY: nothing to read
    }
    return data[0]; // TOP: the smallest value is in slot 0
  }

  public void insert(int value) {
    if (isFull()) {
      throw new IllegalStateException("heap overflow"); // FULL: no free slot
    }
    size++;
    data[size - 1] = value; // APPEND in the first free slot: the tree stays complete
    reheapifyUp(size - 1); // SIFT UP to its place
  }

  private void reheapifyUp(int index) {
    if (index != 0) { // ROOT: nothing above to compare with
      int parent = getParent(index);
      if (data[parent] > data[index]) { // BROKEN: smaller than its parent
        swap(data, parent, index); // SWAP up one level
        reheapifyUp(parent);
      }
    }
  }

  public void removeMin() {
    if (isEmpty()) {
      throw new IllegalStateException("heap underflow"); // EMPTY: nothing to remove
    }
    data[0] = data[size - 1]; // MOVE the last value into the root
    size--;
    if (size > 0) {
      reheapifyDown(0); // SIFT DOWN to its place
    }
  }

  private void reheapifyDown(int index) {
    int minIndex;
    int leftChild = getLeftChild(index);
    int rightChild = getRightChild(index);
    if (rightChild >= size) {
      if (leftChild >= size) {
        return; // LEAF: no children
      } else {
        minIndex = leftChild; // ONLY CHILD: the left one
      }
    } else {
      boolean c = data[leftChild] <= data[rightChild];
      minIndex = c ? leftChild : rightChild; // SMALLER CHILD, the left one on a tie
    }
    if (data[index] > data[minIndex]) { // BROKEN: a child is smaller
      swap(data, minIndex, index); // SWAP down one level
      reheapifyDown(minIndex);
    }
  }

  private static void swap(int[] a, int i, int j) {
    int temp = a[i]; // SWAP a[i] and a[j]
    a[i] = a[j];
    a[j] = temp;
  }

  public void print() {
    for (int i = 0; i < size; i++) { // LIVE slots only: 0..size - 1
      System.out.print(data[i]);
      System.out.print(' ');
    }
    System.out.println();
  }

  @Override
  public String toString() {
    return "Heap Size: " + size;
  }

  public static void main(String[] args) {
    BinaryMinHeap heap = new BinaryMinHeap(5);
    heap.insert(3);
    heap.insert(9);
    heap.insert(5);
    heap.insert(1);
    System.out.println(heap.getMin()); // 1
    heap.removeMin();
    System.out.println(heap.getMin()); // 3
    heap.print(); // 3 9 5
    System.out.println(heap); // Heap Size: 3
  }
}
