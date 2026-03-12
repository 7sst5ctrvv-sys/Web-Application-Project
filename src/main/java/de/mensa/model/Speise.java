package de.mensa.model;

public class Speise {
    private int id;
    private String name;
    private String beschreibung;
    private double preis;
    private int kategorieId;

    public Speise() {}

    public Speise(int id, String name, String beschreibung, double preis, int kategorieId) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.kategorieId = kategorieId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBeschreibung() { return beschreibung; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
    public double getPreis() { return preis; }
    public void setPreis(double preis) { this.preis = preis; }
    public int getKategorieId() { return kategorieId; }
    public void setKategorieId(int kategorieId) { this.kategorieId = kategorieId; }
}
