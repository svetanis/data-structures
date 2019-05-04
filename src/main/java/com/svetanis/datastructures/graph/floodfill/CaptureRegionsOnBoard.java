package com.svetanis.datastructures.graph.floodfill;

public final class CaptureRegionsOnBoard {
  
  public static void floodFill(char[][] matrix) {

    int n = matrix.length;
    int m = matrix[0].length;

    // replace all 'O' with '-'
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (matrix[i][j] == 'O') {
          matrix[i][j] = '-';
        }
      }
    }

    // call floodFill for all '-' lying on edges
    for (int i = 0; i < n; i++) {
      if (matrix[i][0] == '-') {
        fill(matrix, i, 0, '-', 'O');
      }
    }

    for (int i = 0; i < n; i++) {
      if (matrix[i][m - 1] == '-') {
        fill(matrix, i, m - 1, '-', 'O');
      }
    }

    for (int i = 0; i < m; i++) {
      if (matrix[0][i] == '-') {
        fill(matrix, 0, i, '-', 'O');
      }
    }

    for (int i = 0; i < m; i++) {
      if (matrix[n - 1][i] == '-') {
        fill(matrix, n - 1, i, '-', 'O');
      }
    }

    // replace all '-' with 'X'
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (matrix[i][j] == '-') {
          matrix[i][j] = 'X';
        }
      }
    }
  }

  private static void fill(char[][] matrix, int x, int y, char old, char c) {

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

  private static void printMatrix(char[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void main(String[] args) {
    char[][] matrix1 = { //
        { 'X', 'O', 'X', 'X', 'X', 'X' }, //
        { 'X', 'O', 'X', 'X', 'O', 'X' }, //
        { 'X', 'X', 'X', 'O', 'O', 'X' }, //
        { 'O', 'X', 'X', 'X', 'X', 'X' }, //
        { 'X', 'X', 'X', 'O', 'X', 'O' }, //
        { 'O', 'O', 'X', 'O', 'O', 'O' }, //
    };//

    floodFill(matrix1);
    printMatrix(matrix1);

    System.out.println();

    char[][] matrix2 = { //
        { 'X', 'X', 'X', 'X' }, //
        { 'X', 'O', 'X', 'X' }, //
        { 'X', 'O', 'O', 'X' }, //
        { 'X', 'O', 'X', 'X' }, //
        { 'X', 'X', 'O', 'O' }, //
    };//

    floodFill(matrix2);
    printMatrix(matrix2);
    
    char[][] matrix3 = { //
        { 'X', 'X', 'X', 'X' }, //
        { 'X', 'O', 'O', 'X' }, //
        { 'X', 'X', 'O', 'X' }, //
        { 'X', 'O', 'X', 'X' }, //
    };//

    floodFill(matrix3);
    printMatrix(matrix3);
    
  }
}
