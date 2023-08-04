<%-- 
    Document   : login
    Created on : Aug 4, 2023, 3:08:54 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:url value="/login" var="action" />
<form method="post" action="${action}">
    <div class="form-floating mb-3 mt-3">
        <input type="text" class="form-control" id="name" placeholder="Tên đăng nhập..." name="username">
        <label for="name">Tên đăng nhập</label>
    </div>

    <div class="form-floating mt-3 mb-3">
        <input type="text" class="form-control" id="pwd" placeholder="Mật khẩu..." name="password">
        <label for="pwd">Mật khẩu</label>
    </div>
    
    <div class="form-floating mt-3 mb-3">
        <input type="submit" value="Đăng nhập" class="btn btn-danger" />
    </div>
</form>