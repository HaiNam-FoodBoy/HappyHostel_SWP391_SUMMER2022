package com.hqt.happyhostel.servlet;

import com.hqt.happyhostel.dao.HostelOwnerDAO;
import com.hqt.happyhostel.dao.RoomDAO;
import com.hqt.happyhostel.dto.HostelOwnerAccount;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddRoomServlet", value = "/AddRoomServlet")
public class AddRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "loginPage";
        int quantityRoom = Integer.parseInt(request.getParameter("txtQuantityRoom"));
        int capacity = Integer.parseInt(request.getParameter("txtCapacity"));
        int attic = Integer.parseInt(request.getParameter("txtAttic"));
        double roomArea = Double.parseDouble(request.getParameter("txtRoomArea"));
        int restrooms = Integer.parseInt(request.getParameter("txtNumberRestrooms"));
        int windows = Integer.parseInt(request.getParameter("txtNumberWindows"));
        int airConditions = Integer.parseInt(request.getParameter("txtNumberAirConditions"));
        try {
            if (quantityRoom > 1) {
                for (int i = 0; i < quantityRoom; i++) {
                    Boolean isSuccess = RoomDAO.addNewManyRooms(1, capacity, roomArea, attic,
                            "RestRoom", restrooms, 1,
                            "Windows", windows, 1,
                            "AirCondition", airConditions, 1);
                    url = "success";
                    if (isSuccess) {
                        request.setAttribute("isSuccess", "true");
                    } else {
                        request.setAttribute("isSuccess", "false");
                        break;
                    }
                }
            } else {
                int roomNumber = Integer.parseInt(request.getParameter("txtRoomName"));
                Boolean isSuccess = RoomDAO.addNewRoom(1, roomNumber, capacity, roomArea, 1,
                        "RestRoom", restrooms, 1,
                        "Windows", windows, 1,
                        "AirCondition", airConditions, 1);
                url = "success";
                if (isSuccess) {
                    request.setAttribute("isSuccess", "true");
                } else {
                    request.setAttribute("isSuccess", "false");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
