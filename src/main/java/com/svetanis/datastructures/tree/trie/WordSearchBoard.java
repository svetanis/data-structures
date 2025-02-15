package com.svetanis.datastructures.tree.trie;

import static com.svetanis.java.base.collect.Lists.newList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.svetanis.java.base.utils.Print;

public final class WordSearchBoard {

  public static List<String> search(char[][] grid, List<String> words) {
    int n = grid.length;
    int m = grid[0].length;
    boolean[][] visited = new boolean[n][m];
    Trie tree = Trie.build(words);
    Set<String> set = new HashSet<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        dfs(grid, visited, "", i, j, tree, set);
      }
    }
    return newList(set);
  }

  private static void dfs(char[][] grid, boolean[][] visited, String str, int r, int c, Trie tree, Set<String> set) {
    int n = grid.length;
    int m = grid[0].length;
    if (!isValid(r, c, n, m)) {
      return;
    }
    if (visited[r][c]) {
      return;
    }
    str += grid[r][c];
    if (!tree.startsWith(str)) {
      return;
    }

    if (tree.search(str)) {
      set.add(str);
    }

    visited[r][c] = true;
    dfs(grid, visited, str, r - 1, c, tree, set);
    dfs(grid, visited, str, r + 1, c, tree, set);
    dfs(grid, visited, str, r, c - 1, tree, set);
    dfs(grid, visited, str, r, c + 1, tree, set);
    visited[r][c] = false;
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
    Print.print(list);

    // ANDY 3 0
    // CAT 0 2
    // DOG -1 -1

    char[][] matrix2 = { //
        { 'T', 'R', 'A', 'P' }, //
        { 'C', 'A', 'R', 'D' }, //
        { 'F', 'A', 'C', 'T' }, //
        { 'D', 'A', 'R', 'T' }//
    };

    String[] a2 = { "CAT", "DOG", "FACT" };
    List<String> list2 = search(matrix2, Arrays.asList(a2));
    Print.print(list2);

    // CAT 2 2
    // DOG -1 -1
    // FACT 2 0
    
    
    char[][] matrix3 = { //
        { 'o', 'a', 'a', 'n' }, //
        { 'e', 't', 'a', 'e' }, //
        { 'i', 'h', 'k', 'r' }, //
        { 'i', 'f', 'l', 'v' } //
    };
    String[] a3 = { "oath", "pea", "eat", "rain" };
    System.out.println(search(matrix3, Arrays.asList(a3))); // [eat, oath]

    char[][] matrix4 = { //
        { 'a', 'b' }, //
        { 'c', 'd' } //
    };
    String[] a4 = { "abcb" };
    System.out.println(search(matrix4, Arrays.asList(a4)));

  }
}
