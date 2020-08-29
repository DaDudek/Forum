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

<jsp:include page="fragments/navbar.jsp" />

    <div class="container">
        <div class="row bs-callout bs-callout-warning">
            <div class="col col-md-1 col-sm-2">
                <a href="${pageContext.request.contextPath}/vote?post_id=${requestScope.post.postId}&is_positive=true&post-page=true" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                <div class="well well-sm centered"><p style="text-align: center"><c:out value="${requestScope.post.positiveVote - requestScope.post.negativeVote}" /></div>
                <a href="${pageContext.request.contextPath}/vote?post_id=${requestScope.post.postId}&is_positive=false&post-page=true" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
            </div>
            <div class="col col-md-11 col-sm-10">
                <h3 class="centered"><a href="#"><c:out value="${requestScope.post.title}" /></a></h3>
                <h6><small style="color:#171716">Dodane przez: <c:out value="${requestScope.post.user.username}" />  <fmt:formatDate value="${requestScope.post.date}" pattern="dd/MM/YYYY"/></small></h6>
                <p> <c:out value="${requestScope.post.message}" /></p>
                <c:if test="${requestScope.post.user.userId ==  sessionScope.user.userId}">
                    <a href="${pageContext.request.contextPath}/delete-post?post_id=${requestScope.post.postId}"><button class="btn btn-danger btn-xs">Usuń post</button></a>
                </c:if>
            </div>
        </div>
    </div>


<jsp:include page="fragments/footer.jsp" />

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="resources/js/bootstrap.js"></script>
</body>
</html>