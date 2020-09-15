<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<c:if test="${not empty commentsResponse}">
    <c:forEach var="comment" items="${commentsResponse}">
        <c:set var="commentsResponse" value="${comment.firstChildrenList}" scope="request"/>
        <div class="container">
            <div class="row bs-callout bs-callout-warning">
                <div class="col col-md-1 col-sm-2">
                    <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=true&comment_id=${comment.commentId}&post-page=true&page=${requestScope.pageNumber}"" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
                    <div class="well well-sm centered"><p style="text-align: center"><c:out value="${comment.positiveVote - comment.negativeVote}" /></div>
                    <a href="${pageContext.request.contextPath}/comment-vote?post_id=${comment.postId}&is_positive=false&comment_id=${comment.commentId}&post-page=true&page=${requestScope.pageNumber}"" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
                </div>
                <div class="col col-md-11 col-sm-10">
                    <h6><small style="color:#171716">Add by: <c:out value="${comment.author}" />  date: <fmt:formatDate value="${comment.date}" pattern="dd/MM/YYYY"/></small></h6>
                    <c:choose>
                        <c:when test="${requestScope.commentEditingId == comment.commentId}">
                            <form class="form-signin" method="post" action="edit-comment?comment-id=${comment.commentId}&post-id=${requestScope.post.postId}&page=${requestScope.pageNumber}">
                                        <textarea name="inputMessage" rows="5" class="form-control"
                                                  placeholder="post comment - max 500 character (more will be cut)" required autofocus>${comment.message}</textarea>
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
                    <a href="${pageContext.request.contextPath}/delete-comment?comment-id=${comment.commentId}&post-id=${requestScope.post.postId}&page=${requestScope.pageNumber}"><button class="btn btn-danger btn-xs">Delete comment</button></a>
                </c:if>
                <c:if test="${comment.userId ==  sessionScope.user.userId}">
                    <c:if test="${requestScope.isEditing == null}">
                        <a href="${pageContext.request.contextPath}/edit-comment?comment-id=${comment.commentId}&post-id=${requestScope.post.postId}&page=${requestScope.pageNumber}"><button class="btn btn-warning btn-xs">Edit comment</button></a>
                    </c:if>
                </c:if>
                <c:if test="${not empty sessionScope.user}">
                    <c:if test="${requestScope.isEditing == null}">
                        <a href="${pageContext.request.contextPath}/response?post-id=${requestScope.post.postId}&parent-id=${comment.commentId}&page=${requestScope.pageNumber}"><button class="btn btn-info btn-xs">Response to comment</button></a>
                    </c:if>
                </c:if>
            </div>
            <jsp:include page="response.jsp" />
            <c:if test="${comment.commentId == requestScope.responseParentId}">
                <div class="row bs-callout bs-callout-warning">
                    <form class="form-signin" method="post" action="response?post-id=${requestScope.post.postId}&parent-id=${comment.commentId}&page=${requestScope.pageNumber}">
                                    <textarea name="inputMessage" rows="5" class="form-control"
                                              placeholder="post comment - max 500 character (more will be cut)" required autofocus></textarea>
                        <input class="btn btn-lg btn-success btn-block" type="submit"
                               value="add response" />
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>
</c:if>
