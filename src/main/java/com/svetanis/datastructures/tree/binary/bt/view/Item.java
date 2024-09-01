package com.svetanis.datastructures.tree.binary.bt.view;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class Item {
  public Node node;
  public int hd;

  public Item(Node node, int hd) {
    this.node = node;
		this.hd = hd;
  }
}