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

@WebServlet("/GuthabenServlet")
public class GuthabenServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");

        if (benutzer == null) { response.sendRedirect("index.jsp"); return; }

        double betrag = Double.parseDouble(request.getParameter("betrag"));
        DataStore ds = DataStore.getInstance();
        ds.guthabenAufladen(benutzer.getId(), betrag);

        session.setAttribute("benutzer", ds.getBenutzerById(benutzer.getId()));
        request.setAttribute("meldung", "Guthaben aufgeladen!");
        request.getRequestDispatcher("guthaben.jsp").forward(request, response);
    }
}
