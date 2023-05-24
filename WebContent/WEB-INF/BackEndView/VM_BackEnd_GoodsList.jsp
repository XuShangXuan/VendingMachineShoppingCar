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
 		.page {
 			display:inline-block;
 			padding-left: 10px; 
		}
	</style>
	<script type="text/javascript">
	
	function test(){
		
		alert("測試");
	      
	}
	
	</script>
</head>
<body>

	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
	
	<div style="margin-left:25px">
		<h2>商品列表</h2>
		
		<br/>
		<form action="BackEndActionSearchGoods.do" method="get" class="needs-validation" novalidate>
			<div class="form-row">
			    <div class="col-3">
			      <label for="goodsIDID">商品編號</label>
			      <input type="number" min="1" class="form-control" value="${searchCondition.goodsID}" name="goodsID" id="goodsIDID" placeholder="Enter number" required>
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col-3">
			      <label for="keywordID">商品名稱</label>
			      <input type="text" class="form-control" value="${searchCondition.keyword}" name="keyword" id="keywordID" placeholder="Enter text" required>
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
	  		</div>
	  		<br/>
	  		<div class="form-row">
			    <div class="col-2">
			      <label for="minPriceID">商品價格最低價</label>
			      <input type="number" min="0" class="form-control" value="${searchCondition.minPrice}" name="minPrice" id="minPriceID" placeholder="Enter goods price start" required>
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col-2">
			      <label for="maxPriceID">商品價格最高價</label>
			      <input type="number" min="0" class="form-control" value="${searchCondition.maxPrice}" name="maxPrice" id="maxPriceID" placeholder="Enter goods price end" required>
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col-2">
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
			    <div class="col-2">
			      <label for="stockID">商品低於庫存量</label>
			      <input type="number" min="0" class="form-control" value="${searchCondition.stock}" name="stock" id="stockID" placeholder="Enter goods stock quantity" required>
			      <div class="valid-feedback">
			        Looks good!
			      </div>
			    </div>
			    <div class="col-2">
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
			    <div class="col-2">
			    	<label for="submitID">查詢</label>
			    	<input type="hidden" name="action" value="queryGoodsBySearchCondition"/>
			    	<input type="hidden" name="currentPage" value="1"/>
			    	<input type="submit" id="submitID" class="form-control" value="查詢">
					<!-- <input type="button" onclick="test()" class="form-control" value="查詢"> -->
			    </div>
	  		</div>
		</form>
		
		<br/>
		
<!-- 		<table> -->
<!-- 			<td colspan="2" align="Left"> -->
<!-- 				<form action="BackEndAction.do" method="get"> -->
<!-- 					<input type="hidden" name="action" value="searchGoods"/> -->
<!-- 					<input type="hidden" name="pageNo" value="1"/> -->
<!-- 					<input type="text" name="searchKeyword"/> -->
<!-- 					<input type="submit" value="商品查詢"/> -->
<!-- 				</form> -->
<!-- 			</td> -->
<!-- 		</table> -->
		<table border="1">
			<tbody>
				<tr height="50">
					<td width="100"><b>商品編號</b></td>
					<td width="200"><b>商品名稱</b></td> 
					<td width="100"><b>商品價格</b></td>
					<td width="100"><b>現有庫存</b></td>
					<td width="100"><b>商品狀態</b></td>
				</tr>
	   			<c:forEach items="${searchCondition.goods}" var="good">
					<tr height="30">
						<td>${good.goodsID}</td> 
						<td>${good.goodsName}</td> 
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
	</div>
	
	<table width="630">
	<tr>
		<td colspan="2" align="right">
			<c:if test="${searchCondition.page.currentPage != 1}">
				<h3 class="page">
					<a href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${searchCondition.page.currentPage-1}"> 上一頁 </a>
				</h3>
			</c:if>
			<c:forEach begin="${searchCondition.page.startPage}" end="${searchCondition.page.endPage}" var="pageNum">
				<c:if test="${searchCondition.page.currentPage != pageNum}">
					<h3 class="page">
						<a href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${pageNum}"> ${pageNum} </a>
					</h3>
				</c:if>
				<c:if test="${searchCondition.page.currentPage == pageNum}">
					<h3 class="page">
						<a style="color:red" href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${pageNum}"> ${pageNum} </a>
					</h3>
				</c:if>
			</c:forEach>
			<c:if test="${searchCondition.page.currentPage != searchCondition.page.pageTotalCount}">
				<h3 class="page" >
					<a href="BackEndActionSearchGoods.do?action=queryGoodsBySearchCondition&currentPage=${searchCondition.page.currentPage+1}"> 下一頁 </a>
				</h3>
			</c:if>
		</td>
	</tr>
	</table>
</body>
</html>