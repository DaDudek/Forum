<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class = "navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a href="${pageContext.request.contextPath}" class="navbar-brand">Forum</a>

        <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
            <span class="glyphicon glyphicon-list"></span>
        </button>

        <div class="collapse navbar-collapse navHeaderCollapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/history">History</a></li>
                </c:if>
                <li><a href="${pageContext.request.contextPath}/new-post">Add</a></li>
                <li><a href="${pageContext.request.contextPath}/search">Search</a></li>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li><a href="${pageContext.request.contextPath}/sign-out">Sign out</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/sign-in">Sign in</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>


    </div>
</nav>