package com.svetanis.datastructures.heap.impl;

// A binary max heap of ints in a fixed-size array (no LeetCode number)
//
// BinaryMaxHeap(size) makes an empty heap that holds at most size values. insert(value) adds a
// value; getMax() returns the largest value held; removeMax() removes it; size(), isEmpty() and
// isFull() report how full it is; print() writes the values held, in array order. insert on a
// full heap and getMax or removeMax on an empty one throw IllegalArgumentException.
//
// The one idea: one array slot holds one value, and the slots 0..size - 1 are a complete binary
// tree filled row by row: slot i's children are slots 2i + 1 and 2i + 2, its parent is slot
// (i - 1) / 2. The heap rule is that no value is larger than its parent, so the largest value is
// always in slot 0. insert puts the new value in the first free slot, which keeps the tree
// complete, then swaps it up while it is larger than its parent. removeMax moves the last value
// into slot 0, which also keeps the tree complete, then swaps it down with its larger child while
// that child is larger. Each swap fixes the rule between one parent and its children, and only
// along the one path the value travels.
//
// Time: insert and removeMax O(log n) -- one swap per level, and a complete tree of n values has
// about log n levels; getMax, size, isEmpty, isFull O(1); print O(n).
// Space: O(capacity) -- the array; the swaps recurse once per level, O(log n) deep.
//
// Siblings: BinaryMinHeap (this package) is the same class with every comparison turned round.

public final class BinaryMaxHeap {

  private int[] data;
  private int size;

  public BinaryMaxHeap(int size) {
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

  private int leftChild(int index) {
    return 2 * index + 1; // LEFT child's slot
  }

  private int rightChild(int index) {
    return 2 * index + 2; // RIGHT child's slot
  }

  private int parent(int index) {
    return (index - 1) / 2; // PARENT's slot
  }

  public int getMax() {
    if (isEmpty()) {
      throw new IllegalArgumentException("heap underflow"); // EMPTY: nothing to read
    }
    return data[0]; // TOP: the largest value is in slot 0
  }

  public void insert(int value) {
    if (isFull()) {
      throw new IllegalArgumentException("heap overflow"); // FULL: no free slot
    }
    size++;
    data[size - 1] = value; // APPEND in the first free slot: the tree stays complete
    reheapifyUp(size - 1); // SIFT UP to its place
  }

  private void reheapifyUp(int index) {
    if (index != 0) { // ROOT: nothing above to compare with
      int parent = parent(index);
      if (data[parent] < data[index]) { // BROKEN: larger than its parent
        swap(data, parent, index); // SWAP up one level
        reheapifyUp(parent);
      }
    }
  }

  public void removeMax() {
    if (isEmpty()) {
      throw new IllegalArgumentException("heap underflow"); // EMPTY: nothing to remove
    }
    data[0] = data[size - 1]; // MOVE the last value into the root
    size--;
    if (size > 0) {
      reheapifyDown(0); // SIFT DOWN to its place
    }
  }

  private void reheapifyDown(int index) {
    int maxIndex;
    int leftChild = leftChild(index);
    int rightChild = rightChild(index);

    if (rightChild >= size) {
      if (leftChild >= size) {
        return; // LEAF: no children
      } else {
        maxIndex = leftChild; // ONLY CHILD: the left one
      }
    } else {
      boolean c = data[leftChild] >= data[rightChild];
      maxIndex = c ? leftChild : rightChild; // LARGER CHILD, the left one on a tie
    }
    if (data[index] < data[maxIndex]) { // BROKEN: a child is larger
      swap(data, maxIndex, index); // SWAP down one level
      reheapifyDown(maxIndex);
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
    BinaryMaxHeap heap = new BinaryMaxHeap(5);
    heap.insert(3);
    heap.insert(9);
    heap.insert(5);
    heap.insert(1);
    System.out.println(heap.getMax()); // 9
    heap.removeMax();
    System.out.println(heap.getMax()); // 5
    heap.print(); // 5 3 1
    System.out.println(heap); // Heap Size: 3
  }
}
