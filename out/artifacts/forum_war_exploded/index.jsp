<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<nav class = "navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <a href="#" class="navbar-brand">Forum</a>

    <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
      <span class="glyphicon glyphicon-list"></span>
    </button>

    <div class="collapse navbar-collapse navHeaderCollapse">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">Główna</a></li>
        <li><a href="#">Dodaj</a></li>
        <c:choose>
          <c:when test="${not empty sessionScope.user}">
            <li><a href="sign-out">Wyloguj się</a></li>
          </c:when>
          <c:otherwise>
            <li><a href="sign-in">Zaloguj się</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>


  </div>
</nav>

<div class="container">
  <div class="row bs-callout bs-callout-warning">
    <div class="col col-md-1 col-sm-2">
      <a href="#" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
      <div class="well well-sm centered"><p style="text-align: center">12</div>
      <a href="#" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
    </div>
    <div class="col col-md-11 col-sm-10">
      <h3 class="centered"><a href="#">Post</a></h3>
      <h6><small style="color:#171716">Dodane przez: Dawid 24.08.2020</small></h6>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
      <button class="btn btn-info btn-xs">Przejdź do strony</button>
    </div>
  </div>


  <div class="row bs-callout bs-callout-warning">
    <div class="col col-md-1 col-sm-2">
      <a href="#" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
      <div class="well well-sm centered"><p style="text-align: center">12</div>
      <a href="#" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
    </div>
    <div class="col col-md-11 col-sm-10">
      <h3 class="centered"><a href="#">Post</a></h3>
      <h6><small style="color:#171716">Dodane przez: Dawid 24.08.2020</small></h6>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
      <button class="btn btn-info btn-xs">Przejdź do strony</button>
    </div>
  </div>


  <div class="row bs-callout bs-callout-warning">
    <div class="col col-md-1 col-sm-2">
      <a href="#" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
      <div class="well well-sm centered"><p style="text-align: center">12</div>
      <a href="#" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
    </div>
    <div class="col col-md-11 col-sm-10">
      <h3 class="centered"><a href="#">Post</a></h3>
      <h6><small style="color:#171716">Dodane przez: Dawid 24.08.2020</small></h6>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
      <button class="btn btn-info btn-xs">Przejdź do strony</button>
    </div>
  </div>


  <div class="row bs-callout bs-callout-warning">
    <div class="col col-md-1 col-sm-2">
      <a href="#" class="btn btn-block btn-primary btn-success"><span class="glyphicon glyphicon-thumbs-up"></span>  </a>
      <div class="well well-sm centered"><p style="text-align: center">12</div>
      <a href="#" class="btn btn-block btn-primary btn-danger"><span class="glyphicon glyphicon-thumbs-down"></span>  </a>
    </div>
    <div class="col col-md-11 col-sm-10">
      <h3 class="centered"><a href="#">Post</a></h3>
      <h6 ><small style="color:#171716">Dodane przez: Dawid 24.08.2020</small></h6>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
      <button class="btn btn-info btn-xs">Przejdź do strony</button>
    </div>
  </div>
</div>



<footer class="footer">
  <div class="container">
    <p class="navbar-text">Forum - developed by <a href="https://github.com/DaDudek">Dawid Dudek</a></p>
  </div>
</footer>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="resources/js/bootstrap.js"></script>
</body>
</html>