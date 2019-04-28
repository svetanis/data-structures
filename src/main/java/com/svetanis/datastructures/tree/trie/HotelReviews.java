package com.svetanis.datastructures.tree.trie;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.svetanis.java.base.Splitters.split;
import static com.svetanis.java.base.collect.Lists.sort;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;

public final class HotelReviews {

  public static ImmutableList<Integer> sortReviews(String good, List<String> list) {
    List<String> words = split('_', good);
    List<ImmutableList<String>> lists = transform(list, s -> split('_', s));
    Node root = Trie.build(words).getRoot();
    List<Rank> ranks = newArrayList();
    for (int i = 0; i < lists.size(); i++) {
      int score = 0;
      for (String str : lists.get(i)) {
        if (search(root, str)) {
          score++;
        }
      }
      ranks.add(new Rank(i, score));
    }
    return transform(sort(ranks), r -> r.index);
  }

  private static boolean search(Node root, String str) {
    Node node = root;
    for (char c : str.toCharArray()) {
      if (!node.children.containsKey(c)) {
        return false;
      }
      node = node.children.get(c);
    }
    return node != null && node.leaf;
  }

  public static void main(String[] args) {
    String good = "cool_ice_wifi";
    List<String> list = newArrayList("water_is_cool", "cold_ice_drink", "cool_wifi_speed");
    print(sortReviews(good, list));
  }

  private static class Rank implements Comparable<Rank> {
    int index;
    int score;

    public Rank(int index, int score) {
      this.index = index;
      this.score = score;
    }

    @Override
    public int compareTo(Rank other) {
      if (this.score == other.score) {
        return this.index - other.index;
      }
      return other.score - this.score;
    }

    @Override
    public String toString() {
      return index + ":" + score;
    }
  }
}
