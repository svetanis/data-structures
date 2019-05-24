package com.svetanis.datastructures.hashmap;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.Exceptions.illegalState;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class Itenerary {

  public static ImmutableList<Pair<String, String>> itenerary(BiMap<String, String> bimap) {
    // find the starting point
    String start = getStart(bimap);
    if (start == null) {
      throw illegalState("invalid input");
    }
    return asList(start, bimap);
  }

  private static ImmutableList<Pair<String, String>> asList(String start, BiMap<String, String> bimap) {
    List<Pair<String, String>> list = newArrayList();
    String to = bimap.get(start);
    while (to != null) {
      list.add(Pair.build(start, to));
      start = to;
      to = bimap.get(to);
    }
    return newList(list);
  }

  private static String getStart(BiMap<String, String> bimap) {
    String start = null;
    for (Entry<String, String> entry : bimap.entrySet()) {
      if (!bimap.inverse().containsKey(entry.getKey())) {
        start = entry.getKey();
        break;
      }
    }
    return start;
  }

  public static void main(String[] args) {
    BiMap<String, String> bimap = HashBiMap.create();
    bimap.put("Chennai", "Banglore");
    bimap.put("Bombay", "Delhi");
    bimap.put("Goa", "Chennai");
    bimap.put("Delhi", "Goa");
    printLines(itenerary(bimap));
  }
}
