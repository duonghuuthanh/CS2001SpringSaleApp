<%-- 
    Document   : index
    Created on : Jul 7, 2023, 1:08:19 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section class="container">
    <h1 class="text-center text-info mt-1">DANH SÁCH SẢN PHẨM</h1>
    <a href="<c:url value="/products" />" class="btn btn-info">Thêm sản phẩm</a>

    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <li class="page-item"><a class="page-link" href="<c:url value="/" />">Tất cả</a></li>
                <c:forEach begin="1" end="${counter}" var="i">
                    <c:url value="/" var="pageUrl">
                        <c:param name="page" value="${i}"></c:param>
                    </c:url>
                <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
                </c:forEach>
        </ul>
    </c:if>

    <table class="table table-hover">
        <thead>
            <tr>
                <th></th>
                <th>Id</th>
                <th>Tên sản phẩm</th>
                <th>Gía</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${products}" var="p">
                <tr>
                    <td>
                        <img src="${p.image}" alt="${p.name}" width="120" />
                    </td>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td>${p.price}</td>
                    <td>
                        <c:url value="/api/products/${p.id}" var="apiDel" />
                        <a href="<c:url value="/products/${p.id}" />" class="btn btn-success">Cập nhật</a>
                        <button class="btn btn-danger" onclick="delPro('${apiDel}', ${p.id})">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</section>
    
    <script src="<c:url value="/js/main.js" />"></script>