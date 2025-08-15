package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 1396. Design Underground System

public final class UndergroundSystem {

	private Map<Integer, Integer> checkInTimes;
	private Map<Integer, String> checkInStations;
	private Map<String, int[]> travelInfo;

	public UndergroundSystem() {
		this.travelInfo = new HashMap<>();
		this.checkInTimes = new HashMap<>();
		this.checkInStations = new HashMap<>();
	}

	public void checkIn(int id, String stationName, int t) {
		checkInTimes.put(id, t);
		checkInStations.put(id, stationName);
	}

	public void checkOut(int id, String stationName, int t) {
		String routeKey = checkInStations.get(id) + "-" + stationName;
		int[] travelData = travelInfo.getOrDefault(routeKey, new int[2]);
		travelData[0] += t - checkInTimes.get(id);
		travelData[1]++;
		travelInfo.put(routeKey, travelData);
	}

	public double getAverageTime(String startStation, String endStation) {
		String routeKey = startStation + "-" + endStation;
		int[] travelData = travelInfo.get(routeKey);
		return (double) travelData[0] / travelData[1];
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