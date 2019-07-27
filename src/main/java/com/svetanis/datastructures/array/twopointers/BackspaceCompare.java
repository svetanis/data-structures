package com.svetanis.datastructures.array.twopointers;

public final class BackspaceCompare {

  public static boolean compare(String str1, String str2) {
    
    int n = str1.length();
    int m = str2.length();
    int i = n - 1;
    int j = m - 1;

    while (i >= 0 || j >= 0) {
      int next1 = next(str1, i);
      int next2 = next(str2, j);
      
      // reached end of both strings
      if (next1 < 0 && next2 < 0) {
        return true;
      }
      
      // reached end of one of the strings
      if (next1 < 0 || next2 < 0) {
        return false;
      }
      
      if (str1.charAt(next1) != str2.charAt(next2)) {
        return false;
      }

      i = next1 - 1;
      j = next2 - 1;
    }

    return true;
  }

  private static int next(String str, int i) {
    int backSpace = 0;
    while (i >= 0) {
      if (str.charAt(i) == '#') {
        backSpace++;
      } else if (backSpace > 0) {
        backSpace--;
      } else {
        break;
      }
      i--;
    }
    return i;
  }

  public static void main(String[] args) {
    System.out.println(compare("xy#z", "xzz#"));
    System.out.println(compare("xy#z", "xyz#"));
    System.out.println(compare("xp#", "xyz##"));
    System.out.println(compare("xywrrmp", "xywrrmu#p"));
  }
}
