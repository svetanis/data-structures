package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 1396. Design Underground System
//
// checkIn(id, stationName, t) and checkOut(id, stationName, t) record a rider entering and
// leaving; getAverageTime(startStation, endStation) returns the average time of all finished
// trips from startStation straight to endStation. On LeetCode: 1 <= id, t <= 10^6, station
// names are 1 to 10 letters and digits, at most 2 * 10^4 calls, every check-out follows its
// rider's check-in, and getAverageTime is only asked about a route with a finished trip.
//
// Two kinds of record. An open trip, by rider id: where and when the rider checked in. A
// finished route, by "start-end": the total time of its trips and how many there were, so an
// average is one division. The "-" cannot appear inside a station name, so two routes never
// share a key. The total is a long: a route can hold 10^4 trips of almost 10^6 seconds each,
// far past an int.
//
// Siblings, same problem: none in this package.

public final class UndergroundSystem {
	// Time complexity: every call O(1) on average, a few map steps; the key is at most 21 chars
	// Auxiliary Space: O(p + r), p riders seen and r routes travelled

	private Map<Integer, Integer> checkInTimes; // OPEN: rider -> check-in time
	private Map<Integer, String> checkInStations; // OPEN: rider -> check-in station
	private Map<String, long[]> travelInfo; // ROUTE: "start-end" -> { total time, trips }

	public UndergroundSystem() {
		this.travelInfo = new HashMap<>();
		this.checkInTimes = new HashMap<>();
		this.checkInStations = new HashMap<>();
	}

	public void checkIn(int id, String stationName, int t) {
		checkInTimes.put(id, t); // OPEN: a later check-in overwrites a finished trip's record
		checkInStations.put(id, stationName);
	}

	public void checkOut(int id, String stationName, int t) {
		String routeKey = checkInStations.get(id) + "-" + stationName; // KEY: "-" is never in a name
		long[] travelData = travelInfo.getOrDefault(routeKey, new long[2]);
		travelData[0] += t - checkInTimes.get(id); // ADD: this trip's time, in long
		travelData[1]++; // COUNT
		travelInfo.put(routeKey, travelData);
	}

	public double getAverageTime(String startStation, String endStation) {
		String routeKey = startStation + "-" + endStation;
		long[] travelData = travelInfo.get(routeKey);
		return (double) travelData[0] / travelData[1]; // AVERAGE: total over trips
	}

	public static void main(String[] args) {
		UndergroundSystem ugs = new UndergroundSystem();
		ugs.checkIn(45, "Leyton", 3);
		ugs.checkIn(32, "Paradise", 8);
		ugs.checkIn(27, "Leyton", 10);
		ugs.checkOut(45, "Waterloo", 15); // Customer 45 "Leyton" -> "Waterloo" in 15-3 = 12
		ugs.checkOut(27, "Waterloo", 20); // Customer 27 "Leyton" -> "Waterloo" in 20-10 = 10
		ugs.checkOut(32, "Cambridge", 22); // Customer 32 "Paradise" -> "Cambridge" in 22-8 = 14
		// return 14.00000. One trip "Paradise" -> "Cambridge", (14) / 1 = 14
		System.out.println(ugs.getAverageTime("Paradise", "Cambridge"));
		// return 11.00000. Two trips "Leyton" -> "Waterloo", (10 + 12) / 2 = 11
		System.out.println(ugs.getAverageTime("Leyton", "Waterloo"));
		ugs.checkIn(10, "Leyton", 24);
		System.out.println(ugs.getAverageTime("Leyton", "Waterloo")); // return 11.00000
		ugs.checkOut(10, "Waterloo", 38); // Customer 10 "Leyton" -> "Waterloo" in 38-24 = 14
		// return 12.00000. Three trips "Leyton" -> "Waterloo", (10 + 12 + 14) / 3 = 12
		System.out.println(ugs.getAverageTime("Leyton", "Waterloo"));
		
		UndergroundSystem ugs2 = new UndergroundSystem();
		ugs2.checkIn(10, "Leyton", 3);
		ugs2.checkOut(10, "Paradise", 8); // Customer 10 "Leyton" -> "Paradise" in 8-3 = 5
		System.out.println(ugs2.getAverageTime("Leyton", "Paradise")); // return 5.00000, (5) / 1 = 5
		ugs2.checkIn(5, "Leyton", 10);
		ugs2.checkOut(5, "Paradise", 16); // Customer 5 "Leyton" -> "Paradise" in 16-10 = 6
		System.out.println(ugs2.getAverageTime("Leyton", "Paradise")); // return 5.50000, (5 + 6) / 2 = 5.5
		ugs2.checkIn(2, "Leyton", 21);
		ugs2.checkOut(2, "Paradise", 30); // Customer 2 "Leyton" -> "Paradise" in 30-21 = 9
		System.out.println(ugs2.getAverageTime("Leyton", "Paradise")); // return 6.66667, (5 + 6 + 9) / 3 = 6.66667
	}
}