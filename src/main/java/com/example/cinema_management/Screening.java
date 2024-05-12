package com.example.cinema_management;

import BDD.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Screening {


        private int id;
        private int movieId;
        private int cinemaId;
        private int roomNumber;
        private String start_time;
        private String start_hour;
        private int duration;
        private String Duration;
        private String endTime;

        private String MovieTitle;

        private List<Seat> seats;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public int getCinemaId() {
            return cinemaId;
        }

        public void setCinemaId(int cinemaId) {
            this.cinemaId = cinemaId;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
        }

        public String getStartTime() {
            return start_time;
        }

        public void setStartTime(String start_time) {
            this.start_time = start_time;
        }
        public String getStartHour() {
            return start_hour;
        }

    public void setStartHour(String start_hour) {
        this.start_hour = start_hour;

    }

        public int getDuration() {
            return duration;
        }
    public void setDuration(int duration) {
        this.duration = duration;
    }

        public void setDurationString(String duration) {
            this.Duration = duration;
        }

    public String getDurationString() {
        return Duration;
    }
        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Screening() {
            this.movieId = movieId;
            this.cinemaId = cinemaId;
            this.roomNumber = roomNumber;
            this.start_time = start_time;
            this.start_hour = start_hour;
            this.duration = duration;
            this.Duration = Duration;

        }



    public void setMovieTitle(String movieTitle) {
        this.MovieTitle = movieTitle;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    @Override
    public String toString() {
        return "Movie Title: " + MovieTitle + ", Start Time: " + start_time;
    }

    public List<Seat> getAvailableSeats(Screening screening) {
            Query query = new Query();
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (query.isSeatBooked(screening.getId(), seat.getSeatId())) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }

    }

