package com.svetanis.datastructures.tree.trie.wordsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.svetanis.datastructures.tree.trie.Trie;

// 212. Word Search II
//
// Input: grid, an m x n grid of characters, and words, a list of distinct words. A word is on
// the grid if its characters can be traced through horizontally or vertically adjacent cells,
// using each cell at most once for that word. Returns every word of words that is on the grid,
// in no particular order. On LeetCode: 1 <= m, n <= 12, 1 <= words.length <= 3 * 10^4,
// 1 <= words[i].length <= 10, lowercase letters; the Trie used here accepts any characters.
//
// The one idea: put all the words in one Trie and walk the grid once. The walk carries the
// string spelled so far; at each cell it adds the cell's character and asks the Trie two
// questions: is this string the start of some word (startsWith), and if so is it a whole word
// (search)? "No" to the first cuts the path; "yes" to the second records the word. The cell is
// marked visited while its neighbours are tried and cleared on the way back.
//
// Time: O(w * L + m * n * 3^L * L) -- building the Trie reads every character of every word;
// the walk starts at each cell, has at most 3 neighbours to try at each step, for at most L
// steps, and each step asks the Trie about the whole string so far, which walks it from the
// root: O(L).
// Space: O(w * L) -- the Trie; the recursion adds O(L) frames and strings, and the result O(w).
//
// Siblings: WordSearchTrieSimple and WordSearchTriePruned do the same walk with a hand-made trie
// that is stepped one node per cell instead of asked about the whole string, so each step costs
// O(1); WordSearchBacktracking runs a separate search per word.
//
// Dependency: the Trie class it uses is the shared tree.trie.Trie, so this file compiles only
// with that class (and the library that class imports) on the classpath.

public final class WordSearchTrieStringCarried {

  public static List<String> search(char[][] grid, List<String> words) {
    int n = grid.length;
    int m = grid[0].length;
    boolean[][] visited = new boolean[n][m];
    Trie tree = Trie.build(words); // BUILD: one Trie of all the words
    Set<String> set = new HashSet<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) { // START: every cell
        dfs(grid, visited, "", i, j, tree, set);
      }
    }
    return new ArrayList<>(set);
  }

  private static void dfs(char[][] grid, boolean[][] visited, String str, int r, int c,
      Trie tree, Set<String> set) {
    int n = grid.length;
    int m = grid[0].length;
    if (!isValid(r, c, n, m)) { // PRUNE: off the grid
      return;
    }
    if (visited[r][c]) { // PRUNE: already on this path
      return;
    }
    str += grid[r][c]; // SPELL: add this cell's character
    if (!tree.startsWith(str)) { // PRUNE: no word starts with this string
      return;
    }

    if (tree.search(str)) {
      set.add(str); // RECORD: a whole word
    }

    visited[r][c] = true; // MARK: used for this path
    dfs(grid, visited, str, r - 1, c, tree, set); // TRY: up
    dfs(grid, visited, str, r + 1, c, tree, set); // TRY: down
    dfs(grid, visited, str, r, c - 1, tree, set); // TRY: left
    dfs(grid, visited, str, r, c + 1, tree, set); // TRY: right
    visited[r][c] = false; // UNMARK: another path may use the cell
  }

  // check whether matrix[i][j] is a valid cell or not
  private static boolean isValid(int i, int j, int n, int m) {
    if (i < 0 || j < 0 || i >= n || j >= m) {
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    char[][] matrix = { //
        { 'A', 'B', 'C', 'D' }, //
        { 'P', 'R', 'A', 'T' }, //
        { 'K', 'I', 'T', 'A' }, //
        { 'A', 'N', 'D', 'Y' }//
    };

    String[] a = { "ANDY", "CAT", "DOG" };
    List<String> list = search(matrix, Arrays.asList(a));
    System.out.println(list); // [CAT, ANDY] -- the order of a HashSet

    char[][] matrix2 = { //
        { 'T', 'R', 'A', 'P' }, //
        { 'C', 'A', 'R', 'D' }, //
        { 'F', 'A', 'C', 'T' }, //
        { 'D', 'A', 'R', 'T' }//
    };

    String[] a2 = { "CAT", "DOG", "FACT" };
    List<String> list2 = search(matrix2, Arrays.asList(a2));
    System.out.println(list2); // [FACT]

    char[][] matrix3 = { //
        { 'o', 'a', 'a', 'n' }, //
        { 'e', 't', 'a', 'e' }, //
        { 'i', 'h', 'k', 'r' }, //
        { 'i', 'f', 'l', 'v' } //
    };
    String[] a3 = { "oath", "pea", "eat", "rain" };
    System.out.println(search(matrix3, Arrays.asList(a3))); // [oath, eat]

    char[][] matrix4 = { //
        { 'a', 'b' }, //
        { 'c', 'd' } //
    };
    String[] a4 = { "abcb" };
    System.out.println(search(matrix4, Arrays.asList(a4))); // []

  }
}
