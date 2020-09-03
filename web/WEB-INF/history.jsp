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


<c:if test="${not empty requestScope.posts}">
    <h1>Posty</h1>
    <c:forEach var="post" items="${requestScope.posts}">
        <div class="container">
            <div class="row bs-callout bs-callout-warning">
                <div class="col col-md-1 col-sm-2">
                    <a href="${pageContext.request.contextPath}/vote?post_id=${post.postId}&is_positive=true" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                    <div class="well well-sm centered"><p style="text-align: center"><c:out value="${post.positiveVote - post.negativeVote}" /></div>
                    <a href="${pageContext.request.contextPath}/vote?post_id=${post.postId}&is_positive=false" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
                </div>
                <div class="col col-md-11 col-sm-10">
                    <h3 class="centered"><a href="${pageContext.request.contextPath}/post?post-id=${post.postId}"><c:out value="${post.title}" /></a></h3>
                    <h6><small style="color:#171716">Dodane przez: <c:out value="${post.user.username}" />  <fmt:formatDate value="${post.date}" pattern="dd/MM/YYYY"/></small></h6>
                    <p> <c:out value="${post.description}" /></p>
                    <a href="${pageContext.request.contextPath}/delete-post?post_id=${post.postId}"><button class="btn btn-danger btn-xs">Usuń post</button></a>
                    <a href="${pageContext.request.contextPath}/edit-post?post-id=${post.postId}"><button class="btn btn-warning btn-xs">Edytuj post</button></a>
                </div>
            </div>
        </div>
    </c:forEach>
</c:if>

<c:if test="${not empty requestScope.comments}">
    <h1>Komentarze</h1>
    <c:forEach var="comment" items="${requestScope.comments}">
        <div class="container">
            <div class="row bs-callout bs-callout-warning">
                <div class="col col-md-1 col-sm-2">
                    <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=true&comment_id=${comment.commentId}&post-page=true" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                    <div class="well well-sm centered"><p style="text-align: center"><c:out value="${comment.positiveVote - comment.negativeVote}" /></div>
                    <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=false&comment_id=${comment.commentId}&post-page=true" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
                </div>
                <div class="col col-md-11 col-sm-10">
                    <h6><small style="color:#171716">Dodane przez: <c:out value="${comment.author}" />  <fmt:formatDate value="${comment.date}" pattern="dd/MM/YYYY"/></small></h6>
                    <p> <c:out value="${comment.message}" /></p>
                </div>
                <a href="${pageContext.request.contextPath}/delete-comment?comment-id=${comment.commentId}&post-id=${comment.postId}"><button class="btn btn-danger btn-xs">Usuń komentarz</button></a>
                <a href="${pageContext.request.contextPath}/edit-comment?comment-id=${comment.commentId}&post-id=${comment.postId}"><button class="btn btn-warning btn-xs">Edytuj komentarz</button></a>
                <a href="${pageContext.request.contextPath}/post?post-id=${comment.postId}"><button class="btn btn-info btn-xs">Przejdź do strony</button></a>
            </div>
        </div>
    </c:forEach>
</c:if>

<jsp:include page="fragments/footer.jsp" />

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="resources/js/bootstrap.js"></script>
</body>
</html>