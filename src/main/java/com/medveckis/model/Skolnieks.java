package com.medveckis.model;

public class Skolnieks {
    private int id;
    private SkolasBiedrs skolnieksInfo;
    private int klaseId;

    public Skolnieks() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SkolasBiedrs getSkolnieksInfo() {
        return skolnieksInfo;
    }

    public void setSkolnieksInfo(SkolasBiedrs skolnieksInfo) {
        this.skolnieksInfo = skolnieksInfo;
    }

    public int getKlaseId() {
        return klaseId;
    }

    public void setKlaseId(int klaseId) {
        this.klaseId = klaseId;
    }
}
