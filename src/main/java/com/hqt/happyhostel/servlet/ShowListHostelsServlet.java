package com.hqt.happyhostel.servlet;

import com.hqt.happyhostel.dao.HostelDAO;
import com.hqt.happyhostel.dto.Account;
import com.hqt.happyhostel.dto.Hostel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowListHostelsServlet", value = "/ShowListHostelsServlet")
public class ShowListHostelsServlet extends HttpServlet {
    public static final String ERROR = "list-hostel-page";
    public static final String SUCCESS = "list-hostel-page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = ERROR;
        Account acc = new Account();

        try {
            HostelDAO hostelDAO = new HostelDAO();
            HttpSession session = req.getSession();
            acc = (Account) session.getAttribute("USER");
            int accountId = acc.getAccId();
            List<Hostel> listHostel = hostelDAO.getHostelByOwnerId(accountId);

            if (listHostel.size() > 0) {
                req.setAttribute("LIST_HOSTEL", listHostel);
                url = SUCCESS;
            }
            session.setAttribute("CURRENT_PAGE", "hostel");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
