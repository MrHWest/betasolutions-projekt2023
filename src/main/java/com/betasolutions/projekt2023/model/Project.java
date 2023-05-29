package com.betasolutions.projekt2023.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Project {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate slutDate;
    private boolean isDone;
    public Project() {
    }

    public Project(int id, String name, LocalDate startDate, LocalDate slutDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.slutDate = slutDate;
    }

    public Project(int id, String name, LocalDate startDate, LocalDate slutDate, boolean isDone) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.slutDate = slutDate;
        this.isDone = isDone;
    }

    public Project(String name, LocalDate startDate, LocalDate slutDate) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getSlutDate() {
        return slutDate;
    }

    public void setSlutDate(LocalDate slutDate) {
        this.slutDate = slutDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
