<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.mensa.dao.DataStore" %>
<%@ page import="de.mensa.model.*" %>
<%@ page import="java.util.List" %>
<%
    Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");
    if (benutzer == null) { response.sendRedirect("index.jsp"); return; }

    DataStore ds = DataStore.getInstance();
    List<Bestellung> bestellungen = ds.getBestellungenByBenutzer(benutzer.getId());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Meine Bestellungen</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="nav">
        <a href="speisekarte.jsp">Speisekarte</a> |
        <a href="meineBestellungen.jsp"><b>Meine Bestellungen</b></a> |
        <a href="guthaben.jsp">Guthaben</a> |
        <%= benutzer.getName() %> - Guthaben: <%= String.format("%.2f", benutzer.getGuthaben()) %> EUR
        | <a href="LogoutServlet">Abmelden</a>
    </div>

    <div class="inhalt">
        <h2>Meine Bestellungen</h2>

        <% if (bestellungen.isEmpty()) { %>
            <p>Noch keine Bestellungen.</p>
        <% } else { %>
            <table>
                <tr>
                    <th>Nr.</th>
                    <th>Speise</th>
                    <th>Menge</th>
                    <th>Preis</th>
                    <th>Datum</th>
                </tr>
                <%
                    double summe = 0;
                    for (Bestellung b : bestellungen) {
                        Speise sp = ds.getSpeiseById(b.getSpeiseId());
                        summe += b.getGesamtpreis();
                %>
                <tr>
                    <td><%= b.getId() %></td>
                    <td><%= sp != null ? sp.getName() : "?" %></td>
                    <td><%= b.getMenge() %></td>
                    <td><%= String.format("%.2f", b.getGesamtpreis()) %> EUR</td>
                    <td><%= b.getDatum() %></td>
                </tr>
                <% } %>
                <tr>
                    <td colspan="3"><b>Gesamt:</b></td>
                    <td><b><%= String.format("%.2f", summe) %> EUR</b></td>
                    <td></td>
                </tr>
            </table>
        <% } %>
    </div>
</body>
</html>
