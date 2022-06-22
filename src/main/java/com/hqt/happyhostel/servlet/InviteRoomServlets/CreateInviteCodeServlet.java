package com.hqt.happyhostel.servlet.InviteRoomServlets;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hqt.happyhostel.dao.HostelOwnerDAO;
import com.hqt.happyhostel.dao.RoomDAO;
import com.hqt.happyhostel.dao.RoomInviteDAO;
import com.hqt.happyhostel.dto.Account;
import com.hqt.happyhostel.dto.Room;
import com.hqt.happyhostel.utils.EncodeBase64Utils;
import com.hqt.happyhostel.utils.RandomStringGenerator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@WebServlet(name = "CreateInviteCodeServlet", value = "/CreateInviteCodeServlet")
public class CreateInviteCodeServlet extends HttpServlet {
    private final String SUCCESS = "invite-code-page";
    private final String FAIL = "create-room-account";
    private final String ERROR = "error-page";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        String roomId = null;
        Account owner = null;
        Room roomInvite = null;
        StringBuilder inviteUrl = new StringBuilder("RenterRegisterPage?inviteCode=");
        try {

            roomId = request.getParameter("room_id");
            int ownerId = -1;
            String inviteCode = null;

            HttpSession session = request.getSession(false);
            if (session != null) {
                url = FAIL;
                owner = (Account) session.getAttribute("USER");
                RoomInviteDAO roomInviteDAO = new RoomInviteDAO();

                //check request parameter
                if (owner != null && roomId != null) {
                    int roomID = Integer.parseInt(roomId);
                    ownerId = owner.getAccId();

                    //check xem roomID có thuộc ownerID không
                    if (new HostelOwnerDAO().checkOwnerRoom(ownerId, roomID)) {

                        //Create invite link
                        inviteCode = RandomStringGenerator.randomInviteCode(5, roomId);
                        inviteUrl = inviteUrl.append(inviteCode);

                        //Create QR Code
                        QRCodeWriter barcodeWriter = new QRCodeWriter();
                        BitMatrix bitMatrix = barcodeWriter.encode(inviteUrl.toString(), BarcodeFormat.QR_CODE, 200, 200);
                        BufferedImage qrImg = MatrixToImageWriter.toBufferedImage(bitMatrix);
                        String QRBase64 = EncodeBase64Utils.imageToBase64(qrImg);

                        //Create endTime
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Calendar startTime = Calendar.getInstance();
                        long timeInSecs = startTime.getTimeInMillis();
                        Timestamp endTime = new Timestamp(timeInSecs + (30 * 60 * 1000));

                        if (roomInviteDAO.updateRoomInviteCode(roomID, inviteCode, QRBase64, sdf.format(endTime))) {
                            new RoomDAO().updateRoomStatus(roomID, 0);
                            roomInvite = roomInviteDAO.getRoomInviteById(roomID);
                            url = SUCCESS;
                        }
                    }
                    request.setAttribute("ROOM_INVITE", roomInvite);
                    request.setAttribute("URL_INVITE", inviteUrl);
                }
            }
        } catch (Exception e) {
            log("Error at InviteCodeServlet: " + e.toString());
        } finally {
            if (owner != null && roomId != null) request.getRequestDispatcher(url).forward(request, response);
            else response.sendRedirect(url);
        }
    }
}
