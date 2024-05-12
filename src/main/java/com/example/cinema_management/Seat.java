package com.example.cinema_management;

import BDD.Query;

import java.util.List;
import java.util.Objects;

public class Seat {
    private int seatId;
    private int roomId;
    private int seatNumber;

    public Seat() {
        this.seatId = seatId;
        this.roomId = roomId;
        this.seatNumber = seatNumber;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", roomId=" + roomId +
                ", seatNumber=" + seatNumber +
                '}';
    }


    public static List<Seat> isAvailableByscreening(int screening) {
        Query query = new Query();
        return query.getAvailableSeats(screening);

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return seatId == seat.seatId &&
                roomId == seat.roomId &&
                seatNumber == seat.seatNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, roomId, seatNumber);
    }
}
