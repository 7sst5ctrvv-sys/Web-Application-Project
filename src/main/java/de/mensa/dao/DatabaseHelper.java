package de.mensa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:h2:~/mensaDB;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void initDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS Benutzer ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) NOT NULL UNIQUE, "
                + "passwort VARCHAR(100) NOT NULL, "
                + "rolle VARCHAR(20) NOT NULL, "
                + "guthaben DOUBLE DEFAULT 0"
                + ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS Kategorie ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(50) NOT NULL"
                + ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS Speise ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(100) NOT NULL, "
                + "beschreibung VARCHAR(500), "
                + "preis DOUBLE NOT NULL, "
                + "kategorie_id INT, "
                + "FOREIGN KEY (kategorie_id) REFERENCES Kategorie(id)"
                + ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS Bestellung ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "benutzer_id INT NOT NULL, "
                + "speise_id INT NOT NULL, "
                + "menge INT NOT NULL, "
                + "gesamtpreis DOUBLE NOT NULL, "
                + "datum VARCHAR(50), "
                + "FOREIGN KEY (benutzer_id) REFERENCES Benutzer(id), "
                + "FOREIGN KEY (speise_id) REFERENCES Speise(id)"
                + ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS Bewertung ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "benutzer_id INT NOT NULL, "
                + "speise_id INT NOT NULL, "
                + "sterne INT NOT NULL, "
                + "kommentar VARCHAR(500), "
                + "datum VARCHAR(50), "
                + "FOREIGN KEY (benutzer_id) REFERENCES Benutzer(id), "
                + "FOREIGN KEY (speise_id) REFERENCES Speise(id)"
                + ")");

            var rs = stmt.executeQuery("SELECT COUNT(*) FROM Benutzer");
            rs.next();
            if (rs.getInt(1) == 0) {
                insertTestData(stmt);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertTestData(Statement stmt) throws SQLException {
    	
        stmt.execute("INSERT INTO Benutzer (name, email, passwort, rolle, guthaben) VALUES ('Prof. Röckler', 'prof-roeckle@h-lu.de', '1234', 'DOZENT', 40.00)");
        stmt.execute("INSERT INTO Benutzer (name, email, passwort, rolle, guthaben) VALUES ('Hafsa Labhil', 'lahbil@h-lu.de', '1234', 'STUDENT', 25.00)");
        stmt.execute("INSERT INTO Benutzer (name, email, passwort, rolle, guthaben) VALUES ('Rahwa Menghis', 'menghis@h-lu.de', '1234', 'STUDENT', 25.00)");
        stmt.execute("INSERT INTO Benutzer (name, email, passwort, rolle, guthaben) VALUES ('Thelma Ifeoma Ajaero', 'ajaero@h-lu.de', '1234', 'STUDENT', 25.00)");


        
      


        stmt.execute("INSERT INTO Kategorie (name) VALUES ('Hauptspeise')");
        stmt.execute("INSERT INTO Kategorie (name) VALUES ('Beilage')");
        stmt.execute("INSERT INTO Kategorie (name) VALUES ('Getraenk')");
        stmt.execute("INSERT INTO Kategorie (name) VALUES ('Nachtisch')");

        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Schnitzel mit Pommes', 'Paniertes Schweineschnitzel mit Pommes Frites', 4.50, 1)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Spaghetti Bolognese', 'Klassische Pasta mit Fleischsauce', 3.80, 1)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Gemuesecurry', 'Veganes Curry mit Basmatireis', 3.50, 1)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Caesar Salad', 'Frischer Salat mit Croutons und Parmesan', 3.20, 2)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Pommes Frites', 'Knusprige Pommes', 1.50, 2)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Cola 0.3l', 'Coca-Cola', 1.20, 3)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Wasser 0.5l', 'Stilles Mineralwasser', 0.80, 3)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Apfelsaft 0.3l', 'Naturtruebes Apfelsaft', 1.00, 3)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Schokoladenpudding', 'Hausgemachter Schokopudding', 1.50, 4)");
        stmt.execute("INSERT INTO Speise (name, beschreibung, preis, kategorie_id) VALUES ('Obstsalat', 'Frischer Obstsalat der Saison', 1.80, 4)");
    }
}
