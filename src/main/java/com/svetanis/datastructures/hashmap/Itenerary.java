package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// Find itinerary from a given list of tickets (GeeksforGeeks; no LeetCode number)
//
// itenerary takes the tickets as a map from -> to, where every city is left at most once
// and reached at most once and the tickets make one route, and returns the route as a list
// of legs from its first city to its last. It throws if no city is a starting point, or if
// two tickets reach the same city.
//
// The first city of the route is the one city that is left but never reached, so it is
// the one key of the map that is not among its values. From there each city's ticket names
// the next, until a city with no ticket out. Two tickets into one city would break the
// walk ("A" -> "C", "B" -> "C", "C" -> "B" goes round forever from "A"), which is why that
// is checked first.
//
// Siblings: none in this package. DS/graph/directed/eulerian/ReconstructItinerary solves LeetCode 332,
// a different problem: many tickets may leave one airport, and every ticket must be used.

public final class Itenerary {
  // Time complexity: O(n), n = tickets: one pass to find the start, one leg per ticket
  // Auxiliary Space: O(n) for the set of destinations and the route

  private static final String NO_START = "invalid input: every city is reached by a ticket";
  private static final String SAME_DESTINATION = "invalid input: two tickets reach one city in %s";

  public static List<Leg> itenerary(Map<String, String> tickets) {
    Set<String> destinations = new HashSet<>(tickets.values());
    if (destinations.size() < tickets.size()) {
      throw new IllegalStateException(SAME_DESTINATION.formatted(tickets)); // CHECK: one ticket into each city
    }
    // find the starting point
    String start = getStart(tickets, destinations);
    if (start == null) {
      throw new IllegalStateException(NO_START);
    }
    return asList(start, tickets);
  }

  private static List<Leg> asList(String start, Map<String, String> tickets) {
    List<Leg> list = new ArrayList<>();
    String to = tickets.get(start);
    while (to != null) {
      list.add(new Leg(start, to)); // WALK one ticket
      start = to;
      to = tickets.get(to); // NEXT: null at the last city, which has no ticket out
    }
    return list;
  }

  private static String getStart(Map<String, String> tickets, Set<String> destinations) {
    String start = null;
    for (Entry<String, String> entry : tickets.entrySet()) {
      if (!destinations.contains(entry.getKey())) {
        start = entry.getKey(); // FIND: left but never reached
        break;
      }
    }
    return start;
  }

  // one ticket of the route
  public record Leg(String from, String to) {
  }

  public static void main(String[] args) {
    Map<String, String> tickets = new HashMap<>();
    tickets.put("Chennai", "Banglore");
    tickets.put("Bombay", "Delhi");
    tickets.put("Goa", "Chennai");
    tickets.put("Delhi", "Goa");
    for (Leg leg : itenerary(tickets)) {
      System.out.println(leg); // Bombay -> Delhi -> Goa -> Chennai -> Banglore
    }
    System.out.println();
  }
}
