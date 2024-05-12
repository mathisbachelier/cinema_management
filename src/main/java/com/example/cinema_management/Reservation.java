package com.example.cinema_management;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private String clientEmail;
    private int screeningId;
    private int seatId;
    private LocalDateTime reservationTime;

    // Constructor
    public Reservation() {
        this.reservationId = reservationId;
        this.clientEmail = clientEmail;
        this.screeningId = screeningId;
        this.seatId = seatId;
        this.reservationTime = reservationTime;
    }

    // Getters and setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public int getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(int screeningId) {
        this.screeningId = screeningId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }
}