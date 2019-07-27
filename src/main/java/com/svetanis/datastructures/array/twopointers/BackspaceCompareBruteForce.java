package com.svetanis.datastructures.array.twopointers;

public final class BackspaceCompareBruteForce {

  public static boolean compare(String str1, String str2) {
    // TODO: Write your code here
    String s1 = transform(str1);
    String s2 = transform(str2);
    return s1.equals(s2);
  }

  private static String transform(String s) {
    int n = s.length();
    int i = n - 1;
    StringBuilder sb = new StringBuilder();
    while (i > 0) {
      char c = s.charAt(i);
      if (c == '#') {
        i -= 2;
      } else {
        sb.insert(0, c);
        i--;
      }
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    System.out.println(compare("xy#z", "xzz#"));
    System.out.println(compare("xy#z", "xyz#"));
    System.out.println(compare("xp#", "xyz##"));
    System.out.println(compare("xywrrmp", "xywrrmu#p"));
  }
}
