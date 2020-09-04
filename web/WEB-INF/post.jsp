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
                <h2 class="centered"><c:out value="${requestScope.post.title}" /></h2>
                <h3><small style="color:#171716">Add by: <c:out value="${requestScope.post.user.username}" />  <fmt:formatDate value="${requestScope.post.date}" pattern="dd/MM/YYYY"/></small></h3>
                <c:choose>
                    <c:when test="${requestScope.isEditing != null}">
                        <form class="form-signin" method="post" action="edit-post?post-id=${requestScope.post.postId}">
                            <input name="inputTitle" type="text" class="form-control" placeholder="post title max 60 character"
                                   value="${requestScope.post.title}" required autofocus />
                            <textarea name="inputDescription" rows="5" class="form-control"
                                      placeholder="post description - max 100 character" required autofocus>${requestScope.post.description}</textarea>
                            <textarea name="inputMessage" rows="5" class="form-control"
                                       placeholder="post message - max 500 character" required autofocus>${requestScope.post.message}</textarea>
                            <input class="btn btn-lg btn-success btn-block" type="submit"
                                   value="save changes" />
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p> <c:out value="${requestScope.post.message}" /></p>
                    </c:otherwise>
                </c:choose>

                <c:if test="${(requestScope.post.user.userId ==  sessionScope.user.userId) or (sessionScope.user.role.name() eq 'ADMIN' )}">
                    <a href="${pageContext.request.contextPath}/delete-post?post_id=${requestScope.post.postId}"><button class="btn btn-danger btn-xs">Delete post</button></a>
                </c:if>
                <c:if test="${requestScope.post.user.userId ==  sessionScope.user.userId}">
                    <c:if test="${requestScope.isEditing == null}">
                            <a href="${pageContext.request.contextPath}/edit-post?post-id=${requestScope.post.postId}"><button class="btn btn-warning btn-xs">Edit post</button></a>
                    </c:if>
                </c:if>
            </div>
        </div>

        <c:if test="${not empty requestScope.comments}">
            <c:forEach var="comment" items="${requestScope.comments}">
                <div class="container">
                    <div class="row bs-callout bs-callout-warning">
                        <div class="col col-md-1 col-sm-2">
                            <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=true&comment_id=${comment.commentId}&post-page=true" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                            <div class="well well-sm centered"><p style="text-align: center"><c:out value="${comment.positiveVote - comment.negativeVote}" /></div>
                            <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=false&comment_id=${comment.commentId}&post-page=true" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
                        </div>
                        <div class="col col-md-11 col-sm-10">
                            <h6><small style="color:#171716">Add by: <c:out value="${comment.author}" />  <fmt:formatDate value="${comment.date}" pattern="dd/MM/YYYY"/></small></h6>
                            <c:choose>
                                <c:when test="${requestScope.commentEditingId == comment.commentId}">
                                    <form class="form-signin" method="post" action="edit-comment?comment-id=${comment.commentId}&post-id=${requestScope.post.postId}">
                                        <textarea name="inputMessage" rows="5" class="form-control"
                                                  placeholder="post comment - max 500 character" required autofocus>${comment.message}</textarea>
                                        <input class="btn btn-lg btn-success btn-block" type="submit"
                                               value="save comment changes" />
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <p> <c:out value="${comment.message}" /></p>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <c:if test="${(comment.userId ==  sessionScope.user.userId) or sessionScope.user.role.name() eq 'ADMIN' }">
                            <a href="${pageContext.request.contextPath}/delete-comment?comment-id=${comment.commentId}&post-id=${requestScope.post.postId}"><button class="btn btn-danger btn-xs">Delete comment</button></a>
                        </c:if>
                        <c:if test="${comment.userId ==  sessionScope.user.userId}">
                            <c:if test="${requestScope.isEditing == null}">
                                <a href="${pageContext.request.contextPath}/edit-comment?comment-id=${comment.commentId}&post-id=${requestScope.post.postId}"><button class="btn btn-warning btn-xs">Edit comment</button></a>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:if>


        <c:if test="${not empty sessionScope.user}">
            <div class="row bs-callout bs-callout-warning">
                <form class="form-signin" method="post" action="add-comment?post-id=${requestScope.post.postId}">
                    <textarea name="inputMessage" rows="5" class="form-control"
                              placeholder="post comment - max 500 character" required autofocus></textarea>
                    <input class="btn btn-lg btn-success btn-block" type="submit"
                           value="add comment" />
                </form>
            </div>
        </c:if>

    </div>

<div class="text-center">
    <nav aria-label="...">
        <ul class="pagination">
            <c:choose>
                <c:when test="${requestScope.pageNumber == 1}">
                    <li class="page-item disabled">
                        <span class="page-link">Previous</span>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/post?post-id=${requestScope.post.postId}&page=${requestScope.pageNumber-1}">Previous</a>
                    </li>
                </c:otherwise>
            </c:choose>

            <c:forEach var="page" items="${requestScope.pages}">
                <c:choose>
                    <c:when test="${page == requestScope.pageNumber}">
                        <li class="page-item active">
                            <span class="page-link">
                                <c:out value="${page.toString()}" />
                                <span class="sr-only">(current)</span>
                            </span>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="${pageContext.request.contextPath}/post?post-id=${requestScope.post.postId}&page=${page}"><c:out value="${page.toString()}" /></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
                <c:when test="${requestScope.pageNumber == requestScope.lastPageNumber}">
                    <li class="page-item disabled">
                        <span class="page-link">Next</span>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/post?post-id=${requestScope.post.postId}&page=${requestScope.pageNumber+1}">Next</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</div>



<jsp:include page="fragments/footer.jsp" />

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="resources/js/bootstrap.js"></script>
</body>
</html>