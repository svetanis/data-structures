package com.svetanis.datastructures.graph.floodfill;

// 130. Surrounded Regions
//
// Input: a board of 'X' and 'O' with m rows and n columns. On LeetCode: 1 <= m, n <= 200.
// Returns: nothing; the board is changed in place. Every region of 'O' cells (cells joined up,
// down, left or right) that does not touch the border of the board is flipped to 'X'. A region
// that touches the border stays as it is.
//
// The one idea: it is easier to find the 'O' cells that are SAVED than the ones that are
// surrounded. An 'O' is saved exactly when a chain of 'O' cells leads from it to a cell on the
// border, and then the whole region it belongs to is saved with it. So: (1) rename every 'O' to
// '-' ("not saved yet"); (2) from every '-' on the border, flood fill back to 'O' -- the fill
// spreads over the whole region and saves all of it; (3) whatever is still '-' was never reached
// from the border, so it is surrounded and becomes 'X'.
//
// Known limit: fill is recursive, one call deeper for every cell of an open region, so a very
// large region overflows the default Java stack -- a 200 x 200 board that is all 'O' (LeetCode's
// largest) does. CaptureRegionsOnBoardIterative is the same algorithm with an explicit stack and
// has no such limit.
//
// Time: O(m * n) -- three passes over the board, and each cell is changed from '-' back to 'O' at
// most once, so the fill does constant work per cell.
// Space: O(m * n) -- the recursion depth, which is the size of the largest region in the worst
// case.
//
// Siblings: CaptureRegionsOnBoardIterative is the same algorithm without recursion. FloodFillDfs
// is the same recursive fill, started from one given cell instead of from the border.

public final class CaptureRegionsOnBoard {

  public static void floodFill(char[][] matrix) {

    int n = matrix.length;
    int m = matrix[0].length;

    // replace all 'O' with '-'
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (matrix[i][j] == 'O') {
          matrix[i][j] = '-'; // MARK: every 'O' is unsaved until the border reaches it
        }
      }
    }

    // call floodFill for all '-' lying on edges
    for (int i = 0; i < n; i++) {
      if (matrix[i][0] == '-') {
        fill(matrix, i, 0, '-', 'O'); // SAVE: left column
      }
    }

    for (int i = 0; i < n; i++) {
      if (matrix[i][m - 1] == '-') {
        fill(matrix, i, m - 1, '-', 'O'); // SAVE: right column
      }
    }

    for (int i = 0; i < m; i++) {
      if (matrix[0][i] == '-') {
        fill(matrix, 0, i, '-', 'O'); // SAVE: top row
      }
    }

    for (int i = 0; i < m; i++) {
      if (matrix[n - 1][i] == '-') {
        fill(matrix, n - 1, i, '-', 'O'); // SAVE: bottom row
      }
    }

    // replace all '-' with 'X'
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (matrix[i][j] == '-') {
          matrix[i][j] = 'X'; // FLIP: never reached from the border, so surrounded
        }
      }
    }
  }

  private static void fill(char[][] matrix, int x, int y, char old, char c) {

    int n = matrix.length;
    int m = matrix[0].length;
    if (x < 0 || x >= n || y >= m || y < 0) {
      return; // SKIP: off the board
    }

    if (matrix[x][y] != old) {
      return; // SKIP: not an unsaved cell (an 'X', or already saved)
    }
    matrix[x][y] = c; // PAINT: save this cell, which also marks it as visited

    fill(matrix, x + 1, y, old, c); // RECURSE: the four neighbours
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
    // X O X X X X   the top 'O' on column 1 touches the border, so it stays; the block of 'O'
    // X O X X X X   at rows 1-2, columns 3-4 is closed in, so it flips
    // X X X X X X
    // O X X X X X   border cells stay
    // X X X O X O   (4,3) joins (5,3) on the bottom border, so it stays
    // O O X O O O

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
    // X X X X   the region at rows 1-3 is closed in, so it flips; the 'O' pair at the bottom
    // X X X X   right sits on the border, so it stays
    // X X X X
    // X X X X
    // X X O O

    char[][] matrix3 = { //
        { 'X', 'X', 'X', 'X' }, //
        { 'X', 'O', 'O', 'X' }, //
        { 'X', 'X', 'O', 'X' }, //
        { 'X', 'O', 'X', 'X' }, //
    };//

    floodFill(matrix3);
    printMatrix(matrix3);
    // X X X X   the region at rows 1-2 is closed in, so it flips; the single 'O' at (3,1) is on
    // X X X X   the bottom border, so it stays
    // X X X X
    // X O X X

  }
}
