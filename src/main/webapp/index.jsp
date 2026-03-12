<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Uni-Mensa - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="center">
        <h1>Uni-Mensa</h1>
        <p>Bitte anmelden</p>

        <% if (request.getAttribute("fehler") != null) { %>
            <p class="fehler"><%= request.getAttribute("fehler") %></p>
        <% } %>

        <form action="LoginServlet" method="post">
            <label>E-Mail:</label><br>
            <input type="email" name="email" required><br><br>
            <label>Passwort:</label><br>
            <input type="password" name="passwort" required><br><br>
            <button type="submit">Anmelden</button>
        </form>

        <br>
        <p><b>Test-Zugaenge:</b></p>
        <p>prof-roeckle@h-lu.de / PW:1234</p>
        <p>lahbil@h-lu.de / PW:1234</p>
        <p>menghis@h-lu.de / PW:1234</p>
        <p>ajaero@h-lu.de / PW:1234</p>      

    </div>
</body>
</html>
