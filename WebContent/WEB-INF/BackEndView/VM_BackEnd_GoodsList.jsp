<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>販賣機-後臺ShoppingCar</title>
	<style type="text/css">
 		.page {
 			display:inline-block;
 			padding-left: 10px; 
		}
	</style>
	<script type="text/javascript">
	</script>
</head>
<body>

	<%@ include file="VM_BackEnd_FunMenu.jsp" %>
		
	<h2>商品列表</h2><br/>
	<div style="margin-left:25px;">
		<table border="1">
			<tbody>
				<tr height="50">
					<td width="150"><b>商品名稱</b></td> 
					<td width="100"><b>商品價格</b></td>
					<td width="100"><b>現有庫存</b></td>
					<td width="100"><b>商品狀態</b></td>
				</tr>
	   			<c:forEach items="${pageBean.goods}" var="good">
					<tr height="30">
						<td>${good.goodsName}</td> 
						<td>${good.goodsPrice}</td>
						<td>${good.goodsQuantity}</td>
						<c:choose>
							<c:when test="${good.status eq 1}">
								<td>上架</td>
							</c:when>
							<c:otherwise>
								<td>下架</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<tr>
			<td colspan="2" align="right">
				<c:if test="${pageBean.currentPage != 1}">
					<h3 class="page">
						<a href="BackEndAction.do?action=queryGoodsByPage&currentPage=${pageBean.currentPage-1}"> 上一頁 </a>
					</h3>
				</c:if>
				<c:forEach begin="${pageBean.startPage}" end="${pageBean.endPage}" var="pageNum">
					<c:if test="${pageBean.currentPage != pageNum}">
						<h3 class="page">
							<a href="BackEndAction.do?action=queryGoodsByPage&currentPage=${pageNum}"> ${pageNum} </a>
						</h3>
					</c:if>
					<c:if test="${pageBean.currentPage == pageNum}">
						<h3 class="page">
							<a style="color:red" href="BackEndAction.do?action=queryGoodsByPage&currentPage=${pageNum}"> ${pageNum} </a>
						</h3>
					</c:if>
				</c:forEach>
				<c:if test="${pageBean.currentPage != pageBean.pageTotalCount}">
					<h3 class="page" >
						<a href="BackEndAction.do?action=queryGoodsByPage&currentPage=${pageBean.currentPage+1}"> 下一頁 </a>
					</h3>
				</c:if>
			</td>
		</tr>
	</div>
</body>
</html>