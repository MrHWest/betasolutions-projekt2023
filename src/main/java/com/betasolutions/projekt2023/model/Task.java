package com.betasolutions.projekt2023.model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isPending;


    public Task() {
    }

    public Task(String name, LocalDate startDate, LocalDate endDate) {
    }

    public Task(int id, String name, LocalDate startDate, LocalDate endDate, boolean isPending) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPending = isPending;
    }

    public Task(int id, String name, LocalDate startDate, LocalDate endDate) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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
