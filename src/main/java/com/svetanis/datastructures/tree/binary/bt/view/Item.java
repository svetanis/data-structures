package com.svetanis.datastructures.tree.binary.bt.view;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Item: one queue entry for the column-numbered walks -- a tree node paired with its
// horizontal distance (column). The root is column 0, a left child is one column less than its
// parent, a right child one more. Used by BottomViewIterative, TopViewIterative and the
// diagonal and vertical walks.
//
// Time: O(1) per entry.
// Space: O(1) per entry.

public final class Item {
  public TreeNode node;
  public int hd;

  public Item(TreeNode node, int hd) {
    this.node = node;
		this.hd = hd;
  }
}