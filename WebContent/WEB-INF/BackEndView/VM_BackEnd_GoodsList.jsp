<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	</style>
	
	<script type="text/javascript">
	</script>
</head>
<body>
	<div class="container">
	
		<%@ include file="VM_BackEnd_FunMenu.jsp" %>
	
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
			    	<input type="hidden" name="showPageCount" value="${searchCondition.page.showPageCount}"/>
			    	<input type="submit" id="submitID" class="form-control" value="查詢">
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
	    
	    <div class="row">
		    <div class="col-1">
			    <div class="dropdown">
				  <button class="btn btn-light dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    ${searchCondition.page.showPageCount}/Page
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				    <a class="dropdown-item" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=3&currentPage=${searchCondition.page.currentPage}">3/Page</a>
				    <a class="dropdown-item" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=5&currentPage=${searchCondition.page.currentPage}">5/Page</a>
				    <a class="dropdown-item" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=7&currentPage=${searchCondition.page.currentPage}">7/Page</a>
				  </div>
				</div>
			</div>
	    	<div class="col">
				<ul class="pagination ">
			      <c:if test="${searchCondition.page.currentPage != 1}">
				      <li class="page-item">
				        <a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=${searchCondition.page.showPageCount}&currentPage=1">«</a>
				      </li>
				      <li class="page-item">
				        <a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=${searchCondition.page.showPageCount}&currentPage=${searchCondition.page.currentPage-1}">‹</a>
				      </li>
			      </c:if>
			      <c:forEach begin="${searchCondition.page.startPage}" end="${searchCondition.page.endPage}" var="pageNum">
			      	<c:if test="${searchCondition.page.currentPage != pageNum}">
			      		<li class="page-item"><a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=${searchCondition.page.showPageCount}&currentPage=${pageNum}">${pageNum}</a></li>
			      	</c:if>
			      	<c:if test="${searchCondition.page.currentPage == pageNum}">
			      		<li class="page-item active"><a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=${searchCondition.page.showPageCount}&currentPage=${pageNum}">${pageNum}</a></li>
			      	</c:if>
			      </c:forEach>
			      <c:if test="${searchCondition.page.currentPage != searchCondition.page.pageTotalCount}">
				      <li class="page-item">
				        <a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=${searchCondition.page.showPageCount}&currentPage=${searchCondition.page.currentPage+1}">›</a>
				      </li>
				      <li class="page-item">
				        <a class="page-link" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&showPageCount=${searchCondition.page.showPageCount}&currentPage=${searchCondition.page.pageTotalCount}">»</a>
				      </li>
			      </c:if>
			    </ul>
		    </div>
	    	<div class="col-2">
	    		<p class="text-muted" align="right">共${searchCondition.goodsTotalCounts}件商品</p>
	    	</div>
	    </div>
	</div>
</body>
</html>