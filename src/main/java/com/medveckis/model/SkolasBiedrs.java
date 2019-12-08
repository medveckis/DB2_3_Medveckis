package com.medveckis.model;

public class SkolasBiedrs {
    private int id;
    private String vards;
    private String uzvards;
    private String ePast;
    private String telefonaNumurs;
    private Adrese dzivesVieta;

    public SkolasBiedrs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVards() {
        return vards;
    }

    public void setVards(String vards) {
        this.vards = vards;
    }

    public String getUzvards() {
        return uzvards;
    }

    public void setUzvards(String uzvards) {
        this.uzvards = uzvards;
    }

    public String getePast() {
        return ePast;
    }

    public void setePast(String ePast) {
        this.ePast = ePast;
    }

    public String getTelefonaNumurs() {
        return telefonaNumurs;
    }

    public void setTelefonaNumurs(String telefonaNumurs) {
        this.telefonaNumurs = telefonaNumurs;
    }

    public Adrese getDzivesVieta() {
        return dzivesVieta;
    }

    public void setDzivesVieta(Adrese dzivesVieta) {
        this.dzivesVieta = dzivesVieta;
    }
}
