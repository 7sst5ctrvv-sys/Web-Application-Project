package de.mensa.model;

public class Benutzer {
    private int id;
    private String name;
    private String email;
    private String passwort;
    private String rolle;
    private double guthaben;

    public Benutzer() {}

    public Benutzer(int id, String name, String email, String passwort, String rolle, double guthaben) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwort = passwort;
        this.rolle = rolle;
        this.guthaben = guthaben;
    }

    public void guthabenAufladen(double betrag) {
        this.guthaben += betrag;
    }

    public boolean guthabenAbziehen(double betrag) {
        if (this.guthaben >= betrag) {
            this.guthaben -= betrag;
            return true;
        }
        return false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswort() { return passwort; }
    public void setPasswort(String passwort) { this.passwort = passwort; }
    public String getRolle() { return rolle; }
    public void setRolle(String rolle) { this.rolle = rolle; }
    public double getGuthaben() { return guthaben; }
    public void setGuthaben(double guthaben) { this.guthaben = guthaben; }
}
