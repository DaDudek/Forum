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
    <h1>Posts</h1>
    <c:forEach var="post" items="${requestScope.posts}">
        <div class="container">
            <div class="row bs-callout bs-callout-warning">
                <div class="col col-md-1 col-sm-2">
                    <c:choose>
                        <c:when test="${post.postVoteType eq 'POSITIVE'}">
                            <a href="${pageContext.request.contextPath}/vote?post_id=${post.postId}&is_positive=true&history=true" class="btn btn-block btn-primary my-bt-vote-up"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/vote?post_id=${post.postId}&is_positive=true&history=true" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                        </c:otherwise>
                    </c:choose>
                    <div class="well well-sm centered"><p style="text-align: center"><c:out value="${post.positiveVote - post.negativeVote}" /></div>
                    <c:choose>
                        <c:when test="${post.postVoteType eq 'NEGATIVE'}">
                            <a href="${pageContext.request.contextPath}/vote?post_id=${post.postId}&is_positive=false&history=true" class="btn btn-block btn-primary my-bt-vote-down"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/vote?post_id=${post.postId}&is_positive=false&history=true" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col col-md-11 col-sm-10">
                    <h2 class="centered"><a href="${pageContext.request.contextPath}/post?post-id=${post.postId}"><c:out value="${post.title}" /></a></h2>
                    <h3><small style="color:#171716">Add by: <c:out value="${post.user.username}" />  date:  <fmt:formatDate value="${post.date}" pattern="dd/MM/YYYY"/></small></h3>
                    <p> <c:out value="${post.description}" /></p>
                    <a href="${pageContext.request.contextPath}/delete-post?post_id=${post.postId}"><button class="btn btn-danger btn-xs">Delete post</button></a>
                    <a href="${pageContext.request.contextPath}/edit-post?post-id=${post.postId}"><button class="btn btn-warning btn-xs">Edit post</button></a>
                </div>
            </div>
        </div>
    </c:forEach>
</c:if>

<c:if test="${not empty requestScope.comments}">
    <h1>Comments</h1>
    <c:forEach var="comment" items="${requestScope.comments}">
        <div class="container">
            <div class="row bs-callout bs-callout-warning">
                <div class="col col-md-1 col-sm-2">
                    <c:choose>
                        <c:when test="${comment.commentVoteType eq 'POSITIVE'}">
                            <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=true&comment_id=${comment.commentId}&history=true" class="btn btn-block btn-primary my-bt-vote-up"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=true&comment_id=${comment.commentId}&history=true" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                        </c:otherwise>
                    </c:choose>
                    <div class="well well-sm centered"><p style="text-align: center"><c:out value="${comment.positiveVote - comment.negativeVote}" /></div>
                    <c:choose>
                        <c:when test="${comment.commentVoteType eq 'NEGATIVE'}">
                            <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=false&comment_id=${comment.commentId}&history=true" class="btn btn-block btn-primary my-bt-vote-down"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=false&comment_id=${comment.commentId}&history=true" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col col-md-11 col-sm-10">
                    <h3><small style="color:#171716">Add by: <c:out value="${comment.author}" />   date: <fmt:formatDate value="${comment.date}" pattern="dd/MM/YYYY"/></small></h3>
                    <p> <c:out value="${comment.message}" /></p>
                </div>
                <a href="${pageContext.request.contextPath}/delete-comment?comment-id=${comment.commentId}&post-id=${comment.postId}"><button class="btn btn-danger btn-xs">Delete comment</button></a>
                <a href="${pageContext.request.contextPath}/edit-comment?comment-id=${comment.commentId}&post-id=${comment.postId}"><button class="btn btn-warning btn-xs">Edit comment</button></a>
                <a href="${pageContext.request.contextPath}/post?post-id=${comment.postId}"><button class="btn btn-info btn-xs">Go to post page</button></a>
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