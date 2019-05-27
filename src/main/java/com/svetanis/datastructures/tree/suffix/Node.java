package com.svetanis.datastructures.tree.suffix;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.List;
import java.util.Map;

public final class Node {

  public char value;
  public List<Integer> indexes;
  public Map<Character, Node> children;

  public Node() {
    this.value = '0';
    this.indexes = newArrayList();
    this.children = newHashMap();
  }

  public void insert(String s, int index) {
    indexes.add(index);
    if (isNotBlank(s)) {
      value = s.charAt(0);
      Node child = null;
      if (children.containsKey(value)) {
        child = children.get(value);
      } else {
        child = new Node();
        children.put(value, child);
      }
      String remainder = s.substring(1);
      child.insert(remainder, index);
    }
  }

  public List<Integer> search(String s) {
    if (isBlank(s)) {
      return indexes;
    } else {
      char first = s.charAt(0);
      if (children.containsKey(first)) {
        String remainder = s.substring(1);
        return children.get(first).search(remainder);
      }
    }
    return newArrayList();
  }
}