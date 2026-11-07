package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 1797. Design Authentication Manager
//
// Every token lives timeToLive seconds. generate(id, t) creates a token that expires at
// t + timeToLive; renew(id, t) moves an unexpired token's expiry to t + timeToLive and ignores
// an expired or unknown one; countUnexpiredTokens(t) returns how many tokens are still alive
// at t. A token expiring at exactly t is already gone at t: expiry happens before any other
// action at the same second. On LeetCode: 1 <= timeToLive, currentTime <= 10^8, currentTime
// strictly increases across calls, generate is never called twice for one id, at most 2000
// calls.
//
// The map keeps, for each token, the second it expires -- not the second it was made, so
// "alive at t" is one comparison, expiry > t. renew only rewrites a token that passes that
// same test. Nothing is ever removed: expired tokens stay in the map and the count skips them.
// The unknown-token default of 0 counts as expired because every currentTime is at least 1.
//
// Siblings, same problem: none in this package.

public final class AuthenticationManager {
	// Time complexity: generate and renew O(1); countUnexpiredTokens O(g), g = tokens ever
	// generated -- expired ones are never removed, so the count walks all of them
	// Auxiliary Space: O(g)

	private int timeToLive;
	private Map<String, Integer> map; // EXPIRY: token -> the second it stops counting

	public AuthenticationManager(int timeToLive) {
		this.timeToLive = timeToLive;
		this.map = new HashMap<>();
	}

	public void generate(String tokenId, int currentTime) {
		map.put(tokenId, currentTime + timeToLive); // STORE: the expiry, not the start
	}

	public void renew(String tokenId, int currentTime) {
		int time = map.getOrDefault(tokenId, 0); // FIND: 0 for an unknown token, always expired
		if (time > currentTime) { // ALIVE?: expiring at exactly currentTime is too late
			generate(tokenId, currentTime); // RENEW: a fresh expiry from now
		}
	}

	public int countUnexpiredTokens(int currentTime) {
		return (int) map.values().stream().filter(t -> t > currentTime).count(); // COUNT: the alive ones
	}

	public static void main(String[] args) {
		AuthenticationManager am = new AuthenticationManager(5);
		am.renew("aaa", 1);
		am.generate("aaa", 2);
		System.out.println(am.countUnexpiredTokens(6)); // 1
		am.generate("bbb", 7);
		am.renew("aaa", 8);
		am.renew("bbb", 10);
		System.out.println(am.countUnexpiredTokens(15)); // 0
	}
}