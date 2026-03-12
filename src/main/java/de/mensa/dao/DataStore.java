package de.mensa.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import de.mensa.model.*;

public class DataStore {

    private static DataStore instance;

    private DataStore() {
        DatabaseHelper.initDatabase();
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public Benutzer login(String email, String passwort) {
        String sql = "SELECT * FROM Benutzer WHERE email = ? AND passwort = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, passwort);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapBenutzer(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Benutzer getBenutzerById(int id) {
        String sql = "SELECT * FROM Benutzer WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapBenutzer(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Benutzer> getAlleBenutzer() {
        List<Benutzer> liste = new ArrayList<>();
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Benutzer")) {
            while (rs.next()) liste.add(mapBenutzer(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public void guthabenAufladen(int benutzerId, double betrag) {
        String sql = "UPDATE Benutzer SET guthaben = guthaben + ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, betrag);
            ps.setInt(2, benutzerId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Kategorie> getAlleKategorien() {
        List<Kategorie> liste = new ArrayList<>();
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie")) {
            while (rs.next()) liste.add(new Kategorie(rs.getInt("id"), rs.getString("name")));
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public Kategorie getKategorieById(int id) {
        String sql = "SELECT * FROM Kategorie WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Kategorie(rs.getInt("id"), rs.getString("name"));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Speise> getAlleSpeisen() {
        List<Speise> liste = new ArrayList<>();
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Speise")) {
            while (rs.next()) liste.add(mapSpeise(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public List<Speise> getSpeisenByKategorie(int kategorieId) {
        List<Speise> liste = new ArrayList<>();
        String sql = "SELECT * FROM Speise WHERE kategorie_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kategorieId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) liste.add(mapSpeise(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public Speise getSpeiseById(int id) {
        String sql = "SELECT * FROM Speise WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapSpeise(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public synchronized boolean bestellungAufgeben(int benutzerId, int speiseId, int menge) {
        Benutzer benutzer = getBenutzerById(benutzerId);
        Speise speise = getSpeiseById(speiseId);
        if (benutzer == null || speise == null) return false;
        double gesamtpreis = speise.getPreis() * menge;
        if (benutzer.getGuthaben() < gesamtpreis) return false;

        String datum = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm").format(new java.util.Date());
        try (Connection conn = DatabaseHelper.getConnection()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement ps1 = conn.prepareStatement(
                    "UPDATE Benutzer SET guthaben = guthaben - ? WHERE id = ? AND guthaben >= ?");
                ps1.setDouble(1, gesamtpreis);
                ps1.setInt(2, benutzerId);
                ps1.setDouble(3, gesamtpreis);
                if (ps1.executeUpdate() == 0) { conn.rollback(); return false; }

                PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO Bestellung (benutzer_id, speise_id, menge, gesamtpreis, datum) VALUES (?, ?, ?, ?, ?)");
                ps2.setInt(1, benutzerId);
                ps2.setInt(2, speiseId);
                ps2.setInt(3, menge);
                ps2.setDouble(4, gesamtpreis);
                ps2.setString(5, datum);
                ps2.executeUpdate();
                conn.commit();
                return true;
            } catch (SQLException e) { conn.rollback(); e.printStackTrace(); return false; }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Bestellung> getBestellungenByBenutzer(int benutzerId) {
        List<Bestellung> liste = new ArrayList<>();
        String sql = "SELECT * FROM Bestellung WHERE benutzer_id = ? ORDER BY id DESC";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, benutzerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) liste.add(mapBestellung(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public List<Bestellung> getAlleBestellungen() {
        List<Bestellung> liste = new ArrayList<>();
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Bestellung ORDER BY id DESC")) {
            while (rs.next()) liste.add(mapBestellung(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public synchronized void bewertungAbgeben(int benutzerId, int speiseId, int sterne, String kommentar) {
        String datum = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm").format(new java.util.Date());
        String sql = "INSERT INTO Bewertung (benutzer_id, speise_id, sterne, kommentar, datum) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, benutzerId);
            ps.setInt(2, speiseId);
            ps.setInt(3, sterne);
            ps.setString(4, kommentar);
            ps.setString(5, datum);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Bewertung> getBewertungenBySpeise(int speiseId) {
        List<Bewertung> liste = new ArrayList<>();
        String sql = "SELECT * FROM Bewertung WHERE speise_id = ? ORDER BY id DESC";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, speiseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) liste.add(mapBewertung(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public double getDurchschnittsBewertung(int speiseId) {
        String sql = "SELECT AVG(sterne) as avg_sterne FROM Bewertung WHERE speise_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, speiseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("avg_sterne");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Benutzer mapBenutzer(ResultSet rs) throws SQLException {
        return new Benutzer(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
            rs.getString("passwort"), rs.getString("rolle"), rs.getDouble("guthaben"));
    }

    private Speise mapSpeise(ResultSet rs) throws SQLException {
        return new Speise(rs.getInt("id"), rs.getString("name"), rs.getString("beschreibung"),
            rs.getDouble("preis"), rs.getInt("kategorie_id"));
    }

    private Bestellung mapBestellung(ResultSet rs) throws SQLException {
        return new Bestellung(rs.getInt("id"), rs.getInt("benutzer_id"), rs.getInt("speise_id"),
            rs.getInt("menge"), rs.getDouble("gesamtpreis"), rs.getString("datum"));
    }

    private Bewertung mapBewertung(ResultSet rs) throws SQLException {
        return new Bewertung(rs.getInt("id"), rs.getInt("benutzer_id"), rs.getInt("speise_id"),
            rs.getInt("sterne"), rs.getString("kommentar"), rs.getString("datum"));
    }
}
