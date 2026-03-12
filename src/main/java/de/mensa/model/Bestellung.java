package de.mensa.model;

public class Bestellung {
    private int id;
    private int benutzerId;
    private int speiseId;
    private int menge;
    private double gesamtpreis;
    private String datum;

    public Bestellung() {}

    public Bestellung(int id, int benutzerId, int speiseId, int menge, double gesamtpreis, String datum) {
        this.id = id;
        this.benutzerId = benutzerId;
        this.speiseId = speiseId;
        this.menge = menge;
        this.gesamtpreis = gesamtpreis;
        this.datum = datum;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBenutzerId() { return benutzerId; }
    public void setBenutzerId(int benutzerId) { this.benutzerId = benutzerId; }
    public int getSpeiseId() { return speiseId; }
    public void setSpeiseId(int speiseId) { this.speiseId = speiseId; }
    public int getMenge() { return menge; }
    public void setMenge(int menge) { this.menge = menge; }
    public double getGesamtpreis() { return gesamtpreis; }
    public void setGesamtpreis(double gesamtpreis) { this.gesamtpreis = gesamtpreis; }
    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }
}
