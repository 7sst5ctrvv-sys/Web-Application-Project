package de.mensa.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import de.mensa.dao.DataStore;
import de.mensa.model.Benutzer;

@WebServlet("/BestellungServlet")
public class BestellungServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");

        if (benutzer == null) { response.sendRedirect("index.jsp"); return; }

        int speiseId = Integer.parseInt(request.getParameter("speiseId"));
        int menge = Integer.parseInt(request.getParameter("menge"));

        DataStore ds = DataStore.getInstance();
        boolean erfolg = ds.bestellungAufgeben(benutzer.getId(), speiseId, menge);

        if (erfolg) {
            session.setAttribute("benutzer", ds.getBenutzerById(benutzer.getId()));
            request.setAttribute("meldung", "Bestellung erfolgreich!");
        } else {
            request.setAttribute("fehler", "Nicht genug Guthaben!");
        }
        request.getRequestDispatcher("speisekarte.jsp").forward(request, response);
    }
}
