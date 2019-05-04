package com.svetanis.datastructures.graph.floodfill;

import static com.svetanis.java.base.utils.Print.print;

public final class FloodFill {

  public static void floodFill(int[][] matrix, int x, int y, int c) {
    int old = matrix[x][y];
    fill(matrix, x, y, old, c);
  }

  public static void fill(int[][] matrix, int x, int y, int old, int c) {

    int n = matrix.length;
    int m = matrix[0].length;

    if (x < 0 || x >= n || y >= m || y < 0) {
      return;
    }

    if (matrix[x][y] != old) {
      return;
    }

    matrix[x][y] = c;
    fill(matrix, x + 1, y, old, c);
    fill(matrix, x - 1, y, old, c);
    fill(matrix, x, y + 1, old, c);
    fill(matrix, x, y - 1, old, c);
  }

  public static void main(String[] args) {
    int[][] matrix = { //
        { 1, 1, 1, 1, 1, 1, 1, 1 }, //
        { 1, 1, 1, 1, 1, 1, 0, 0 }, //
        { 1, 0, 0, 1, 1, 0, 1, 1 }, //
        { 1, 2, 2, 2, 2, 0, 1, 0 }, //
        { 1, 1, 1, 2, 2, 0, 1, 0 }, //
        { 1, 1, 1, 2, 2, 2, 2, 0 }, //
        { 1, 1, 1, 1, 1, 2, 1, 1 }, //
        { 1, 1, 1, 1, 1, 2, 2, 1 }, //
    };//

    floodFill(matrix, 4, 4, 3);
    print(matrix);
  }
}
