package com.svetanis.datastructures.graph.clone;

import java.util.ArrayList;
import java.util.List;

public final class Graph {

  private List<Node> nodes;

  public Graph() {
    this.nodes = new ArrayList<>();
  }

  public Graph(List<Node> nodes) {
    this.nodes = nodes;
  }

  public void addNode(Node node) {
    this.nodes.add(node);
  }

  public List<Node> getNodes() {
    return this.nodes;
  }
}
