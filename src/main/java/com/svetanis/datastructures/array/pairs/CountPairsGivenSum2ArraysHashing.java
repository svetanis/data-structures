package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.utils.Maps.freqMap;

import java.util.Map;

// given two arrays and integer k
// find the total number of pairs
// with sum equal to k

public final class CountPairsGivenSum2ArraysHashing {

	public static int count(int[] a1, int[] a2, int k) {
		// Time Complexity: O(n + m)

		Map<Integer,Integer> map = freqMap(a1); 
		int count = 0;
		for(int i = 0; i < a2.length; i++) {
			int val = k - a2[i];
			int freq = map.getOrDefault(val, 0);
			if(freq > 0) {
				count++;
				map.put(val, freq - 1);
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 1, 3, 4, 5, 6, 6 };
		int[] a2 = { 1, 4, 4, 5, 7 };
		System.out.println(count(a1, a2, 10));

		int[] a3 = { 1, 10, 13, 15 };
		int[] a4 = { 3, 3, 12, 4 };
		System.out.println(count(a3, a4, 13));
	}
}
