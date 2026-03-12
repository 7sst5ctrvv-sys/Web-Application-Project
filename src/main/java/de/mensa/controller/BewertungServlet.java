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

@WebServlet("/BewertungServlet")
public class BewertungServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");

        if (benutzer == null) { response.sendRedirect("index.jsp"); return; }

        int speiseId = Integer.parseInt(request.getParameter("speiseId"));
        int sterne = Integer.parseInt(request.getParameter("sterne"));
        String kommentar = request.getParameter("kommentar");

        DataStore.getInstance().bewertungAbgeben(benutzer.getId(), speiseId, sterne, kommentar);
        response.sendRedirect("speiseDetail.jsp?id=" + speiseId);
    }
}
