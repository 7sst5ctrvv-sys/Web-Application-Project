<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.mensa.dao.DataStore" %>
<%@ page import="de.mensa.model.*" %>
<%@ page import="java.util.List" %>
<%
    Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");
    if (benutzer == null) { response.sendRedirect("index.jsp"); return; }

    int speiseId = Integer.parseInt(request.getParameter("id"));
    DataStore ds = DataStore.getInstance();
    Speise speise = ds.getSpeiseById(speiseId);
    Kategorie kat = ds.getKategorieById(speise.getKategorieId());
    List<Bewertung> bewertungen = ds.getBewertungenBySpeise(speiseId);
    double durchschnitt = ds.getDurchschnittsBewertung(speiseId);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= speise.getName() %></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="nav">
        <a href="speisekarte.jsp">Speisekarte</a> |
        <a href="meineBestellungen.jsp">Meine Bestellungen</a> |
        <a href="guthaben.jsp">Guthaben</a> |
        <%= benutzer.getName() %> - Guthaben: <%= String.format("%.2f", benutzer.getGuthaben()) %> EUR
        | <a href="LogoutServlet">Abmelden</a>
    </div>

    <div class="inhalt">
        <p><a href="speisekarte.jsp">Zurueck</a></p>

        <h2><%= speise.getName() %></h2>
        <p>Kategorie: <%= kat.getName() %></p>
        <p><%= speise.getBeschreibung() %></p>
        <p><b>Preis: <%= String.format("%.2f", speise.getPreis()) %> EUR</b></p>
        <p>Bewertung: <%= durchschnitt > 0 ? String.format("%.1f", durchschnitt) + " / 5" : "Noch keine" %></p>

        <form action="BestellungServlet" method="post">
            <input type="hidden" name="speiseId" value="<%= speise.getId() %>">
            Menge:
            <select name="menge">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
            </select>
            <button type="submit">Bestellen</button>
        </form>

        <hr>
        <h3>Bewertung abgeben</h3>
        <form action="BewertungServlet" method="post">
            <input type="hidden" name="speiseId" value="<%= speise.getId() %>">
            Sterne:
            <select name="sterne">
                <option value="5">5</option>
                <option value="4">4</option>
                <option value="3">3</option>
                <option value="2">2</option>
                <option value="1">1</option>
            </select><br><br>
            Kommentar:<br>
            <textarea name="kommentar" rows="3" cols="40"></textarea><br><br>
            <button type="submit">Bewertung abgeben</button>
        </form>

        <hr>
        <h3>Bewertungen (<%= bewertungen.size() %>)</h3>
        <% if (bewertungen.isEmpty()) { %>
            <p>Noch keine Bewertungen.</p>
        <% } else { %>
            <% for (Bewertung bw : bewertungen) {
                Benutzer autor = ds.getBenutzerById(bw.getBenutzerId());
            %>
            <div class="bewertung">
                <b><%= autor != null ? autor.getName() : "Unbekannt" %></b> - <%= bw.getDatum() %><br>
                <%= bw.getSterne() %> / 5 Sterne<br>
                <p><%= bw.getKommentar() %></p>
            </div>
            <% } %>
        <% } %>
    </div>
</body>
</html>
