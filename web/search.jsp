<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Forum</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
</head>

<body>

<jsp:include page="WEB-INF/fragments/navbar.jsp" />

<div class="container">
    <div class="col-sm-6 col-md-4 col-md-offset-4">
        <form class="form-signin" method="post" action="sign-on">
            <h2 class="form-signin-heading">sign on</h2>
            <input name="inputKeywords" type="text" class="form-control" placeholder="what do you want to find" required autofocus /> <br />
            <label class="radio"><input type="radio" name="optradio" value="best-vote" checked>sort by best vote</label>
            <label class="radio"><input type="radio" name="optradio" value="oldest">sort by oldest</label>
            <label class="radio"><input type="radio" name="optradio" value="newest">sort by newest</label>
        </form>
    </div>
</div>

<jsp:include page="WEB-INF/fragments/footer.jsp" />

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="resources/js/bootstrap.js"></script>
</body>
</html>