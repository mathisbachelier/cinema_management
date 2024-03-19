package com.example.cinema_management;

public class Film {
    private int id;
    private String title;
    private String director;
    private String description;
    private int duration;

    private int is_archived;

    // Constructor
    public Film(int id, String title, String director, String description, int duration, int is_archived) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.description = description;
        this.duration = duration;
    }
    public Film(){

    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setIs_archived(int is_archived) {
        this.is_archived = is_archived;
    }
    public int getIs_archived() {
        return is_archived;
    }
}
