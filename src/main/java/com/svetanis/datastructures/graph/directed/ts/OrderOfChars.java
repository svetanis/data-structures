package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.datastructures.graph.directed.ts.TopologicalSorting.topologicalSort;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.graph.directed.Graph;

// Order of the letters of an alien alphabet, from a dictionary sorted in that alphabet.
//
// Input: words -- a dictionary sorted in the alien order, over the letters 'a' ... ('a' + k - 1)
// for a graph of k vertices (vertex i is the letter 'a' + i). It is assumed valid: no word comes
// directly after a longer word that it is a prefix of. sort(g) takes the graph made by
// createGraph, with the edge x -> y for each letter pair "x comes before y" the dictionary
// shows. Returns: all k letters in an order consistent with those pairs; throws
// IllegalArgumentException if the pairs contradict each other (a cycle).
//
// The one idea: two neighbouring words differ first at some position, and at that position the
// letter in the earlier word comes before the letter in the later word -- nothing else about the
// order can be read from the pair, since the letters after that position and the letters before it
// say nothing. Each such pair is an edge "x before y"; any order that puts every x before its y,
// a topological order of those edges, fits the dictionary.
//
// Time: O(L + k + E) -- L is the total length of the words (each neighbouring pair is compared up
// to its first difference), and the topological sort is O(k + E) for E edges.
// Space: O(k + E) -- the graph and the sort.
//
// Siblings: AlienDictionary, AlienOrder, AlienOrderSubmit and AlienOrderArrays in the alien
// package solve the same task as LeetCode 269 with Kahn's algorithm, and also reject the invalid
// dictionary this one assumes away; AlienDictionaryDfs is this same depth-first method on a map.

public final class OrderOfChars {

  public static List<Character> sort(Graph g) {
    List<Integer> list = topologicalSort(g);
    List<Character> letters = new ArrayList<>();
    for (int i : list) {
      letters.add((char) ('a' + i)); // LETTER: vertex i is the i-th letter
    }
    return letters;
  }

  private static void createGraph(String[] words, Graph g) {
    int len = words.length;
    for (int i = 0; i < len - 1; i++) {
      // take the current two words and
      // find the first mismatching char
      String w1 = words[i];
      String w2 = words[i + 1];
      for (int j = 0; j < min(w1.length(), w2.length()); j++) {
        // if we find a mismatching char, then add an edge
        // from char of w1 to that of w2
        if (w1.charAt(j) != w2.charAt(j)) {
          int v1 = w1.charAt(j) - 'a';
          int v2 = w2.charAt(j) - 'a';
          g.addEdge(v1, v2); // EDGE: v1 comes before v2
          break; // only the first difference tells anything
        }
      }
    }
  }

  public static void main(String[] args) {
    String[] words = { "caa", "aaa", "aab" };
    Graph g = new Graph(3);
    createGraph(words, g); // caa / aaa differ first at c, a: c before a; aaa / aab: a before b
    System.out.println(sort(g)); // [c, a, b]
  }
}
