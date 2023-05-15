package com.betasolutions.projekt2023.model;

import java.util.ArrayList;

public class Task {
    private int id;
    private String name;
    private int startDato;
    private int slutDato;
    private boolean isPending;


    public Task() {
    }

    public Task(int id, String name, int startDato, int slutDato, boolean isPending) {
        this.id = id;
        this.name = name;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.isPending = isPending;
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

    public int getStartDato() {
        return startDato;
    }

    public void setStartDato(int startDato) {
        this.startDato = startDato;
    }

    public int getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(int slutDato) {
        this.slutDato = slutDato;
    }

    public boolean getIsPending() {
        return isPending;
    }
    public boolean setIsPending(boolean isPending) {
        return this.isPending = isPending;
    }
}
