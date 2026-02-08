package com.svetanis.datastructures.hashmap;

// 1603. Design Parking System

public final class ParkingSystem {

	private int[] availableSpaces;

	public ParkingSystem(int big, int medium, int small) {
		this.availableSpaces = new int[] { 0, big, medium, small };
	}

	public boolean addCar(int carType) {
		if (availableSpaces[carType] <= 0) {
			return false;
		}
		availableSpaces[carType] -= 1;
		return true;
	}

	public static void main(String[] args) {
		ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
		System.out.println(parkingSystem.addCar(1)); // return true because there is 1 available slot for a big car
		System.out.println(parkingSystem.addCar(2)); // return true because there is 1 available slot for a medium car
		System.out.println(parkingSystem.addCar(3)); // return false because there is no available slot for a small car
		System.out.println(parkingSystem.addCar(1)); // return false because there is no available slot for a big car. It is
																									// already occupied.
	}
}