package com.medveckis.model;

public class Adrese {
    private int id;
    private int dzivoklaNumurs;
    private String iela;
    private String pilseta;
    private String valst;
    private String pastaIndeks;

    public Adrese() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDzivoklaNumurs() {
        return dzivoklaNumurs;
    }

    public void setDzivoklaNumurs(int dzivoklaNumurs) {
        this.dzivoklaNumurs = dzivoklaNumurs;
    }

    public String getIela() {
        return iela;
    }

    public void setIela(String iela) {
        this.iela = iela;
    }

    public String getPilseta() {
        return pilseta;
    }

    public void setPilseta(String pilseta) {
        this.pilseta = pilseta;
    }

    public String getValst() {
        return valst;
    }

    public void setValst(String valst) {
        this.valst = valst;
    }

    public String getPastaIndeks() {
        return pastaIndeks;
    }

    public void setPastaIndeks(String pastaIndeks) {
        this.pastaIndeks = pastaIndeks;
    }
}
