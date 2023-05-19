package com.betasolutions.projekt2023.model;

public class Task {
    private int id;
    private String name;
    private int startDate;
    private int endDate;
    private boolean isPending;


    public Task(String name, int startDate, int endDate) {
    }

    public Task(int id, String name, int startDate, int endDate, boolean isPending) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPending = isPending;
    }

    public Task(int id, String name, int startDate, int endDate) {
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

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public boolean getIsPending() {
        return isPending;
    }
    public boolean setIsPending(boolean isPending) {
        return this.isPending = isPending;
    }
    @Override
    public String toString(){
        return "task{" + "id=" + id + ", name='" + name + '\'' + ", startDate=" + startDate + '}';
    }
}
