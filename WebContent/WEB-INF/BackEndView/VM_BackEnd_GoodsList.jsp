<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="./bootstrap4.0/bootstrap.min.css" />
<title>販賣機-後臺ShoppingCar</title>
<script type="text/javascript" src="./bootstrap4.0/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="./bootstrap4.0/popper.min.js"></script>
<script type="text/javascript" src="./bootstrap4.0/bootstrap.min.js"></script>
	<style type="text/css">
 		.page {
 			display:inline-block;
 			padding-left: 10px; 
		}
	</style>
	<script type="text/javascript">
	
// 	function test(){
		
// 		alert("測試");
	      
// 	}
	
	
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
		  'use strict';
		  window.addEventListener('load', function() {
		    // Fetch all the forms we want to apply custom Bootstrap validation styles to
		    var forms = document.getElementsByClassName('needs-validation');
		    // Loop over them and prevent submission
		    var validation = Array.prototype.filter.call(forms, function(form) {
		      form.addEventListener('submit', function(event) {
		        if (form.checkValidity() === false) {
		          event.preventDefault();
		          event.stopPropagation();
		        }
		        form.classList.add('was-validated');
		      }, false);
		    });
		  }, false);
		})();
	
	
	</script>
</head>
<body>
	<div class="container">
	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
	<div class="container">
		<p class="h2">商品列表</p>
		<br/>
		
		<form action="BackEndActionSearchGoods.do" method="get" class="needs-validation" novalidate>
			<div class="form-row">
			    <div class="col">
			      <label for="goodsIDID">商品編號</label>
			      <input type="number" min="1" class="form-control" value="${searchCondition.goodsID}" name="goodsID" id="goodsIDID" placeholder="Enter number">
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col">
			      <label for="keywordID">商品名稱</label>
			      <input type="text" class="form-control" value="${searchCondition.keyword}" name="keyword" id="keywordID" placeholder="Enter text">
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
	  		</div>
	  		<br/>
	  		<div class="form-row">
			    <div class="col">
			      <label for="minPriceID">商品價格最低價</label>
			      <input type="number" min="0" class="form-control" value="${searchCondition.minPrice}" name="minPrice" id="minPriceID" placeholder="Enter goods price start">
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col">
			      <label for="maxPriceID">商品價格最高價</label>
			      <input type="number" min="0" class="form-control" value="${searchCondition.maxPrice}" name="maxPrice" id="maxPriceID" placeholder="Enter goods price end">
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col">
			      <label for="sortByPriceID">價格排序</label>
			      <select class="form-control" name="sortByPrice" id="sortByPriceID">
			      	<option <c:if test="${empty searchCondition.sortByPrice}">selected</c:if> value="">無</option>
			        <option <c:if test="${searchCondition.sortByPrice eq 'ASC'}">selected</c:if> value="ASC">由低到高</option>
					<option <c:if test="${searchCondition.sortByPrice eq 'DESC'}">selected</c:if> value="DESC">由高到低</option>
				  </select>
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
	  		</div>
	  		<br/>
	  		<div class="form-row">
			    <div class="col">
			      <label for="stockID">商品低於庫存量</label>
			      <input type="number" min="0" class="form-control" value="${searchCondition.stock}" name="stock" id="stockID" placeholder="Enter goods stock quantity">
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col">
			      <label for="statusID">商品狀態</label>
			      <select class="form-control" name="status" id="statusID">
			        <option <c:if test="${empty searchCondition.status}">selected</c:if> value="">ALL</option>
			        <option <c:if test="${searchCondition.status eq 1}">selected</c:if> value="1">上架</option>
					<option <c:if test="${searchCondition.status eq 0 && not empty searchCondition.status}">selected</c:if> value="0">下架</option>
				  </select>
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col">
			    	<label for="submitID">查詢</label>
			    	<input type="hidden" name="action" value="queryGoodsBySearchCondition"/>
			    	<input type="hidden" name="currentPage" value="1"/>
					<!--  <button class="btn btn-primary" type="submit">查詢</button> -->
			    	<input type="submit" id="submitID" class="form-control" value="查詢">
					<!-- <input type="button" onclick="test()" class="form-control" value="查詢"> -->
			    </div>
	  		</div>
		</form>
		
		<br/>
		<table class="table table-striped table table-hover" border="1">
		    <thead>
		      <tr>
		        <th scope="col">商品編號</th>
		        <th colspan="2">商品名稱</th>
		        <th scope="col">商品價格</th>
		        <th scope="col">現有庫存</th>
		        <th scope="col">商品狀態</th>
		      </tr>
		    </thead>
		    <tbody>
		   	 <c:forEach items="${searchCondition.goods}" var="good">
			      <tr>
			        <th scope="row">${good.goodsID}</th>
			        <td colspan="2">${good.goodsName}</td>
			        <td>${good.goodsPrice}</td>
			        <td>${good.goodsQuantity}</td>
			        <c:choose>
							<c:when test="${good.status eq 1}">
								<td style="color:blue">上架</td>
							</c:when>
							<c:otherwise>
								<td style="color:red">下架</td>
							</c:otherwise>
					</c:choose>
			      </tr>
		      </c:forEach>
		    </tbody>
	    </table>
	    
		<ul class="pagination">
	      <c:if test="${searchCondition.page.currentPage != 1}">
		      <li class="page-item">
		        <a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=1">«</a>
		      </li>
		      <li class="page-item">
		        <a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${searchCondition.page.currentPage-1}">‹</a>
		      </li>
	      </c:if>
	      <c:forEach begin="${searchCondition.page.startPage}" end="${searchCondition.page.endPage}" var="pageNum">
	      	<c:if test="${searchCondition.page.currentPage != pageNum}">
	      		<li class="page-item"><a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${pageNum}">${pageNum}</a></li>
	      	</c:if>
	      	<c:if test="${searchCondition.page.currentPage == pageNum}">
	      		<li class="page-item active"><a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${pageNum}">${pageNum}</a></li>
	      	</c:if>
	      </c:forEach>
	      <c:if test="${searchCondition.page.currentPage != searchCondition.page.pageTotalCount}">
		      <li class="page-item">
		        <a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${searchCondition.page.currentPage+1}">›</a>
		      </li>
		      <li class="page-item">
		        <a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${searchCondition.page.pageTotalCount}">»</a>
		      </li>
	      </c:if>
	    </ul>
	    <p class="text-muted" style="display:inline-block">共${fn:length(searchCondition.goods)}件商品</p>
	</div>
</body>
</html>