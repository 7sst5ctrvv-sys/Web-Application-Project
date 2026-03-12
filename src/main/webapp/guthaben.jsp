<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.mensa.model.Benutzer" %>
<%
    Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");
    if (benutzer == null) { response.sendRedirect("index.jsp"); return; }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Guthaben</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="nav">
        <a href="speisekarte.jsp">Speisekarte</a> |
        <a href="meineBestellungen.jsp">Meine Bestellungen</a> |
        <a href="guthaben.jsp"><b>Guthaben</b></a> |
        <%= benutzer.getName() %> - Guthaben: <%= String.format("%.2f", benutzer.getGuthaben()) %> EUR
        | <a href="LogoutServlet">Abmelden</a>
    </div>

    <div class="inhalt">
        <h2>Guthaben verwalten
            <% if (request.getAttribute("meldung") != null) { %>
                <span class="meldung"><%= request.getAttribute("meldung") %></span>
            <% } %>
        </h2>

        <p>Aktuelles Guthaben: <b><%= String.format("%.2f", benutzer.getGuthaben()) %> EUR</b></p>

        <form action="GuthabenServlet" method="post">
            Betrag (EUR):
            <input type="number" name="betrag" min="1" max="100" step="0.50" value="10" required>
            <button type="submit">Aufladen</button>
        </form>

        <br>
        <p>Schnell aufladen:</p>
        <form action="GuthabenServlet" method="post" style="display:inline;">
            <input type="hidden" name="betrag" value="5">
            <button type="submit">+ 5 EUR</button>
        </form>
        <form action="GuthabenServlet" method="post" style="display:inline;">
            <input type="hidden" name="betrag" value="10">
            <button type="submit">+ 10 EUR</button>
        </form>
        <form action="GuthabenServlet" method="post" style="display:inline;">
            <input type="hidden" name="betrag" value="20">
            <button type="submit">+ 20 EUR</button>
        </form>
    </div>

    <script>
        setTimeout(function() {
            var m = document.querySelectorAll('.meldung, .fehler');
            m.forEach(function(el) { el.style.opacity = '0'; setTimeout(function() { el.remove(); }, 1500); });
        }, 2000);
    </script>
</body>
</html>
