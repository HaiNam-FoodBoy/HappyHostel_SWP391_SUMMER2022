<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.hqt.happyhostel.dto.Account" %>
<%@ page import="com.hqt.happyhostel.dto.RoommateInfo" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 84337
  Date: 6/18/2022
  Time: 12:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Renter</title>
    <link rel="icon" href="./assets/images/favicon/favicon.png" type="image/x-icon"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/core_style/core.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./assets/css/renter_page/Renter-update-roommate.css">

    <!-- CSS Push Notification -->
    <link rel="stylesheet" href="./assets/css/push_notification_style/style.css">


</head>

<body>
<%
    Account account = (Account) session.getAttribute("USER");
%>
<div>
    <%@include file="components/navbar.jsp"%>

    <div class="row" style="padding: 0;margin: 0;">
        <%@include file="components/sidebar.jsp"%>
        <%
            ArrayList<RoommateInfo> listroommateinfor = (ArrayList<RoommateInfo>) session.getAttribute("listroommateinfor");
            RoommateInfo roommate1 = null;
            for (int i = 0; i < listroommateinfor.size(); i++) {
                if (listroommateinfor.get(i).getRoommateID() == Integer.parseInt(request.getParameter("roommateID"))) {
                    roommate1 = listroommateinfor.get(i);
                    session.setAttribute("roommate1", roommate1);
                }
            }
        %>
        <div class="content">
            <div class="div-controll-form" id="div-controll-form">
                <form action="renter-roommate-update" method="post" class="form" id="form_update_roommate">
                    <h1>Cập Nhật Thông Tin</h1>
                    <h3 style="color: green">${SUCCESS}</h3>
                    <h3 style="color: red">${ERROR}</h3>

                    <div class="form-item" id="form-item">
                        <input id="form-item-input-1" name="new-name" type="text" placeholder="Tên Đầy đủ"
                               value="<%=roommate1.getInformation().getFullname()%>">
                        <p class="border-bottom"></p>
                        <span id="mes-1"></span>
                    </div>
                    <div class="form-item">
                        <input id="form-item-input-2" placeholder="Email" type="email" name="new-email" multiple
                               value="<%=roommate1.getInformation().getEmail()%>">
                        <p class="border-bottom"></p>
                        <span id="mes-2"></span>
                    </div>
                    <div class="form-item">
                        <input id="form-item-input-3" name="new-birthday" type="date" placeholder="Ngày sinh"
                               value="<fmt:parseDate pattern="yyyy-MM-dd" value="${roommate1.information.birthday}" var="birthday" /><fmt:formatDate value="${birthday}" type="Date" pattern="dd-MM-yyyy"/>"/>
                        <p class="border-bottom"></p>
                        <span id="mes-3"></span>
                    </div>
                    <div class="form-item">
                        <select name="new-gender" id="form-item-input-4">
                            <option value="0">Nữ</option>
                            <option value="1">Nam</option>
                        </select>
                    </div>
                    <div class="form-item">
                        <input id="form-item-input-5" name="new-phone" type="text" placeholder="Số điện thoại"
                               value="<%=roommate1.getInformation().getPhone()%>">
                        <p class="border-bottom"></p>
                        <span id="mes-5"></span>
                    </div>
                    <div class="form-item">
                        <input id="form-item-input-6" name="new-address" type="text" placeholder="Địa chỉ"
                               value="<%=roommate1.getInformation().getAddress()%>">
                        <p class="border-bottom"></p>
                        <span id="mes-6"></span>
                    </div>
                    <div class="form-item">
                        <input id="form-item-input-7" name="new-cccd" type="text" placeholder="Số CCCD"
                               value="<%=roommate1.getInformation().getCccd()%>">
                        <p class="border-bottom"></p>
                        <span id="mes-7"></span>
                    </div>
                    <input type="hidden" value="${param.roommateID}" name="roommateID">
                    <input id="form-item-submit" type="button" value="Lưu">
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="components/footer.jsp"%>

<!-- Push notification element -->
<div id="push-noti"></div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="./assets/js/renter/Renter-update-roommate.js"></script>

<!-- Push notification -->
<script src="./assets/js/push-notification-alert.js"></script>
<!-- Web socket -->
<script src="./assets/js/receiveWebsocket.js"></script>
<script>
   form_1.addEventListener("blur", () => {
       isRequire(form_1, "Vui lòng nhập trường này!")
   });
   form_2.addEventListener("blur", () => {
       isRequire(form_2, "Vui lòng nhập trường này!")
       isMail(form_2, "Email không hợp lệ!")
   });
   form_3.addEventListener("blur", () => {
       isRequire(form_3, "Vui lòng nhập trường này!")
   });
   form_4.addEventListener("blur", () => {
       isRequire(form_4, "Vui lòng nhập trường này!")
   });
   form_5.addEventListener("blur", () => {
       isRequire(form_5, "Vui lòng nhập trường này!")
       isPhone(form_5, "Số điện thoại không hợp lệ!")
   });
   form_6.addEventListener("blur", () => {
       isRequire(form_6, "Vui lòng nhập trường này!")
   });
   form_7.addEventListener("blur", () => {
       isRequire(form_7, "Vui lòng nhập trường này!")
       isCccd(form_7, "Số cccd không khả dụng!")
   });
</script>

<script type="text/javascript">
    // Receive
    receiveWebsocket(alertPushNoti);
    // Close when leave
    window.onbeforeunload = function(){
        receiveWebsocket.disconnectWebSocket();
    };
</script>
</body>

</html>