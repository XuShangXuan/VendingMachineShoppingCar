<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link type="text/css" rel="stylesheet" href="./bootstrap4.0/bootstrap.min.css" />
<script type="text/javascript" src="./bootstrap4.0/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="./bootstrap4.0/popper.min.js"></script>
<script type="text/javascript" src="./bootstrap4.0/bootstrap.min.js"></script>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	    <div class="container-fluid">
	      <a class="navbar-brand" href="BackEndActionSearchGoods.do?goodsID=&keyword=&minPrice=&maxPrice=&sortByPrice=&stock=&status=&action=queryGoodsBySearchCondition&showPageCount=3&currentPage=1">
		    <img src="DrinksImage/coffee.jpg" style="border-radius:50%;" width="30" height="24" class="d-inline-block align-text-top">
		    BackEnd Service
		  </a>
	      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
	        <span class="navbar-toggler-icon"></span>
	      </button>
	      <div class="collapse navbar-collapse" id="navbarColor01">
	        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	          <li class="nav-item">
	            <a class="nav-link" href="BackEndActionSearchGoods.do?goodsID=&keyword=&minPrice=&maxPrice=&sortByPrice=&stock=&status=&action=queryGoodsBySearchCondition&showPageCount=3&currentPage=1">商品列表</a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link" href="BackEndAction.do?action=backEndGoodsReplenishmentView">商品維護作業</a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link" href="BackEndAction.do?action=backEndGoodsCreateView">商品新增上架</a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link" href="BackEndAction.do?action=querySalesReport">銷售報表</a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link active" aria-current="page" href="FrontEndAction.do?action=queryGoodsBySearchCondition">前台頁面</a>
	          </li>
	        </ul>
	      </div>
	    </div>
    </nav>
	<br/>