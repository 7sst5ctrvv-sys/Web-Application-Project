package de.mensa.model;

public class Bewertung {
    private int id;
    private int benutzerId;
    private int speiseId;
    private int sterne;
    private String kommentar;
    private String datum;

    public Bewertung() {}

    public Bewertung(int id, int benutzerId, int speiseId, int sterne, String kommentar, String datum) {
        this.id = id;
        this.benutzerId = benutzerId;
        this.speiseId = speiseId;
        this.sterne = sterne;
        this.kommentar = kommentar;
        this.datum = datum;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBenutzerId() { return benutzerId; }
    public void setBenutzerId(int benutzerId) { this.benutzerId = benutzerId; }
    public int getSpeiseId() { return speiseId; }
    public void setSpeiseId(int speiseId) { this.speiseId = speiseId; }
    public int getSterne() { return sterne; }
    public void setSterne(int sterne) { this.sterne = sterne; }
    public String getKommentar() { return kommentar; }
    public void setKommentar(String kommentar) { this.kommentar = kommentar; }
    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }
}
