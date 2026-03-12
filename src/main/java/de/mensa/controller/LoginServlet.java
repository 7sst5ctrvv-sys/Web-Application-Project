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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String passwort = request.getParameter("passwort");

        Benutzer benutzer = DataStore.getInstance().login(email, passwort);

        if (benutzer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("benutzer", benutzer);
            response.sendRedirect("speisekarte.jsp");
        } else {
            request.setAttribute("fehler", "Falsche E-Mail oder Passwort!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
