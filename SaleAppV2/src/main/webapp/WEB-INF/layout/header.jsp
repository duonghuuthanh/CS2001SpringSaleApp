<%-- 
    Document   : header
    Created on : Jul 21, 2023, 3:14:16 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="action" />
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">E-commerce Website</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${action}">Trang chủ</a>
                </li>
                <c:forEach items="${categories}" var="c">
                    <c:url value="/" var="cateAction">
                        <c:param name="cateId" value="${c.id}" />
                    </c:url>
                    <li class="nav-item">
                        <a class="nav-link" href="${cateAction}">${c.name}</a>
                    </li>
                </c:forEach>
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <li class="nav-item">
                            <a class="nav-link text-danger" href="<c:url value="/" />">${pageContext.request.userPrincipal.name}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-danger" href="<c:url value="/logout" />">Đăng xuất</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/login" />">Đăng nhập</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>

            <form class="d-flex" action="${action}">
                <input class="form-control me-2" type="text" name="kw" placeholder="Nhập từ khóa...">
                <button class="btn btn-primary" type="submit">Tìm</button>
            </form>
        </div>
    </div>
</nav>
