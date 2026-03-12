<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.mensa.dao.DataStore" %>
<%@ page import="de.mensa.model.*" %>
<%@ page import="java.util.List" %>
<%
    Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");
    if (benutzer == null) { response.sendRedirect("index.jsp"); return; }
    DataStore ds = DataStore.getInstance();
    List<Kategorie> kategorien = ds.getAlleKategorien();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Speisekarte</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="nav">
        <a href="speisekarte.jsp"><b>Speisekarte</b></a> |
        <a href="meineBestellungen.jsp">Meine Bestellungen</a> |
        <a href="guthaben.jsp">Guthaben</a> |
        <%= benutzer.getName() %> (<%= benutzer.getRolle() %>) -
        Guthaben: <%= String.format("%.2f", benutzer.getGuthaben()) %> EUR
        | <a href="LogoutServlet">Abmelden</a>
    </div>

    <div class="inhalt">
        <h2>Speisekarte
            <% if (request.getAttribute("meldung") != null) { %>
                <span class="meldung"><%= request.getAttribute("meldung") %></span>
            <% } %>
            <% if (request.getAttribute("fehler") != null) { %>
                <span class="fehler"><%= request.getAttribute("fehler") %></span>
            <% } %>
        </h2>

        <% for (Kategorie kat : kategorien) { %>
            <h3><%= kat.getName() %></h3>
            <table>
                <tr>
                    <th>Speise</th>
                    <th>Beschreibung</th>
                    <th>Preis</th>
                    <th>Bewertung</th>
                    <th>Bestellen</th>
                    <th></th>
                </tr>
                <%
                    List<Speise> speisen = ds.getSpeisenByKategorie(kat.getId());
                    for (Speise sp : speisen) {
                        double bew = ds.getDurchschnittsBewertung(sp.getId());
                        int anz = ds.getBewertungenBySpeise(sp.getId()).size();
                %>
                <tr>
                    <td><%= sp.getName() %></td>
                    <td><%= sp.getBeschreibung() %></td>
                    <td><%= String.format("%.2f", sp.getPreis()) %> EUR</td>
                    <td><%= bew > 0 ? String.format("%.1f", bew) + "/5" : "-" %> (<%= anz %>)</td>
                    <td>
                        <form action="BestellungServlet" method="post" style="display:inline;">
                            <input type="hidden" name="speiseId" value="<%= sp.getId() %>">
                            <select name="menge">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                            <button type="submit">Bestellen</button>
                        </form>
                    </td>
                    <td><a href="speiseDetail.jsp?id=<%= sp.getId() %>">Details</a></td>
                </tr>
                <% } %>
            </table>
        <% } %>
    </div>

    <script>
        setTimeout(function() {
            var m = document.querySelectorAll('.meldung, .fehler');
            m.forEach(function(el) { el.style.opacity = '0'; setTimeout(function() { el.remove(); }, 1500); });
        }, 2000);
    </script>
</body>
</html>
