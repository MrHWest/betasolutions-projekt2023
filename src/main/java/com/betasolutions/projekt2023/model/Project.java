package com.betasolutions.projekt2023.model;

public class Project {
    private int id;
    private String name;
    private int startDate;
    private int slutDate;

    public Project() {
    }

    public Project(int id, String name, int startDate, int slutDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.slutDate = slutDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getSlutDate() {
        return slutDate;
    }

    public void setSlutDate(int slutDate) {
        this.slutDate = slutDate;
    }
}
