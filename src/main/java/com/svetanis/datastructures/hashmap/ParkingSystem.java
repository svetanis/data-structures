package com.svetanis.datastructures.hashmap;

// 1603. Design Parking System
//
// The constructor takes how many big, medium and small slots the lot has. addCar(carType)
// parks a car of type 1 (big), 2 (medium) or 3 (small) in a free slot of its own size and
// returns true, or returns false when that size is full. On LeetCode:
// 0 <= big, medium, small <= 1000, carType is 1, 2 or 3, at most 1000 calls to addCar.
//
// One counter of free slots per size. The array has four places so that carType itself is
// the index: place 0 is never used, and no type has to be translated into a position.
//
// Siblings, same problem: none in this package.

public final class ParkingSystem {
	// Time complexity: O(1) per call, one array read and at most one write
	// Auxiliary Space: O(1), four ints

	private int[] availableSpaces; // FREE: free slots for carType 1, 2, 3; place 0 unused

	public ParkingSystem(int big, int medium, int small) {
		this.availableSpaces = new int[] { 0, big, medium, small };
	}

	public boolean addCar(int carType) {
		if (availableSpaces[carType] <= 0) { // FULL?
			return false;
		}
		availableSpaces[carType] -= 1; // TAKE one slot of that size
		return true;
	}

	public static void main(String[] args) {
		ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
		System.out.println(parkingSystem.addCar(1)); // return true because there is 1 available slot for a big car
		System.out.println(parkingSystem.addCar(2)); // return true because there is 1 available slot for a medium car
		System.out.println(parkingSystem.addCar(3)); // return false because there is no available slot for a small car
		System.out.println(parkingSystem.addCar(1)); // return false: the one big slot is already taken
	}
}