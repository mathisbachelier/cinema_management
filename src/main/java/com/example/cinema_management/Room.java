package com.example.cinema_management;

public class Room {

        private String roomNumber; // Unique identifier for the room
        private int capacity; // Maximum number of people the room can accommodate

        // Getters and Setters for each attribute

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        // Constructor with arguments (optional)
        public Room(String roomNumber, int capacity) {
            this.roomNumber = roomNumber;
            this.capacity = capacity;
        }

        // Default constructor (no arguments) - Optional
        public Room() {
            this.roomNumber = null;
            this.capacity = 0;
        }

        // Additional methods specific to rooms (e.g., check availability) - You can define these as needed


}
