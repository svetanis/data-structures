package com.svetanis.datastructures.tree.binary.bt.view;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

class Item {
  Node node;
  int hDist;

  public Item(Node node, int hDist) {
    this.node = node;
    this.hDist = hDist;
  }
}