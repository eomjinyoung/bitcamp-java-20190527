<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav id='header' class='navbar navbar-expand-lg navbar-light bg-light'>
  <a class='navbar-brand' href='#'>
    <img src='/images/logo.png' class='d-inline-block align-top'>
    수업관리시스템
  </a>
  <button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarNav' aria-controls='navbarNav' aria-expanded='false' aria-label='Toggle navigation'> 
    <span class='navbar-toggler-icon'></span> 
  </button>
  <div class='collapse navbar-collapse' id='navbarSupportedContent'>
    <ul class='navbar-nav'>
      <li class='nav-item active'>
        <a class='nav-link' href='/board/list'>게시판</a>
      </li>
      <li class='nav-item active'>
        <a class='nav-link' href='/lesson/list'>수업관리</a>
      </li>
      <li class='nav-item active'>
        <a class='nav-link' href='/member/list'>회원관리</a>
      </li>
      <li class='nav-item active'>
        <a class='nav-link' href='/photoboard/list'>사진게시판</a>
      </li>
    </ul>
  </div>
    
  <div>
<c:if test="${empty loginUser}">
  <a href='/auth/login' class='btn btn-outline-dark btn-sm'>로그인</a>
</c:if>
<c:if test="${not empty loginUser}">
  <a href='/member/detail?no=${loginUser.no}'>${loginUser.name}</a> 
  <a href='/auth/logout' class='btn btn-outline-dark btn-sm'>로그아웃</a>
</c:if>
  </div>
</nav>
